import pandas as pd
import unittest


class ReverseExcelData(unittest.TestCase):
    def test_excel(self):
        df = pd.read_excel('test_excel.xlsx', sheetname='Sheet1')
        for i in df.index:
            print(df['Number'][i])
        reversed_data = df[::-1]
        writer = pd.ExcelWriter('copied.xlsx', engine='xlsxwriter')
        reversed_data.to_excel(writer, index=False, sheet_name='report')
        writer.save()


if __name__ == '__main__':
    unittest.main(verbosity=2)
