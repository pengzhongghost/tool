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
                "CREATE TABLE `config_item` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `is_delete` tinyint(4) DEFAULT NULL,\n" +
                        "  `create_time` datetime DEFAULT NULL,\n" +
                        "  `update_time` datetime DEFAULT NULL,\n" +
                        "  `creator_id` int(11) DEFAULT NULL,\n" +
                        "  `modifier_id` int(11) DEFAULT NULL,\n" +
                        "  `config_id` int(11) DEFAULT NULL COMMENT '配置id',\n" +
                        "  `user_id` int(11) DEFAULT NULL COMMENT '用户id',\n" +
                        "  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',\n" +
                        "  `value` varchar(10000) DEFAULT NULL COMMENT '配置值'\n" +
                        ")";
        String createTable = HiveSqlParseTest.parseSql(sql);
        createTable = createTable + " PARTITIONED BY (`ds` string COMMENT 'YYYYMMDD') ROW format delimited FIELDS TERMINATED BY ',' STORED AS ORC TBLPROPERTIES ( 'orc.compress' = 'ZLIB' );";
        List<ColumnInfo> columnInfos = SqlTest.parseSql(sql);
        System.out.println("---------mysql to hdfs-------");
        System.out.println(createTable);
        System.out.println(JSONUtil.toJsonStr(columnInfos));
    }

}
