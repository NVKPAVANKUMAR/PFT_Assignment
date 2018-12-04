package javaExamples;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.given;

public class YoutubeViews {

    private final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    private final String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    private String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for (String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }


    @Test
    public void test_url() {
        String id = extractVideoIdFromUrl("https://www.youtube.com/watch?v=iSB-8Yes9XE");
        Response resp = given().
                param("part", "contentDetails,statistics").
                param("id", id).
                param("key", "API_KEY").
                when().
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                get("https://www.googleapis.com/youtube/v3/videos");
        resp.prettyPrint();


        int res = resp.
                then().
                contentType(ContentType.JSON).
                extract().
                path("items.statistics.viewCount");
        System.out.println(res);
        assert resp.statusCode() == 200;
    }
}
