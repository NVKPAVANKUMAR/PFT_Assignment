import unittest
import urlparse
import json
from pprint import pprint

import requests


def get_youtube_id(url):
    url_data = urlparse.urlparse(url)
    query = urlparse.parse_qs(url_data.query)
    video = query["v"][0]
    return video


class GetViewsCount(unittest.TestCase):

    def test_01(self):
        youtube_url = "https://www.youtube.com/watch?v=iSB-8Yes9XE"
        video_id = get_youtube_id(youtube_url)
        api_base_url = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails,statistics"
        payload = {'id': video_id, 'key': 'API_KEY'}
        r = requests.get(api_base_url, params=payload, verify=False)
        r.encoding = r.apparent_encoding
        link_data = json.loads(r.text)
        if r.status_code == 200:
            print(link_data['items'][0]['statistics']['viewCount'])


if __name__ == '__main__':
    unittest.main(verbosity=2)
