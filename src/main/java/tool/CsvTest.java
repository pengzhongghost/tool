package tool;

import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author pengzhong
 * @since 2023/5/30
 */
public class CsvTest {

    public static void main(String[] args) throws FileNotFoundException {
        CsvReader reader  = CsvUtil.getReader(new FileReader("/Users/pengzhong/Downloads/原始团长6月份.csv"));
        reader.stream().forEach(row -> {
            System.out.println(row.getRawList());
            System.out.println();
        });
    }

}
