package tool.dss;

/**
 * @author pengzhong
 * @since 2023/7/3
 */
public class ParseDssSql {

    public static void main(String[] args) {
        String sql = "CREATE TABLE data_cube.`group`(\n" +
                "`id` int,\n" +
                "`type` string,\n" +
                "`service_type` smallint,\n" +
                "`paltfarm_type` smallint,\n" +
                "`product_category_ids` string,\n" +
                "`product_category_two_ids` string,\n" +
                "`product_category_three_ids` string,\n" +
                "`team_id` int,\n" +
                "`branch_id` int,\n" +
                "`name` string,\n" +
                "`avatar` string,\n" +
                "`username` string,\n" +
                "`password_hash` string,\n" +
                "`auth_key` string,\n" +
                "`remark` string,\n" +
                "`status` smallint,\n" +
                "`created_at` int,\n" +
                "`updated_at` int,\n" +
                "`contact_tel` string,\n" +
                "`max_bind_number` int)\n"
                ;
        String extend = "PARTITIONED BY (`ds` string COMMENT 'YYYYMMDD')\n" +
                "row format delimited fields terminated by ','\n" +
                "STORED AS ORC\n" +
                "TBLPROPERTIES ('orc.compress'='ZLIB');";
        System.out.println(sql + extend);
    }

}
