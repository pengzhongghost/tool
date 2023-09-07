package tool.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import cn.hutool.poi.excel.sax.ExcelSaxUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * @author pengzhong
 * @since 2023/9/5
 */
public class ExcelTest {

    public static void main(String[] args) throws FileNotFoundException {
        //ExcelReader reader = ExcelUtil.getReader(new File("/Users/pengzhong/Downloads/96e4e86f-c768-3a39-f449-89853a39f951_3630196287494342838.xlsx"));
        ExcelSaxReader<?> saxReader = ExcelSaxUtil.createSaxReader(true, (sheetIndex, rowIndex, rowCells) -> {
            System.out.println(rowCells);
            throw new RuntimeException("你好");
        });
        saxReader.read(new FileInputStream(new File("/Users/pengzhong/Downloads/96e4e86f-c768-3a39-f449-89853a39f951_3630196287494342838.xlsx")));
        System.out.println();
    }

}
