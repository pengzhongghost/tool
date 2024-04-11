package tool.mysqlToHdfs;

import cn.hutool.json.JSONUtil;
import net.sf.jsqlparser.JSQLParserException;
import pojo.ColumnInfo;
import tool.HiveSqlParseTest;
import tool.SqlTest;

import java.util.List;

/**
 * @author pengzhong
 * @since 2023/6/27
 */
public class MysqlToHdfsTest {

    public static void main(String[] args) throws JSQLParserException {
        String sql =
                "CREATE TABLE `dianyu_talent` (\n" +
                        "  `id` bigint(20) NOT NULL COMMENT \"\",\n" +
                        "  `tenant_id` int(11) NOT NULL DEFAULT \"0\" COMMENT \"租户ID\",\n" +
                        "  `member_id` int(11) NOT NULL DEFAULT \"0\" COMMENT \"用户ID\",\n" +
                        "  `platform` varchar(16) NOT NULL DEFAULT \"\" COMMENT \"达人平台 douyin：抖音 kuaishou：快手 weixin：视频号\",\n" +
                        "  `account_id` varchar(100) NOT NULL DEFAULT \"\" COMMENT \"达人平台账号\",\n" +
                        "  `uid` varchar(100) NOT NULL DEFAULT \"\" COMMENT \"达人平台uid\",\n" +
                        "  `unique_id` varchar(100) NOT NULL DEFAULT \"\" COMMENT \"达人平台授权唯一ID\",\n" +
                        "  `union_id` varchar(100) NOT NULL DEFAULT \"\" COMMENT \"达人平台多主体唯一标识符\",\n" +
                        "  `nickname` varchar(100) NOT NULL DEFAULT \"\" COMMENT \"达人昵称\",\n" +
                        "  `avatar` varchar(255) NOT NULL DEFAULT \"\" COMMENT \"达人头像地址\",\n" +
                        "  `fans_total` int(11) NOT NULL DEFAULT \"0\" COMMENT \"达人粉丝数量\",\n" +
                        "  `mobile` varchar(20) NOT NULL DEFAULT \"\" COMMENT \"达人手机号\",\n" +
                        "  `shop_sales` int(11) NOT NULL DEFAULT \"0\" COMMENT \"达人总橱窗销量\",\n" +
                        "  `level` smallint(6) NOT NULL DEFAULT \"0\" COMMENT \"达人等级\",\n" +
                        "  `auth_time` int(11) NOT NULL DEFAULT \"0\" COMMENT \"授权时间\",\n" +
                        "  `talent_status` tinyint(4) NOT NULL DEFAULT \"1\" COMMENT \"达人状态 1：正常 0：禁用\",\n" +
                        "  `last_month_sales` int(11) NOT NULL DEFAULT \"0\" COMMENT \"达人近30天橱窗销量\",\n" +
                        "  `access_token` varchar(512) NOT NULL DEFAULT \"\" COMMENT \"达人授权access_token\",\n" +
                        "  `expires_in` int(11) NOT NULL DEFAULT \"0\" COMMENT \"达人授权access_token过期时间\",\n" +
                        "  `refresh_token` varchar(512) NOT NULL DEFAULT \"\" COMMENT \"达人授权刷新token\",\n" +
                        "  `refresh_expires_in` int(11) NOT NULL DEFAULT \"0\" COMMENT \"达人授权刷新token过期时间\",\n" +
                        "  `extra_info` json NULL COMMENT \"达人扩展信息\",\n" +
                        "  `created_at` datetime NULL COMMENT \"\",\n" +
                        "  `updated_at` datetime NULL COMMENT \"\",\n" +
                        "  `deleted_at` int(11) NULL COMMENT \"\"\n" +
                        ")";
        System.out.println(sql);
        String createTable = HiveSqlParseTest.parseSql(sql);
        createTable = createTable + " PARTITIONED BY (`ds` string COMMENT 'YYYYMMDD') ROW format delimited FIELDS TERMINATED BY ',' STORED AS ORC TBLPROPERTIES ( 'orc.compress' = 'ZLIB' );";
        List<ColumnInfo> columnInfos = SqlTest.parseSql(sql);
        System.out.println("---------mysql to hdfs-------");
        System.out.println(createTable);
        System.out.println(JSONUtil.toJsonStr(columnInfos));
    }

}
