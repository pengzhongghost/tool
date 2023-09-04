package tool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

/**
 * @author pengzhong
 * @since 2023/6/9
 */
public class JsqlParser {

    public static final List<String> needReplaceTables = List.of("cdouyin_order_pay", "cdouyin_order_refund", "cdouyin_order_settle");

    public static void main(String[] args) throws JSQLParserException {
        dfsParse("select\n" +
                "  s.platform as platform_name,\n" +
                "  a.orderid,\n" +
                "  from_unixtime(a.paid_time, '%Y-%m-%d') as paid_time,\n" +
                "  a.settlement_time,\n" +
                "  a.productid,\n" +
                "  a.shortid,\n" +
                "  ifNull(a.pay_goods_amount, a.pay_amount) as real_pay_amount,\n" +
                "  a.settlement_amount,\n" +
                "  a.achievements_order_multiple,\n" +
                "  a.channel_id,\n" +
                "  a.partner_id,\n" +
                "  a.shopname,\n" +
                "  a.estimate_service_income,\n" +
                "  a.actual_service_income,\n" +
                "  a.productname,\n" +
                "  a.nickname,\n" +
                "  s.shopid_no as shopid,\n" +
                "  a.channel_talent_id,\n" +
                "  b.estimate_service_income as refund_estimate_service_income,\n" +
                "  b.shortid as refund_shortid,\n" +
                "  b.pay_amount,\n" +
                "  b.id as refund_id,\n" +
                "  c.pay_amount,\n" +
                "  c.id as settle_id,\n" +
                "  product_count,\n" +
                "  creadit_logistics,\n" +
                "  creadit_service,\n" +
                "  creadit_product,\n" +
                "  case\n" +
                "    when share_count > 0 then '是'\n" +
                "    else '否'\n" +
                "  end as is_share,\n" +
                "  s.name as shop_name\n" +
                "from\n" +
                "  cdouyin_order_pay a\n" +
                "  left join cdouyin_order_refund b on a.orderid = b.orderid\n" +
                "  left join cdouyin_order_settle c on b.orderid = c.orderid\n" +
                "  left join shop s on s.id = a.shop_id\n" +
                "  left join (\n" +
                "    select\n" +
                "      count(*) as product_count,\n" +
                "      shop_id\n" +
                "    from\n" +
                "      product\n" +
                "    group by\n" +
                "      product.shop_id\n" +
                "  ) d on d.shop_id = s.id\n" +
                "  left join (\n" +
                "    select\n" +
                "      count(*) as share_count,\n" +
                "      shop_id\n" +
                "    from\n" +
                "      shop_share\n" +
                "    group by\n" +
                "      shop_id\n" +
                "  ) e on e.shop_id = d.shop_id");
    }

