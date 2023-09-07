package tool.mysqlToHdfs;

import cn.hutool.json.JSONUtil;
import net.sf.jsqlparser.JSQLParserException;
import pojo.ColumnInfo;
import tool.HiveSqlParseTest;
import tool.SqlTest;

import java.util.List;
import java.util.TreeSet;

/**
 * @author pengzhong
 * @since 2023/6/27
 */
public class MysqlToHdfsTest {

    public static void main(String[] args) throws JSQLParserException {
        String sql =
                "CREATE TABLE `re_du_dian_shang_ke320` (\n" +
                        "  `id` int(11) NOT NULL COMMENT \"\",\n" +
                        "  `col_0` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_1` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_2` datetime NULL COMMENT \"\",\n" +
                        "  `col_3` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_4` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_5` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_6` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_7` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_8` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_9` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_10` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_11` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_12` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_13` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_14` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_15` datetime NULL COMMENT \"\",\n" +
                        "  `col_16` int(11) NULL COMMENT \"\",\n" +
                        "  `col_17` varchar(765) NULL COMMENT \"\",\n" +
                        "  `col_18` varchar(765) NULL COMMENT \"\"\n" +
                        ")";
        String createTable = HiveSqlParseTest.parseSql(sql);
        createTable = createTable + " PARTITIONED BY (`ds` string COMMENT 'YYYYMMDD') ROW format delimited FIELDS TERMINATED BY ',' STORED AS ORC TBLPROPERTIES ( 'orc.compress' = 'ZLIB' );";
        List<ColumnInfo> columnInfos = SqlTest.parseSql(sql);
        System.out.println("---------mysql to hdfs-------");
        System.out.println(createTable);
        System.out.println(JSONUtil.toJsonStr(columnInfos));
    }

}
