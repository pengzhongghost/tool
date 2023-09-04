package tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import java.util.List;

/**
 * @author pengzhong
 * @since 2023/5/30
 */
public class CsvTest {

    public static void main(String[] args) {
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(FileUtil.file("/Users/pengzhong/Downloads/redu_product__d40e0f16_1b64_4eb7_b21a_902db61ee9a0"));
        List<CsvRow> rows = data.getRows();
        for (CsvRow row : rows) {
            for (String s : row.getRawList()) {
                if (s.contains("男女皆可")) {
                    System.out.println(s);
                }
                System.out.printf("%s\t", s);
            }
            System.out.println();
        }
    }

}
