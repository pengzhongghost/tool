package util;

import cn.hutool.core.util.StrUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.util.CollectionUtils;
import pojo.ColMappingDto;
import pojo.NormalSqlStructureDto;

import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pengzhong
 * @since 2023/6/9
 */
public class JsqlParserUtil {
    /**
     * 表名列表
     */
    private final static ThreadLocal<List<String>> TABLE_NAME_LIST = new ThreadLocal<>();
    /**
     * 查询字段名列表
     */
    private final static ThreadLocal<List<String>> COLUMN_NAME_LIST = new ThreadLocal<>();
    /**
     * 表别名映射关系
     */
    private final static ThreadLocal<Map<String, Object>> TABLE_AND_ALIAS_MAPPING = new ThreadLocal<>();
    /**
     * 字段映射关系列表
     */
    private final static ThreadLocal<List<ColMappingDto>> COL_MAPPING_LIST = new ThreadLocal<>();

    public static void main(String[] args) throws JSQLParserException {
        //1、获取原始sql输入
//        String sql = "select t1.s_id as id," +
//                "t1.s_name," +
//                "t1.join_date, \n" +
//                "t2.score, \n" +
//                "t2.* \n" +
//                "from schema1.edu_college_student t1\n" +
//                "join schema2.edu_college_test_score t2\n" +
//                "on t2.s_id = t1.s_id \n" +
//                "where 1=1 \n";

//        String sql = "select t11.*,t1.* \n" +
//                "from original_data.edu_college_student As t1\n" +
//                "JOIN original_data.edu_college_test_score t11\n" +
//                "on t1.s_id = t11.s_id \n" +
//                "where 1=1 \n";

//        String sql = "select t1.*,t1.*,t2.*\n" +
//                "from edu_college_student t1\n" +
//                "join edu_college_test_score t2 on t2.s_id = t1.s_id";

//        String sql = "select '1' from meta_dict_type";

//        String sql = "select\n" +
//                "  t1.s_id,\n" +
//                "  t1.s_name,\n" +
//                "  max(t2.score) as maxscore,\n" +
//                "  t2.course\n" +
//                "from\n" +
//                "  original_data.edu_college_student t1\n" +
//                "  join original_data.edu_college_test_score t2 on t2.s_id = t1.s_id\n" +
//                "group by\n" +
//                "  t2.course,\n" +
//                "  t1.s_id,\n" +
//                "  t1.s_name";

//        String sql = "select t2.id from (select t1.id from (select id from original_data.edu_college_student) t1) t2";

//        String sql = "select t1.stime,t1.sscore from (select o.create_time as stime,t.score as sscore from original_data.edu_college_student o join original_data.edu_college_test_score t on t.s_id = o.s_id ) t1";

//        String sql = "select t1.s_id as sid, t1.t1.s_name from original_data.edu_college_student t1";

//        String sql = "select\n" +
//                "  v1.id as t_id,\n" +
//                "  v1.s_name as t_s_name,\n" +
//                "  v1.join_date as t_date,\n" +
//                "  v1.score As t_score,\n" +
//                "  t3.course AS t_course\n" +
//                "from\n" +
//                "  (\n" +
//                "    select\n" +
//                "      t1.s_id as id,\n" +
//                "      t1.s_name,\n" +
//                "      t1.join_date,\n" +
//                "      t2.score\n" +
//                "    from\n" +
//                "      original_data.edu_college_student t1\n" +
//                "      join original_data.edu_college_test_score t2 on t2.s_id = t1.s_id\n" +
//                "    where\n" +
//                "      1 = 1\n" +
//                "  ) v1\n" +
//                "  join original_data.edu_college_sign_in_situation t3 on t3.s_id = v1.id\n" +
//                "  limit 10";

//        String sql = "select '正常签到' as '签到情况',sum(1) as '次数' from `original_data`.hr_attendance_summary_day where is_early = 0 and is_later = 0 \n" +
//                "union all \n" +
//                "SELECT '迟到' AS '签到情况',if(SUM(is_later)is null,0,SUM(is_later)) AS '次数' FROM `original_data`.hr_attendance_summary_day WHERE is_later = 1\n" +
//                "union all \n" +
//                "SELECT '早退' AS '签到情况',if(SUM(is_early)is null,0,SUM(is_early)) AS '次数' FROM `original_data`.hr_attendance_summary_day WHERE is_early = 1";

        String sql = "SELECT\n" +
                "  total_amount,\n" +
                "  condition_time,\n" +
                "  platform_product_name,\n" +
                "  shop_name,\n" +
                "  talent_commission_rate,\n" +
                "  price,\n" +
                "  category_name,\n" +
                "  first_cid,\n" +
                "  second_cid,\n" +
                "  third_cid,\n" +
                "  shop_id,\n" +
                "  avg_day_count,\n" +
                "  seven_day_count,\n" +
                "  seven_day_amount,\n" +
                "  cover\n" +
                "FROM\n" +
                "  (\n" +
                "    SELECT\n" +
                "      total_amount,\n" +
                "      a.platform_product_id condition_time,\n" +
                "      platform_product_name,\n" +
                "      a.shop_name,\n" +
                "      a.talent_commission_rate,\n" +
                "      a.price,\n" +
                "      a.category_name,\n" +
                "      a.first_cid,\n" +
                "      a.second_cid,\n" +
                "      a.third_cid,\n" +
                "      a.shop_id,\n" +
                "      b.avg_day_count,\n" +
                "      c.seven_day_count,\n" +
                "      c.seven_day_amount,\n" +
                "      cover\n" +
                "    FROM\n" +
                "      (\n" +
                "        SELECT\n" +
                "          sum(a.pay_amount) total_amount,\n" +
                "          a.platform_product_id platform_product_id,\n" +
                "          max(a.platform_product_name) platform_product_name,\n" +
                "          max(a.shop_name) shop_name,\n" +
                "          (\n" +
                "            case\n" +
                "              when min(a.talent_commission_rate) = 0 then max(a.talent_commission_rate)\n" +
                "              else min(a.talent_commission_rate)\n" +
                "            end\n" +
                "          ) AS talent_commission_rate,\n" +
                "          max(b.cover) cover,\n" +
                "          max(b.price) price,\n" +
                "          max(b.category_name) category_name,\n" +
                "          max(b.platform_second_cid) second_cid,\n" +
                "          max(b.platform_third_cid) third_cid,\n" +
                "          max(b.douyin_first_cid) first_cid,\n" +
                "          max(b.shop_id) shop_id\n" +
                "        FROM\n" +
                "          redu_order a\n" +
                "          left JOIN redu_product b ON a.platform_product_id = b.platform_product_id\n" +
                "        WHERE\n" +
                "          (\n" +
                "            a.pid IS NOT null\n" +
                "            OR a.redu_douke_buyin_id IS NOT null\n" +
                "          )\n" +
                "          AND a.platform_product_id NOT IN (\n" +
                "            SELECT\n" +
                "              platform_product_id\n" +
                "            FROM\n" +
                "              redu_product\n" +
                "            WHERE\n" +
                "              platform = 'douyin'\n" +
                "              AND sale_status = 1\n" +
                "              AND optimal_activity_id IS NOT NULL\n" +
                "          )\n" +
                "        GROUP BY\n" +
                "          a.platform_product_id\n" +
                "      ) a\n" +
                "      JOIN(\n" +
                "        SELECT\n" +
                "          round(avg(day_count), 2) avg_day_count,\n" +
                "          a.platform_product_id\n" +
                "        FROM\n" +
                "          (\n" +
                "            SELECT\n" +
                "              a.platform_product_id platform_product_id,\n" +
                "              COUNT(*) day_count\n" +
                "            FROM\n" +
                "              redu_order a\n" +
                "              left JOIN redu_product b ON a.platform_product_id = b.platform_product_id\n" +
                "            WHERE\n" +
                "              (\n" +
                "                a.pid IS NOT null\n" +
                "                OR a.redu_douke_buyin_id IS NOT null\n" +
                "              )\n" +
                "              AND a.platform_product_id NOT IN (\n" +
                "                SELECT\n" +
                "                  platform_product_id\n" +
                "                FROM\n" +
                "                  redu_product\n" +
                "                WHERE\n" +
                "                  platform = 'douyin'\n" +
                "                  AND sale_status = 1\n" +
                "                  AND optimal_activity_id IS NOT NULL\n" +
                "              )\n" +
                "            GROUP BY\n" +
                "              a.platform_product_id,\n" +
                "              date_format(a.paid_time, '%Y-%m-%d')\n" +
                "          ) a\n" +
                "        GROUP BY\n" +
                "          platform_product_id\n" +
                "      ) b ON a.platform_product_id = b.platform_product_id\n" +
                "      JOIN (\n" +
                "        SELECT\n" +
                "          COUNT(*) seven_day_count,\n" +
                "          sum(a.pay_amount) seven_day_amount,\n" +
                "          a.platform_product_id platform_product_id\n" +
                "        FROM\n" +
                "          redu_order a\n" +
                "          left JOIN redu_product b ON a.platform_product_id = b.platform_product_id\n" +
                "        WHERE\n" +
                "          (\n" +
                "            a.pid IS NOT null\n" +
                "            OR a.redu_douke_buyin_id IS NOT null\n" +
                "          )\n" +
                "          AND a.platform_product_id NOT IN (\n" +
                "            SELECT\n" +
                "              platform_product_id\n" +
                "            FROM\n" +
                "              redu_product\n" +
                "            WHERE\n" +
                "              platform = 'douyin'\n" +
                "              AND sale_status = 1\n" +
                "              AND optimal_activity_id IS NOT NULL\n" +
                "          )\n" +
                "          AND UNIX_TIMESTAMP(CURDATE(), '%Y-%m-%d') - UNIX_TIMESTAMP(a.paid_time, '%Y-%m-%d') <= 604800\n" +
                "        GROUP BY\n" +
                "          a.platform_product_id\n" +
                "      ) c on a.platform_product_id = c.platform_product_id\n" +
                "  ) a\n" +
                "WHERE\n" +
                "  a.seven_day_count > 50";

        try {
            getStructure(sql.replaceAll("[\r\n]", " "), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SQL结构
     *
     * @param sql SQL语句
     * @throws JSQLParserException 解析异常
     */
    public static NormalSqlStructureDto getStructure(String sql, boolean isAlias) throws JSQLParserException {
        System.out.println("【START】");
        TABLE_NAME_LIST.set(new ArrayList<>());
        COLUMN_NAME_LIST.set(new ArrayList<>());
        TABLE_AND_ALIAS_MAPPING.set(new HashMap<>());
        COL_MAPPING_LIST.set(new ArrayList<>());
        NormalSqlStructureDto normalSqlStructureDto = new NormalSqlStructureDto();
        if (StrUtil.isEmpty(sql)) {
            throw new RuntimeException("请先输入SQL语句");
        }
        normalSqlStructureDto.setSql(sql);
        sql = sql.replaceAll("(\\$\\{\\w*\\})|(\\{\\{\\w+\\}\\})", "''");
        analysisSql(sql, isAlias, false);
        normalSqlStructureDto.setSelectItems(COLUMN_NAME_LIST.get());
        normalSqlStructureDto.setTableNames(TABLE_NAME_LIST.get());
        normalSqlStructureDto.setTableAliasMapping(TABLE_AND_ALIAS_MAPPING.get());
        List<ColMappingDto> colMappingDtoList = COL_MAPPING_LIST.get();
        for (ColMappingDto mapping : colMappingDtoList) {
            if (Objects.isNull(mapping.getTable()) && Objects.nonNull(mapping.getTableAlias())) {
                mapping.setTable(TABLE_AND_ALIAS_MAPPING.get().get(mapping.getTableAlias()));
            }
        }
        normalSqlStructureDto.setColMappings(colMappingDtoList);

        System.out.println("【END】");
        return normalSqlStructureDto;
    }

    /**
     * 解析SQL
     *
     * @param sql         SQL语句
     * @param isAlias     true|false 是否使用别称<br> eg. 【s_id as id】 => 【id】<br>
     * @param isSubSelect 是否是子查询
     * @throws JSQLParserException 解析异常
     */
    private static void analysisSql(String sql, boolean isAlias, boolean isSubSelect) throws JSQLParserException {
        //logger.info("是否是子查询: " + isSubSelect);
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        // 解析SQL为Statement对象
        Statement statement = parserManager.parse(new StringReader(sql));
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = select.getSelectBody();
        // 判断是否是union查询
        if(selectBody instanceof SetOperationList){
            SetOperationList operationList = (SetOperationList) select.getSelectBody();
            List<SelectBody> plainSelects = operationList.getSelects();
            for (SelectBody plainSelect : plainSelects) {
                analysisSql(plainSelect.toString(), isAlias, isSubSelect);
            }
        } else if(selectBody instanceof PlainSelect){
            analysisSelectBody(isAlias, isSubSelect, statement, select);
        }
    }

    /**
     * 解析SelectBody
     *
     * @param isAlias true|false 是否使用别称<br> eg. 【s_id as id】 => 【id】<br>
     * @param isSubSelect 是否是子查询
     * @param statement Statement对象
     * @param select Select对象
     * @throws JSQLParserException 解析异常
     */
    private static void analysisSelectBody(boolean isAlias, boolean isSubSelect, Statement statement, Select select) throws JSQLParserException {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        // 1.解析表名
        List<String> tableNameList = getTable(statement);
        // 表别名映射
        Map<String, Object> tableMapping = new HashMap<>();
        tableNameList.forEach(i -> tableMapping.put(i, i));
        if (CollectionUtils.isEmpty(TABLE_AND_ALIAS_MAPPING.get())) {
            TABLE_AND_ALIAS_MAPPING.get().putAll(tableMapping);
        }
        if (CollectionUtils.isEmpty(TABLE_NAME_LIST.get())) {
            TABLE_NAME_LIST.get().addAll(tableNameList);
        }
        // 字段和表的映射
        List<ColMappingDto> colMappingList = new ArrayList<>();

        // 2.解析查询元素 列，函数等
        getSelectItems(plainSelect, tableNameList, tableMapping, colMappingList, isAlias, isSubSelect);

        // 3.解析from（可能含有子查询）
        FromItem fromItem = plainSelect.getFromItem();
        String fromTable = getFromItem(fromItem, isAlias);
        System.out.println("from 表名：" + fromTable);

        // 4.解析join
        List<Join> tablewithjoin = getJoinItem(plainSelect);
        if (!CollectionUtils.isEmpty(tablewithjoin)) {
            tablewithjoin.forEach(i -> System.out.println("连接方式为：" + i));
        }
    }

    /**
     * 获取join的项目
     *
     * @param plainSelect
     * @return
     */
    private static List<Join> getJoinItem(PlainSelect plainSelect) {
        // 如果关联后面是子查询，可以通过遍历join集合，获取FromItem rightItem = join.getRightItem();
        List<Join> joinList = plainSelect.getJoins();
        if (joinList != null) {
            for (int i = 0; i < joinList.size(); i++) {
                //注意 ， leftjoin rightjoin 等等的to string()区别
                Join join = joinList.get(i);
                String alias = join.getRightItem().getAlias().toString().trim();
                String tableName = join.getRightItem().toString().replaceAll("(?i)\\s+as\\s+", " ").replace(alias, "").trim();
                //logger.info("join 表名：" + join.getRightItem().toString());
                //logger.info("物理名：" + tableName);
                //logger.info("别名：" + alias);
                TABLE_AND_ALIAS_MAPPING.get().put(alias, tableName);
            }
        }
        return joinList;
    }

    /**
     * 获取from的项目
     *
     * @param fromItem
     * @return
     * @throws JSQLParserException 解析异常
     */
    private static String getFromItem(FromItem fromItem, boolean isAlias) throws JSQLParserException {
        // 判断fromItem属于哪种类型，如果是subSelect类型就是子查询
        if (fromItem instanceof SubSelect) {
            System.out.println("-----------------子查询开始-----------------");
            SelectBody selectBody = ((SubSelect) fromItem).getSelectBody();
            System.out.println("子查询" + selectBody.toString());
            analysisSql(selectBody.toString(), true, true);
            System.out.println("-----------------子查询结束-----------------");
        }
        String alias = "";
        try {
            alias = fromItem.getAlias().toString().trim();
            Table table = ((Table) fromItem);
            String tableName = table.getName();
            String schemaName = table.getSchemaName();
            schemaName = StrUtil.isEmpty(schemaName) ? "" : schemaName;
            String name = schemaName + "." + tableName;
            System.out.println("物理名：" + name);
            System.out.println("别名：" + alias);

            TABLE_AND_ALIAS_MAPPING.get().put(alias, name);
        } catch (Exception e) {
            if (StrUtil.isNotEmpty(alias)) {
                TABLE_AND_ALIAS_MAPPING.get().put(alias, fromItem.toString());
            }
        } finally {
            return fromItem.toString();
        }

    }

    /**
     * 获取当前查询字段
     *
     * @param plainSelect
     * @param tableNameList
     * @param tableMapping
     * @param colMappingList
     */
    private static void getSelectItems(PlainSelect plainSelect, List<String> tableNameList, Map<String, Object> tableMapping, List<ColMappingDto> colMappingList, boolean isAlias, boolean isSubSelect) {
        // 目前不解析子查询
        if (isSubSelect) {
            return;
        }
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        List<String> columnList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(selectItems)) {
            for (SelectItem selectItem : selectItems) {
                ColMappingDto colMapping = new ColMappingDto();
                // 字段名称
                String columnName = "";
                // 表别名
                String tblAlias = "";
                try {
                    if (selectItem instanceof SelectExpressionItem) {
                        SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                        Alias alias = selectExpressionItem.getAlias();
                        Expression expression = selectExpressionItem.getExpression();
                        Column col = ((Column) expression);
                        Table colTbl = col.getTable();
                        if (Objects.nonNull(colTbl)) {
                            tblAlias = colTbl.getName();
                        }
                        if (!isAlias) {
                            columnName = selectItem.toString();
                        } else if (expression instanceof CaseExpression) {
                            // case表达式
                            columnName = alias.getName();
                        } else if (expression instanceof LongValue || expression instanceof StringValue || expression instanceof DateValue || expression instanceof DoubleValue) {
                            // 值表达式
                            columnName = Objects.nonNull(alias.getName()) ? alias.getName() : expression.getASTNode().jjtGetValue().toString();
                        } else if (expression instanceof TimeKeyExpression) {
                            // 日期
                            columnName = alias.getName();
                        } else {
                            if (alias != null) {
                                columnName = alias.getName();
                            } else {
                                SimpleNode node = expression.getASTNode();
                                Object value = node.jjtGetValue();
                                if (value instanceof Column) {
                                    columnName = ((Column) value).getColumnName();
                                } else if (value instanceof Function) {
                                    columnName = value.toString();
                                } else {
                                    // 增加对select 'aaa' from table; 的支持
                                    columnName = String.valueOf(value);
                                    columnName = getString(columnName);
                                }
                            }
                        }

                        columnName = getString(columnName);

                        colMapping.setName(col.getColumnName());
                        if (Objects.nonNull(alias) && StrUtil.isNotEmpty(alias.getName())) {
                            colMapping.setAlias(alias.getName());
                        }
//                        colMapping.setTable(tableMapping.get(tblAlias));
                        colMapping.setTableAlias(tblAlias);

                    } else if (selectItem instanceof AllTableColumns) {
                        AllTableColumns allTableColumns = (AllTableColumns) selectItem;
                        columnName = allTableColumns.toString();
                        if (columnName.indexOf(".") > -1) {
                            tblAlias = columnName.substring(0, columnName.indexOf(".")).trim();
//                            buildTblMapping(tableMapping, sql, tblAlias);
//                            colMapping.setTable(tableMapping.get(tblAlias));
                            colMapping.setTableAlias(tblAlias);
                        } else {
                            colMapping.setTable(tableNameList);
                        }
                        colMapping.setName(columnName);
                    } else if (selectItem.toString().equals("*")) {
                        columnName = selectItem.toString();
                        colMapping.setName(columnName);
                        colMapping.setTable(tableNameList);
                    } else {
                        columnName = selectItem.toString();
                        colMapping.setName(columnName);
                    }
                } catch (Exception e) {
                    columnName = selectItem.toString();
                    colMapping.setName(columnName);
                    colMapping.setTable(null);
                    if (columnName.matches("(?i).+\\s+as\\s+.+")) {
                        colMapping.setAlias(columnName.replaceAll("(?i).+\\s+as\\s+", "").trim());
                    }
                }

                columnList.add(columnName);
                colMappingList.add(colMapping);
                if (!isSubSelect) {
                    COL_MAPPING_LIST.get().add(colMapping);
                    COLUMN_NAME_LIST.set(columnList);
                }
            }
            System.out.println("查询字段名：" + columnList.toString());
        }
    }

    private static String getString(String columnName) {
        columnName = columnName.replace("'", "");
        columnName = columnName.replace("\"", "");
        columnName = columnName.replace("`", "");
        return columnName;
    }

    /**
     * 获取SQL中所有出现的表
     *
     * @param statement
     * @return
     */
    private static List<String> getTable(Statement statement) {
        // 创建表名发现者对象
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        // 获取到表名列表
        List<String> tableNameList = tablesNamesFinder.getTableList(statement);
        //logger.info("查询表名：" + tableNameList.toString());

        return tableNameList;
    }

    /**
     * 构建表名和表别名的对应关系
     *
     * @param tableMapping
     * @param sql
     * @param tblAlias
     */
    private static void buildTblMapping(Map<String, Object> tableMapping, String sql, String tblAlias) {
        if (StrUtil.isNotEmpty(tblAlias)) {
            if (CollectionUtils.isEmpty(tableMapping) || Objects.isNull(tableMapping.get(tblAlias))) {
                sql = sql.replaceAll("(?i)\\s+as\\s+", " ");
                String regex = "(from|join)\\s+(\\w+\\.)?\\w+\\s+".concat(tblAlias).concat("\\s?");
                Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(sql.replaceAll("[\n\r]", " "));
                String replaceReg = "(?i)(from|join|" + tblAlias + ")";
                while (m.find()) {
                    tableMapping.put(tblAlias, m.group(0).replaceAll(replaceReg, "").trim());
                }
            }
        }
    }
}

