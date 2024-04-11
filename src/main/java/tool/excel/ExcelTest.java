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
        ExcelReader reader = ExcelUtil.getReader(new File("https://redu-secret.oss-cn-hangzhou.aliyuncs.com/pixiu/%E9%87%8C%E7%A8%8B%E7%A2%91%E5%A5%96%E5%8A%B1-%E6%88%AA%E6%AD%A28%E6%9C%88%E5%89%A9%E4%BD%99GMV.xlsx?Expires=1727335402&OSSAccessKeyId=LTAI5tJ4CV5ETUiR5WqBQ9hK&Signature=WtO5Ur72RjlxWD0zPEzJi8sIXpU%3D"));
        ExcelSaxReader<?> saxReader = ExcelSaxUtil.createSaxReader(false, (sheetIndex, rowIndex, rowCells) -> {
            System.out.println(rowCells);
            System.out.println();
//            throw new RuntimeException("你好");
        });
        saxReader.read(new FileInputStream("https://xingxuan-dev.oss-cn-hangzhou.aliyuncs.com/pixiu/yunying/%E9%87%8C%E7%A8%8B%E7%A2%91%E5%A5%96%E5%8A%B1-%E6%88%AA%E6%AD%A28%E6%9C%88%E5%89%A9%E4%BD%99GMV.xlsx?Expires=1696405953&OSSAccessKeyId=LTAI5tSe39NoRK98C4ucyHtM&Signature=1JA4YSay%2Balk4FmMcNAbvBBQihk%3D"));
        System.out.println();
    }

    public static String test(){
        return  "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAODcQDQoZfCLZk5C" +
                "Xs5rd/Y2QeeL8MQoWkZ55r33EJQlGuXBgFlpFkFlkpDJoXiXp19avx1VmynO7CJv" +
                "uDhOBGHwPLmfuSRvDJ02VxMWU+bn2MZAw3LkJvKNSc2McSsoBoBiPj3uEmduNA9q" +
                "4I0DgOXOwerzPA9mWjddyM4m39M/AgMBAAECgYAbbYI/WnYkwzw1a48Y6q71T2T2" +
                "k3FI3As28YOdxFc3WtdPEy1OuqzGSVd5Zb7WcNkvnmgVce29Scov4SS9aXbLKPMB" +
                "0w9ivmf/SCKDsqsMRHhKfQ76sGY+ard4hJHQdKdWa1ckQ8O9xckkQz+N/6Rz6Wbs" +
                "utvVXuaq4syeszxBYQJBAPNdNqBpYuSBS2QHXsIAI5kndm0/hz0Shj52GTflMv9M" +
                "8cnnOIH4grQGHnkSwQDKAGhE6KzE7dAYjndlw6+FR+cCQQDsiRYMqKk1qCa2hWHM" +
                "M4RMsHLROgRlJPxB1C1hN7afefysBEikyJE5SRiT6lKJhWa3zXLxU9VYpBvBUaLG" +
                "707pAkEA4VugCkuM2C0isy5gASY/0MNKV0BM67ELvyt9jtUftGp8PItxOGn4HtzL" +
                "uzIyp5hnaCbnj5WM4vbadpY174NsYQJAaH5HF2CLIAEg2REjmypHD/oDPxr6RhSI" +
                "vCwXwBL0wo8JaJSb4vAX986q5O1neQlNz3UbijEW1wnomQaPeoLNwQJAMtTMyTCY" +
                "JH6aHObfnu8LQPX4M+vHY7ewhcvLZ/gZRmZF1G9OO9LLquFVsWUjxLq/ZrFBAV0A" +
                "sMYgQz2tSdBh0Q==";
    }

}
