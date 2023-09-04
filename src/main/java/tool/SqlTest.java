package tool;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.springframework.util.CollectionUtils;
import pojo.ColumnInfo;
import pojo.Meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author pengzhong
 * @since 2023/5/22
 */
public class SqlTest {

    public static void main(String[] args) {
        String sql = "CREATE TABLE `product_apply_detail` (\n" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',\n" +
                "  `task_id` bigint(20) NOT NULL COMMENT '任务id',\n" +
                "  `product_info` varchar(255) DEFAULT NULL COMMENT '商品信息',\n" +
                "  `platform_product_id` bigint(20) NOT NULL COMMENT '平台商品id',\n" +
                "  `platform_activity_id` bigint(20) NOT NULL COMMENT '平台活动id',\n" +
                "  `colonel_id` bigint(20) NOT NULL COMMENT '团长id',\n" +
                "  `colonel_mobile` varchar(255) NOT NULL COMMENT '团长手机号',\n" +
                "  `product_type` int(11) NOT NULL COMMENT '商品类型',\n" +
                "  `service_fee_split` int(11) NOT NULL COMMENT '活动费率分成',\n" +
                "  `service_fee_ratio` varchar(255) NOT NULL COMMENT '服务费率',\n" +
                "  `origin_activity_id` bigint(20) NOT NULL COMMENT '原始活动id',\n" +
                "  `origin_activity_name` varchar(255) DEFAULT NULL COMMENT '原始活动名称',\n" +
                "  `status` int(11) NOT NULL COMMENT '0 未提报 1 提报成功 2 提报失败 3 不提报',\n" +
                "  `error_msg` varchar(500) DEFAULT NULL COMMENT '错误信息',\n" +
                "  `ext` varchar(255) DEFAULT NULL COMMENT '额外信息',\n" +
                "  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',\n" +
                "  `modifier_id` bigint(20) DEFAULT NULL COMMENT '操作人',\n" +
                "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_task_id` (`task_id`) USING BTREE COMMENT '任务id'\n" +
                ")";
        StringBuilder sqlBuilder = new StringBuilder();
        for (String s : sql.split("\n")) {
            int index = s.indexOf("\t");
            if (-1 == index) {
                sqlBuilder.append(s);
                continue;
            }
            String replace = s.replace(s.substring(0, index), "");
            sqlBuilder.append(replace);
        }
        sql = sqlBuilder.toString();
        List<Meta> list = new ArrayList<>();
        assembleMeta(list, sql);
        List<ColumnInfo> columns = new ArrayList<>();
        List<ColumnInfo> nameColumns = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder loadLabel = new StringBuilder();
        StringBuilder params = new StringBuilder();
        for (Meta meta : list) {
            loadLabel.append(meta.getEN()).append(",");
            System.out.println(meta.getEN().replace("`","\"") + ",");
            columns.add(ColumnInfo.builder().name(meta.getEN().replace("`", "")).type(meta.getCN().startsWith("decimal") ? "string":
                    (meta.getCN().indexOf("(") >0 ? meta.getCN().substring(0, meta.getCN().indexOf("(")) : meta.getCN())).build());
            nameColumns.add(ColumnInfo.builder().name(meta.getEN().replace("`", "")).build());
            sb.append("order:").append(meta.getEN().replace("`","")).append(",");
            params.append(meta.getEN().replace("`", "")).append(",");
        }
        System.out.println(loadLabel.toString());
        System.out.println("hiveColumns：" + JSONUtil.toJsonStr(columns));
        System.out.println("nameColumns：" + JSONUtil.toJsonStr(nameColumns));
        System.out.println(sb);
        System.out.println(sb.toString().split(",").length);
        System.out.println(params);
    }