    public static void dfsParse(String sql) throws JSQLParserException {
        // 使用工具类把SQL转换为Select对象
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        //普通查询，查询所有字段
        //最外层的查询
        PlainSelect outSelect = (PlainSelect) select.getSelectBody();
        if (outSelect.getFromItem() instanceof Table) {
            cdouyinOrderPay(outSelect);
            //mdouyin_order
            mdouyinOrder(outSelect);
            //ckuaishou_order
            ckuaishouOrder(outSelect);
        } else {
            //子查询
            SubSelect subSelect = (SubSelect) outSelect.getFromItem();
            if (subSelect.getSelectBody() instanceof PlainSelect) {
                PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
                dfs(plainSelect);
            } else if (subSelect.getSelectBody() instanceof SetOperationList) {
                SetOperationList setOperationList = (SetOperationList) subSelect.getSelectBody();
                if (null != setOperationList) {
                    for (SelectBody selectBody : setOperationList.getSelects()) {
                        dfs((PlainSelect) selectBody);
                    }
                }
            }
            //关联查询
            if (null != outSelect.getJoins()) {
                for (Join join : outSelect.getJoins()) {
                    if (join.getRightItem() instanceof  SubSelect) {
                        SubSelect joinSelect = (SubSelect) join.getRightItem();
                        dfs((PlainSelect) joinSelect.getSelectBody());
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(outSelect.getJoins())) {
            for (Join join : outSelect.getJoins()) {
                FromItem fromItem = join.getRightItem();
                if (fromItem instanceof SubSelect) {
                    PlainSelect plainSelect = (PlainSelect) ((SubSelect) fromItem).getSelectBody();
                    dfs(plainSelect);
                }
            }
        }
        System.out.println(select.toString()
                //.replace("from_unixtime", "date_format")
                .replace("pay_success_time", "paid_time")
                .replace("paid_time_date", "paid_time"));
    }

    public static void dfs(PlainSelect plainSelect) {
        //子查询
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            SelectBody select = subSelect.getSelectBody();
            if (select instanceof PlainSelect) {
                PlainSelect selectBody = (PlainSelect) subSelect.getSelectBody();
                handle(selectBody);
            } else if (select instanceof SetOperationList) {
                SetOperationList operationList = (SetOperationList) select;
                for (SelectBody selectBody : operationList.getSelects()) {
                    handle((PlainSelect) selectBody);
                }
            }
        } else if (fromItem instanceof Table) {
            cdouyinOrderPay(plainSelect);
            product(plainSelect);
            product_category(plainSelect);
            ckuaishouOrder(plainSelect);
            mdouyinOrder(plainSelect);
        }
    }

    private static void handle(PlainSelect selectBody) {
        //cdouyin_order_pay
        cdouyinOrderPay(selectBody);
        //product
        product(selectBody);
        //product_category
        product_category(selectBody);
        //mdouyin_order
        mdouyinOrder(selectBody);
        //ckuaishou_order
        ckuaishouOrder(selectBody);
        //替换关联字段
        List<Join> joinList = selectBody.getJoins();
        if (CollUtil.isNotEmpty(joinList)) {
            for (Join join : joinList) {
//                    EqualsTo equalsTo = (EqualsTo) join.getOnExpression();
//                    Column leftColumn = (Column)equalsTo.getLeftExpression();
//                    cdouyinReplaceColumnInfo(leftColumn);
//                    productColumnInfo(leftColumn);
//                    Column rightColumn = (Column)equalsTo.getRightExpression();
//                    cdouyinReplaceColumnInfo(rightColumn);
//                    productColumnInfo(rightColumn);
                if (join.getRightItem() instanceof SubSelect) {
                    dfs((PlainSelect) ((SubSelect) join.getRightItem()).getSelectBody());
                }
            }
        }
        dfs(selectBody);
    }

    private static void cdouyinOrderPay(PlainSelect selectBody) {
        if (selectBody.getFromItem() instanceof Table) {
            Table table = (Table) selectBody.getFromItem();
            if (needReplaceTables.contains(table.getName())) {
                for (SelectItem selectItem : selectBody.getSelectItems()) {
                    SelectExpressionItem expressionItem = (SelectExpressionItem) selectItem;
                    Expression innerExpression = expressionItem.getExpression();
                    String alias = null;
                    //1.使用的是函数
                    if (innerExpression instanceof Function) {
                        Function function = (Function) innerExpression;
                        ExpressionList expressionList = function.getParameters();
                        if ("ifnull".equalsIgnoreCase(function.getName())) {
                            List<Expression> expressions = function.getParameters().getExpressions();
                            if (CollUtil.isNotEmpty(expressions)) {
                                if (expressions.get(0) instanceof Column) {
                                    Column inner = (Column) expressions.get(0);
                                    if ("pay_goods_amount".equalsIgnoreCase(inner.getColumnName())) {
                                        Column column = new Column();
                                        column.setColumnName("estimate_settlement_amount");
                                        ExpressionList list = new ExpressionList();
                                        list.addExpressions(column);
                                        function.setParameters(list);
                                    }
                                }
                            }
                        }
                        if ("sum".equalsIgnoreCase(function.getName())) {
                            Expression expression = function.getParameters().getExpressions().get(0);
                            if (expression instanceof Function) {
                                Function innerFunction = (Function) expression;
                                if ("ifnull".equalsIgnoreCase(innerFunction.getName())) {
                                    List<Expression> expressions = innerFunction.getParameters().getExpressions();
                                    if (CollUtil.isNotEmpty(expressions)) {
                                        Column inner = (Column) expressions.get(0);
                                        if ("pay_goods_amount".equalsIgnoreCase(inner.getColumnName())) {
                                            Column column = new Column();
                                            column.setColumnName("estimate_settlement_amount");
                                            ExpressionList list = new ExpressionList();
                                            list.addExpressions(column);
                                            function.setParameters(list);
                                        }
                                    }

                                }
                            }
                        }
                        if ("from_unixtime".equalsIgnoreCase(function.getName())) {
                            function.setName("date_format");
                        }
                        if ("UNIX_TIMESTAMP".equalsIgnoreCase(function.getName())) {
                            function.setName("date_format");
                        }
                        if (null != expressionList) {
                            for (Expression expression : expressionList.getExpressions()) {
                                if (expression instanceof Column) {
                                    Column column = (Column) expression;
                                    cdouyinReplaceColumnInfo(column);
                                }
                                if (expression instanceof Parenthesis) {
                                    Column column = (Column) ((Parenthesis) expression).getExpression();
                                    cdouyinReplaceColumnInfo(column);
                                }
                            }
                        }
                    } else {
                        //2.只有字段
                        if (innerExpression instanceof Column) {
                            Column column = (Column) innerExpression;
                            if ("paid_time".equalsIgnoreCase(column.getColumnName())) {
                                Function function = new Function();
                                function.setName("unix_timestamp");
                                ExpressionList expressionList = new ExpressionList();
                                expressionList.addExpressions(new Column("paid_time"));
                                function.setParameters(expressionList);
                                expressionItem.setExpression(function);
                                expressionItem.setAlias(new Alias("paid_time"));
                            }
                            if ("settlement_time".equalsIgnoreCase(column.getColumnName())) {
                                Function function = new Function();
                                function.setName("unix_timestamp");
                                ExpressionList expressionList = new ExpressionList();
                                expressionList.addExpressions(new Column("settlement_time"));
                                function.setParameters(expressionList);
                                expressionItem.setExpression(function);
                                expressionItem.setAlias(new Alias("settlement_time"));
                            }
                            alias = cdouyinReplaceColumnInfo(column);
                        }
                    }
                    if (StrUtil.isNotEmpty(alias)) {
                        if (null == expressionItem.getAlias()) {
                            expressionItem.setAlias(new Alias(alias));
                        } else {
                            expressionItem.getAlias().setName(alias);
                        }
                    }
                }
                Expression where = selectBody.getWhere();
                AndExpression andExpression = new AndExpression();
                if (null != where) {
                    if (where instanceof GreaterThan) {
                        GreaterThan greaterThan = (GreaterThan) where;
                        Expression rightExpression = greaterThan.getRightExpression();
                        if (rightExpression instanceof Function) {
                            Function function = (Function) rightExpression;
                            function.setName("date_format");
                        }
                    }
                    andExpression.withLeftExpression(where);
                }
                if ("cdouyin_order_refund".equals(table.getName())) {
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column("order_status"));
                    equalsTo.setRightExpression(new HexValue("4"));
                    if (null != andExpression.getRightExpression()) {
                        andExpression = new AndExpression().withLeftExpression(andExpression);
                    }
                    if (null == andExpression.getLeftExpression()) {
                        andExpression.withLeftExpression(equalsTo);
                    } else {
                        andExpression.withRightExpression(equalsTo);
                    }
                }
                if ("cdouyin_order_settle".equals(table.getName())) {
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column("order_status"));
                    equalsTo.setRightExpression(new HexValue("3"));
                    if (null != andExpression.getRightExpression()) {
                        andExpression = new AndExpression().withLeftExpression(andExpression);
                    }
                    if (null == andExpression.getLeftExpression()) {
                        andExpression.withLeftExpression(equalsTo);
                    } else {
                        andExpression.withRightExpression(equalsTo);
                    }
                }
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("platform_type"));
                equalsTo.setRightExpression(new HexValue("1"));
                if (null != andExpression.getRightExpression()) {
                    andExpression = new AndExpression().withLeftExpression(andExpression);
                }
                if (null == andExpression.getLeftExpression()) {
                    andExpression.withLeftExpression(equalsTo);
                } else {
                    andExpression.withRightExpression(equalsTo);
                }
                selectBody.setWhere(andExpression);
                if (null != selectBody.getGroupBy()) {
                    for (Expression groupByExpression : selectBody.getGroupBy().getGroupByExpressions()) {
                        if (groupByExpression instanceof Function) {
                            Function function = (Function) groupByExpression;
                            if ("from_unixtime".equalsIgnoreCase(function.getName())) {
                                function.setName("date_format");
                            }
                        }
                    }
                }
                table.setName("redu_order");

            }
        }
    }

    private static void mdouyinOrder(PlainSelect selectBody) {
        if (selectBody.getFromItem() instanceof Table) {
            Table table = (Table) selectBody.getFromItem();
            if ("mdouyin_order".equalsIgnoreCase(table.getName())) {
                for (SelectItem selectItem : selectBody.getSelectItems()) {
                    SelectExpressionItem expressionItem = (SelectExpressionItem) selectItem;
                    Expression innerExpression = expressionItem.getExpression();
                    String alias = null;
                    //1.使用的是函数
                    if (innerExpression instanceof Function) {
                        Function function = (Function) innerExpression;
                        ExpressionList parentExpressionList = function.getParameters();
                        if ("year".equalsIgnoreCase(function.getName()) || "month".equalsIgnoreCase(function.getName()) || "day".equalsIgnoreCase(function.getName())) {
                            ExpressionList expressionList = new ExpressionList();
                            expressionList.addExpressions(new Column("paid_time"));
                            function.setParameters(expressionList);
                        }
                        if ("ifnull".equalsIgnoreCase(function.getName())) {
                            Expression expression = function.getParameters().getExpressions().get(0);
                            if (expression instanceof Division) {
                                Division innerDivision = (Division) expression;
                                Column leftColumn = (Column) innerDivision.getLeftExpression();
                                if ("total_pay_amount".equalsIgnoreCase(leftColumn.getColumnName())) {
                                    ExpressionList expressionList = new ExpressionList();
                                    expressionList.addExpressions(new Column("estimate_settlement_amount"));
                                    function.setParameters(expressionList);
                                }
                            }
                        }
                        if ("sum".equalsIgnoreCase(function.getName())) {
                            Expression expression = function.getParameters().getExpressions().get(0);
                            if (expression instanceof Division) {
                                Division innerDivision = (Division) expression;
                                Column leftColumn = (Column) innerDivision.getLeftExpression();
                                if ("total_pay_amount".equalsIgnoreCase(leftColumn.getColumnName())) {
                                    ExpressionList expressionList = new ExpressionList();
                                    expressionList.addExpressions(new Column("estimate_settlement_amount"));
                                    function.setParameters(expressionList);
                                }
                                if ("pay_goods_amount".equalsIgnoreCase(leftColumn.getColumnName())) {
                                    ExpressionList expressionList = new ExpressionList();
                                    expressionList.addExpressions(new Column("estimate_settlement_amount"));
                                    function.setParameters(expressionList);
                                }
                            } else if (expression instanceof Column) {
                                Column column = (Column) expression;
                                if ("pay_goods_amount".equalsIgnoreCase(column.getColumnName())) {
                                    column.setColumnName("estimate_settlement_amount");
                                }
                            }
                        }
                        if ("max".equalsIgnoreCase(function.getName())) {
                            Expression expression = function.getParameters().getExpressions().get(0);
                            if (expression instanceof Column) {
                                Column column = (Column) expression;
                                if ("pay_goods_amount".equalsIgnoreCase(column.getColumnName())) {
                                    column.setColumnName("estimate_settlement_amount");
                                }
                            }
                        }
                        if (null != parentExpressionList) {
                            for (Expression expression : parentExpressionList.getExpressions()) {
                                if (expression instanceof Column) {
                                    Column column = (Column) expression;
                                    mdouyinReplaceColumnInfo(column);
                                }
                                if (expression instanceof Parenthesis) {
                                    Column column = (Column) ((Parenthesis) expression).getExpression();
                                    mdouyinReplaceColumnInfo(column);
                                }
                            }
                        }
                    } else {
                        //2.只有字段
                        if (innerExpression instanceof Column) {
                            Column column = (Column) innerExpression;
                            alias = mdouyinReplaceColumnInfo(column);
                        }
                    }
                    if (StrUtil.isNotEmpty(alias)) {
                        if (null == expressionItem.getAlias()) {
                            expressionItem.setAlias(new Alias(alias));
                        } else {
                            expressionItem.getAlias().setName(alias);
                        }
                    }
                }
                Expression where = selectBody.getWhere();
                if (where instanceof EqualsTo) {
                    EqualsTo equalsTo = (EqualsTo) where;
                    if(equalsTo.getLeftExpression() instanceof Column){
                        Column column = (Column) equalsTo.getLeftExpression();
                        if ("pay_status".equalsIgnoreCase(column.getColumnName())) {
                            column.setColumnName("order_status");
                            Expression rightExpression = equalsTo.getRightExpression();
                            if (rightExpression instanceof LongValue) {
                                LongValue value = (LongValue) equalsTo.getRightExpression();
                                switch (value.getStringValue()) {
                                    case "0":
                                        value.setStringValue("1");
                                        break;
                                    case "1":
                                        value.setStringValue("3");
                                        break;
                                    case "2":
                                        value.setStringValue("2");
                                        break;
                                }
                            } else if (rightExpression instanceof SignedExpression) {
                                SignedExpression signedExpression = (SignedExpression) rightExpression;
                                if ('-' == signedExpression.getSign()) {
                                    LongValue value = (LongValue) signedExpression.getExpression();
                                    value.setStringValue("4");
                                }
                            }
                        }
                    }
                }
                AndExpression andExpression = new AndExpression();
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("platform_type"));
                equalsTo.setRightExpression(new HexValue("1"));
                if (null != andExpression.getRightExpression()) {
                    andExpression = new AndExpression().withLeftExpression(andExpression);
                }
                if (null == andExpression.getLeftExpression()) {
                    andExpression.withLeftExpression(equalsTo);
                    GreaterThan greaterThan = new GreaterThan();
                    Function function = new Function();
                    function.setName("find_in_set");
                    ExpressionList expressionList = new ExpressionList();
                    expressionList.addExpressions(new Column("2"));
                    expressionList.addExpressions(new Column("applet_peg"));
                    function.setParameters(expressionList);
                    greaterThan.setLeftExpression(function);
                    greaterThan.setRightExpression(new LongValue(0));
                    andExpression.withRightExpression(greaterThan);
                } else {
                    andExpression.withRightExpression(equalsTo);
                    andExpression = new AndExpression().withLeftExpression(andExpression);
                    GreaterThan greaterThan = new GreaterThan();
                    Function function = new Function();
                    function.setName("find_in_set");
                    ExpressionList expressionList = new ExpressionList();
                    expressionList.addExpressions(new Column("2"));
                    expressionList.addExpressions(new Column("applet_peg"));
                    function.setParameters(expressionList);
                    greaterThan.setLeftExpression(function);
                    greaterThan.setRightExpression(new LongValue(0));
                    andExpression.withRightExpression(greaterThan);
                }

                selectBody.setWhere(andExpression);

                GroupByElement groupBy = selectBody.getGroupBy();
                if (null != groupBy) {
                    List<Expression> groupByExpressions = groupBy.getGroupByExpressions();
                    for (Expression groupByExpression : groupByExpressions) {
                        if (groupByExpression instanceof Function) {
                            Function function = (Function) groupByExpression;
                            if ("year".equalsIgnoreCase(function.getName()) || "month".equalsIgnoreCase(function.getName()) || "day".equalsIgnoreCase(function.getName())) {
                                ExpressionList expressionList = new ExpressionList();
                                expressionList.addExpressions(new Column("paid_time"));
                                function.setParameters(expressionList);
                            }
                        }
                    }
                }
                table.setName("redu_order");

            }
        }
    }

    private static String mdouyinReplaceColumnInfo(Column column) {
        switch (column.getColumnName()) {
            case "product_id":
                column.setColumnName("platform_product_id");
                return "product_id";
            case "orderid":
                column.setColumnName("platform_order_id");
                return "orderid";
            case "uid":
                column.setColumnName("shortid");
                return "uid";
            case "shortid":
                column.setColumnName("platform_short_");
                return "shortid";
            default:
                return null;
        }
    }


    private static void ckuaishouOrder(PlainSelect selectBody) {
        if (selectBody.getFromItem() instanceof Table) {
            Table table = (Table) selectBody.getFromItem();
            if ("ckuaishou_order".equalsIgnoreCase(table.getName())) {
                for (SelectItem selectItem : selectBody.getSelectItems()) {
                    SelectExpressionItem expressionItem = (SelectExpressionItem) selectItem;
                    Expression innerExpression = expressionItem.getExpression();
                    String alias = null;
                    //1.使用的是函数
                    if (innerExpression instanceof Function) {
                        Function function = (Function) innerExpression;
                        ExpressionList expressionList = function.getParameters();
                        if ("ifnull".equalsIgnoreCase(function.getName())) {
                            List<Expression> expressions = function.getParameters().getExpressions();
                            if (CollUtil.isNotEmpty(expressions)) {
                                if (expressions.get(0) instanceof Column) {
                                    Column inner = (Column) expressions.get(0);
                                    if ("paid_amount".equalsIgnoreCase(inner.getColumnName())) {
                                        Column column = new Column();
                                        column.setColumnName("estimate_settlement_amount");
                                        ExpressionList list = new ExpressionList();
                                        list.addExpressions(column);
                                        function.setParameters(list);
                                    }
                                }
                            }
                        }
                        if ("sum".equalsIgnoreCase(function.getName())) {
                            Expression expression = function.getParameters().getExpressions().get(0);
                            if (expression instanceof Function) {
                                Function innerFunction = (Function) expression;
                                if ("ifnull".equalsIgnoreCase(innerFunction.getName())) {
                                    List<Expression> expressions = innerFunction.getParameters().getExpressions();
                                    if (CollUtil.isNotEmpty(expressions)) {
                                        Column inner = (Column) expressions.get(0);
                                        if ("paid_amount".equalsIgnoreCase(inner.getColumnName())) {
                                            Column column = new Column();
                                            column.setColumnName("estimate_settlement_amount");
                                            ExpressionList list = new ExpressionList();
                                            list.addExpressions(column);
                                            function.setParameters(list);
                                        }
                                    }

                                }
                            }
                        }
                        if (null != expressionList) {
                            for (Expression expression : expressionList.getExpressions()) {
                                if (expression instanceof Column) {
                                    Column column = (Column) expression;
                                    ckuaishouReplaceColumnInfo(column);
                                }
                                if (expression instanceof Parenthesis) {
                                    Expression express = ((Parenthesis) expression).getExpression();
                                    if (express instanceof Column) {
                                        Column column = (Column) express;
                                        ckuaishouReplaceColumnInfo(column);
                                    }
                                }
                            }
                        }
                    } else {
                        //2.只有字段
                        if (innerExpression instanceof Column) {
                            Column column = (Column) innerExpression;
                            alias = ckuaishouReplaceColumnInfo(column);
                        }
                    }
                    if (StrUtil.isNotEmpty(alias)) {
                        if (null == expressionItem.getAlias()) {
                            expressionItem.setAlias(new Alias(alias));
                        } else {
                            expressionItem.getAlias().setName(alias);
                        }
                    }
                }
                Expression where = selectBody.getWhere();
                if (where instanceof EqualsTo) {
                    EqualsTo equalsTo = (EqualsTo) where;
                    if(equalsTo.getLeftExpression() instanceof Column){
                        Column column = (Column) equalsTo.getLeftExpression();
                        if ("pay_status".equalsIgnoreCase(column.getColumnName())) {
                            column.setColumnName("order_status");
                            Expression rightExpression = equalsTo.getRightExpression();
                            if (rightExpression instanceof LongValue) {
                                LongValue value = (LongValue) equalsTo.getRightExpression();
                                switch (value.getStringValue()) {
                                    case "0":
                                        value.setStringValue("1");
                                        break;
                                    case "1":
                                        value.setStringValue("3");
                                        break;
                                    case "2":
                                        value.setStringValue("2");
                                        break;
                                }
                            } else if (rightExpression instanceof SignedExpression) {
                                SignedExpression signedExpression = (SignedExpression) rightExpression;
                                if ('-' == signedExpression.getSign()) {
                                    LongValue value = (LongValue) signedExpression.getExpression();
                                    value.setStringValue("4");
                                }
                            }
                        }
                    }
                }
                AndExpression andExpression = new AndExpression();
                if ("cdouyin_order_refund".equals(table.getName())) {
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column("order_status"));
                    equalsTo.setRightExpression(new HexValue("4"));
                    if (null != andExpression.getRightExpression()) {
                        andExpression = new AndExpression().withLeftExpression(andExpression);
                    }
                    if (null == andExpression.getLeftExpression()) {
                        andExpression.withLeftExpression(equalsTo);
                    } else {
                        andExpression.withRightExpression(equalsTo);
                    }
                }
                if ("cdouyin_order_settle".equals(table.getName())) {
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column("order_status"));
                    equalsTo.setRightExpression(new HexValue("3"));
                    if (null != andExpression.getRightExpression()) {
                        andExpression = new AndExpression().withLeftExpression(andExpression);
                    }
                    if (null == andExpression.getLeftExpression()) {
                        andExpression.withLeftExpression(equalsTo);
                    } else {
                        andExpression.withRightExpression(equalsTo);
                    }
                }
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("platform_type"));
                equalsTo.setRightExpression(new HexValue("2"));
                if (null != andExpression.getRightExpression()) {
                    andExpression = new AndExpression().withLeftExpression(andExpression);
                }
                if (null == andExpression.getLeftExpression()) {
                    andExpression.withLeftExpression(equalsTo);
                } else {
                    andExpression.withRightExpression(equalsTo);
                }
                selectBody.setWhere(andExpression);
                table.setName("redu_order");

            }
        }
    }

    /**
     * @param column
     * @return alias name
     */
    public static String ckuaishouReplaceColumnInfo(Column column) {
        switch (column.getColumnName()) {
            case "product_id":
                column.setColumnName("platform_product_id");
                return "product_id";
            case "orderid":
                column.setColumnName("platform_order_id");
                return "orderid";
            case "actual_service_income":
                column.setColumnName("final_service_income");
                return "actual_service_income";
            case "paid_amount":
                column.setColumnName("estimate_settlement_amount");
                return "paid_amount";
            case "shop_id":
                column.setColumnName("platform_shop_id");
                return "shop_id";
            case "shopname":
                column.setColumnName("shop_name");
                return "shopname";
            case "productname":
                column.setColumnName("platform_product_name");
                return "productname";
            case "productid":
                column.setColumnName("platform_product_id");
                return "productid";
            case "shopid":
                column.setColumnName("platform_shop_id");
                return "shopid";
            default:
                return null;
        }
    }


    /**
     * @param column
     * @return alias name
     */
    public static String cdouyinReplaceColumnInfo(Column column) {
        switch (column.getColumnName()) {
            case "product_id":
                column.setColumnName("platform_product_id");
                return "product_id";
            case "orderid":
                column.setColumnName("platform_order_id");
                return "orderid";
            case "actual_service_income":
                column.setColumnName("final_service_income");
                return "actual_service_income";
            case "pay_goods_amount":
                column.setColumnName("estimate_settlement_amount");
                return "pay_goods_amount";
            case "shop_id":
                column.setColumnName("platform_shop_id");
                return "shop_id";
            case "shopname":
                column.setColumnName("shop_name");
                return "shopname";
            case "productname":
                column.setColumnName("platform_product_name");
                return "productname";
            case "productid":
                column.setColumnName("platform_product_id");
                return "productid";
            case "shopid":
                column.setColumnName("platform_shop_id");
                return "shopid";
            default:
                return null;
        }
    }

    private static void product(PlainSelect selectBody) {
        if (selectBody.getFromItem() instanceof Table) {
            Table table = (Table) selectBody.getFromItem();
            if ("product".equals(table.getName())) {
                for (SelectItem selectItem : selectBody.getSelectItems()) {
                    SelectExpressionItem expressionItem = (SelectExpressionItem) selectItem;
                    Expression innerExpression = expressionItem.getExpression();
                    //1.使用的是函数
                    if (innerExpression instanceof Function) {
                        Function function = (Function) innerExpression;
                        ExpressionList expressionList = function.getParameters();
                        if (null != expressionList) {
                            for (Expression expression : expressionList.getExpressions()) {
                                if (expression instanceof Column) {
                                    Column column = (Column) expression;
                                    productColumnInfo(column);
                                }
                            }
                        }
                    } else {
                        //2.只有字段
                        Column column = (Column) innerExpression;
                        productColumnInfo(column);
                    }
                }
                table.setName("redu_product");
            }
        }
    }

    private static void product_category(PlainSelect selectBody) {
        if (selectBody.getFromItem() instanceof Table) {
            Table table = (Table) selectBody.getFromItem();
            if ("product_category".equals(table.getName())) {
                table.setName("redu_product_category");
            }
        }
    }

    public static void productColumnInfo(Column column) {
        if ("product_category_id".equals(column.getColumnName())) {
            column.setColumnName("category_id product_category_id");
        }
        if ("id".equals(column.getColumnName())) {
            column.setColumnName("platform_product_id id");
        }
    }

    public static void parse(String sql) throws JSQLParserException {
        // 使用工具类把SQL转换为Select对象
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = select.getSelectBody();
        System.out.println(selectBody);
        //普通查询，查询所有字段
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plain.getSelectItems();
        if (selectItems != null) {
            for (SelectItem selectItem : selectItems) {
                System.out.println(selectItem.toString());
            }
        }
        //获取表名
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(select);
        System.out.println(tableList);
        //获取where条件
        Expression where = plain.getWhere();
        System.out.println(where);
        //子查询
        FromItem fromItem = plain.getFromItem();
        System.out.println("-----子查询-----");
        System.out.println(fromItem);
        SubSelect subSelect = (SubSelect) plain.getFromItem();
        PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
        System.out.println(plainSelect);
    }


}