    public static List<ColumnInfo> parseSql(String sql) {
        StringBuilder sqlBuilder = new StringBuilder();
        for (String s : sql.split("\n")) {
            int index = s.indexOf("\t");
            if (-1 == index) {
                sqlBuilder.append(s);
                continue;
            }
            String replace = s.replace(s.substring(0, index), "");
            sqlBuilder.append(replace);
        }
        sql = sqlBuilder.toString();
        List<Meta> list = new ArrayList<>();
        assembleMeta(list, sql);
        List<ColumnInfo> columns = new ArrayList<>();
        List<ColumnInfo> nameColumns = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder loadLabel = new StringBuilder();
        StringBuilder params = new StringBuilder();
        StringBuilder bean = new StringBuilder();
        for (Meta meta : list) {
            loadLabel.append(meta.getEN()).append(",");
            System.out.println(meta.getEN().replace("`","\"") + ",");
            if (meta.getCN().equalsIgnoreCase("text")) {
                meta.setCN("string");
            }
            if (meta.getCN().equalsIgnoreCase("datetime") || meta.getCN().equalsIgnoreCase("date") ) {
                meta.setCN("timestamp");
            }
            if (meta.getCN().startsWith("varchar") || meta.getCN().startsWith("VARCHAR") || meta.getCN().equalsIgnoreCase("json")) {
                meta.setCN("string");
            }
            columns.add(ColumnInfo.builder().name(meta.getEN().replace("`", "")).type(meta.getCN().startsWith("decimal") ? "string":
                    (meta.getCN().indexOf("(") >0 ? meta.getCN().substring(0, meta.getCN().indexOf("(")) : meta.getCN())).build());
            nameColumns.add(ColumnInfo.builder().name(meta.getEN().replace("`", "")).build());
            sb.append("order:").append(meta.getEN().replace("`","")).append(",");
            params.append(meta.getEN().replace("`", "")).append(",");
            bean.append("private").append(" ").append(convertDbToBeanColumn(meta.getCN())).append(" ").append(StrUtil.toCamelCase(meta.getEN().replace("`",""))).append(";\n");
        }
        System.out.println(loadLabel.toString());
        System.out.println("hiveCreateSql：" + JSONUtil.toJsonStr(columns));
        System.out.println("nameColumns：" + JSONUtil.toJsonStr(nameColumns));
        System.out.println(sb);
        System.out.println(sb.toString().split(",").length);
        System.out.println(params);
        System.out.println("--------bean--------");
        System.out.println(bean);
        return columns;
    }

    private static String convertDbToBeanColumn(String dbColumn) {
        if (dbColumn.startsWith("int")) {
            return "Integer";
        }
        if (dbColumn.startsWith("timestamp")) {
            return "Date";
        }
        if (dbColumn.startsWith("tinyint") || dbColumn.startsWith("smallint")) {
            return "Byte";
        }
        if (dbColumn.startsWith("string")) {
            return "String";
        }
        if (dbColumn.startsWith("double")) {
            return "Double";
        }
        if (dbColumn.startsWith("bigint")) {
            return "Long";
        }
        return dbColumn;
    }

    public static void assembleMeta(List<Meta> metaList, String sql) {
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLCreateTableStatement sqlCreateTableStatement=parser.parseCreateTable();
        List<SQLObject> sqlObjects = sqlCreateTableStatement.getChildren();

        for (SQLObject sqlObject : sqlObjects) {
            if (sqlObject instanceof SQLColumnDefinition) {
                SQLColumnDefinition columnDefinition = ((SQLColumnDefinition) sqlObject);
                Meta meta=new Meta();
                meta.setEN(columnDefinition.getNameAsString());
                //System.out.println("从SQL中获得的字段名："+meta.getEN());
                String metaType=columnDefinition.getDataType().getName();
                List<SQLExpr> arguments = columnDefinition.getDataType().getArguments();
                if (!CollectionUtils.isEmpty(arguments)) {
                    metaType=metaType+"("+((SQLIntegerExpr) arguments.get(0)).getNumber().toString()+")";
                }
                meta.setCN(metaType);
                //System.out.println("从SQL中获得的字段类型："+meta.getCN());
                if (columnDefinition.getComment() != null) {
                    meta.setDes((String) ((SQLCharExpr) columnDefinition.getComment()).getValue());
                    //System.out.println("从SQL中获得的字段描述："+meta.getDes());
                }else{
                    meta.setDes("暂无介绍！");
                    //System.out.println("从SQL中获得的字段描述："+meta.getDes());
                }
                metaList.add(meta);
            }else{
                //System.out.println("类型还是不对！");
            }
        }
    }

    String string = "java -jar tool.jar /Users/pengzhong/Downloads/src /Users/pengzhong/Downloads/target dyxx_ht_month_bill_3" +
            " sessionId=sessionId=user:login:session:505bb22b43764c7b89d0aa0030d83177:2e-74-26-41-bc-da:Chrome:75a50fbee443491c9dbc0530c121e8a4 months='2023-08' account_type,order_id,product_id,order_status,business_type,order_payment_time,order_payment_amount,order_settlement_time,settlement_exchange_rate,sharing_ratio,sharing,platform_technical_service_fee,second_level_team_leader_sharing_ratio,second_level_team_leader_sharing,settlement_income\n";


}
