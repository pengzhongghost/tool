package tool.sqlToColumns;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * @author pengzhong
 * @since 2023/7/3
 */
public class SqlToColumns {

    private static final String sql = "select\n" +
            "  begin_month,\n" +
            "  case\n" +
            "    when diff = 0 then shopid\n" +
            "  end as m0,\n" +
            "  case\n" +
            "    when diff = 1 then shopid\n" +
            "  end as m1,\n" +
            "  case\n" +
            "    when diff = 2 then shopid\n" +
            "  end as m2,\n" +
            "  case\n" +
            "    when diff = 3 then shopid\n" +
            "  end as m3,\n" +
            "  case\n" +
            "    when diff = 4 then shopid\n" +
            "  end as m4,\n" +
            "  case\n" +
            "    when diff = 5 then shopid\n" +
            "  end as m5,\n" +
            "  case\n" +
            "    when diff = 6 then shopid\n" +
            "  end as m6,\n" +
            "  case\n" +
            "    when diff = 7 then shopid\n" +
            "  end as m7,\n" +
            "  case\n" +
            "    when diff = 8 then shopid\n" +
            "  end as m8,\n" +
            "  case\n" +
            "    when diff = 9 then shopid\n" +
            "  end as m9,\n" +
            "  case\n" +
            "    when diff = 10 then shopid\n" +
            "  end as m10,\n" +
            "  case\n" +
            "    when diff = 11 then shopid\n" +
            "  end as m11,\n" +
            "  case\n" +
            "    when diff = 12 then shopid\n" +
            "  end as m12,\n" +
            "  case\n" +
            "    when diff = 13 then shopid\n" +
            "  end as m13,\n" +
            "  case\n" +
            "    when diff = 14 then shopid\n" +
            "  end as m14,\n" +
            "  case\n" +
            "    when diff = 15 then shopid\n" +
            "  end as m15,\n" +
            "  case\n" +
            "    when diff = 16 then shopid\n" +
            "  end as m16,\n" +
            "  case\n" +
            "    when diff = 17 then shopid\n" +
            "  end as m17,\n" +
            "  case\n" +
            "    when diff = 18 then shopid\n" +
            "  end as m18,\n" +
            "  case\n" +
            "    when diff = 19 then shopid\n" +
            "  end as m19,\n" +
            "  case\n" +
            "    when diff = 20 then shopid\n" +
            "  end as m20,\n" +
            "  case\n" +
            "    when diff = 21 then shopid\n" +
            "  end as m21,\n" +
            "  case\n" +
            "    when diff = 22 then shopid\n" +
            "  end as m22,\n" +
            "  case\n" +
            "    when diff = 23 then shopid\n" +
            "  end as m23,\n" +
            "  case\n" +
            "    when diff = 24 then shopid\n" +
            "  end as m24,\n" +
            "  case\n" +
            "    when diff = 25 then shopid\n" +
            "  end as m25,\n" +
            "  case\n" +
            "    when diff = 26 then shopid\n" +
            "  end as m26,\n" +
            "  case\n" +
            "    when diff = 27 then shopid\n" +
            "  end as m27,\n" +
            "  case\n" +
            "    when diff = 28 then shopid\n" +
            "  end as m28,\n" +
            "  case\n" +
            "    when diff = 29 then shopid\n" +
            "  end as m29,\n" +
            "  case\n" +
            "    when diff = 30 then shopid\n" +
            "  end as m30,\n" +
            "  case\n" +
            "    when diff = 0 then GMV\n" +
            "  end as a0,\n" +
            "  case\n" +
            "    when diff = 1 then GMV\n" +
            "  end as a1,\n" +
            "  case\n" +
            "    when diff = 2 then GMV\n" +
            "  end as a2,\n" +
            "  case\n" +
            "    when diff = 3 then GMV\n" +
            "  end as a3,\n" +
            "  case\n" +
            "    when diff = 4 then GMV\n" +
            "  end as a4,\n" +
            "  case\n" +
            "    when diff = 5 then GMV\n" +
            "  end as a5,\n" +
            "  case\n" +
            "    when diff = 6 then GMV\n" +
            "  end as a6,\n" +
            "  case\n" +
            "    when diff = 7 then GMV\n" +
            "  end as a7,\n" +
            "  case\n" +
            "    when diff = 8 then GMV\n" +
            "  end as a8,\n" +
            "  case\n" +
            "    when diff = 9 then GMV\n" +
            "  end as a9,\n" +
            "  case\n" +
            "    when diff = 10 then GMV\n" +
            "  end as a10,\n" +
            "  case\n" +
            "    when diff = 11 then GMV\n" +
            "  end as a11,\n" +
            "  case\n" +
            "    when diff = 12 then GMV\n" +
            "  end as a12,\n" +
            "  case\n" +
            "    when diff = 13 then GMV\n" +
            "  end as a13,\n" +
            "  case\n" +
            "    when diff = 14 then GMV\n" +
            "  end as a14,\n" +
            "  case\n" +
            "    when diff = 15 then GMV\n" +
            "  end as a15,\n" +
            "  case\n" +
            "    when diff = 16 then GMV\n" +
            "  end as a16,\n" +
            "  case\n" +
            "    when diff = 17 then GMV\n" +
            "  end as a17,\n" +
            "  case\n" +
            "    when diff = 18 then GMV\n" +
            "  end as a18,\n" +
            "  case\n" +
            "    when diff = 19 then GMV\n" +
            "  end as a19,\n" +
            "  case\n" +
            "    when diff = 20 then GMV\n" +
            "  end as a20,\n" +
            "  case\n" +
            "    when diff = 21 then GMV\n" +
            "  end as a21,\n" +
            "  case\n" +
            "    when diff = 22 then GMV\n" +
            "  end as a22,\n" +
            "  case\n" +
            "    when diff = 23 then GMV\n" +
            "  end as a23,\n" +
            "  case\n" +
            "    when diff = 24 then GMV\n" +
            "  end as a24,\n" +
            "  case\n" +
            "    when diff = 25 then GMV\n" +
            "  end as a25,\n" +
            "  case\n" +
            "    when diff = 26 then GMV\n" +
            "  end as a26,\n" +
            "  case\n" +
            "    when diff = 27 then GMV\n" +
            "  end as a27,\n" +
            "  case\n" +
            "    when diff = 28 then GMV\n" +
            "  end as a28,\n" +
            "  case\n" +
            "    when diff = 29 then GMV\n" +
            "  end as a29,\n" +
            "  case\n" +
            "    when diff = 30 then GMV\n" +
            "  end as a30\n" +
            "from\n" +
            "  (\n" +
            "    select\n" +
            "      a.shopid,\n" +
            "      a.GMV,\n" +
            "      a.month as begin_month,\n" +
            "      b.month as end_month,\n" +
            "      datediff(\n" +
            "        concat(a.month, '-01'),\n" +
            "        concat(b.month, '-01')\n" +
            "      ) as diff\n" +
            "    from\n" +
            "      (\n" +
            "        select\n" +
            "          if(platform_shop_id is null, 'a', platform_shop_id) as shopid,\n" +
            "          date_format(paid_time, '%Y-%m') as month,\n" +
            "          sum(estimate_settlement_amount) as GMV\n" +
            "        from\n" +
            "          data_cube.redu_order\n" +
            "        where\n" +
            "          find_in_set('1', applet_peg) <> 0\n" +
            "          and date_format(paid_time, '%Y%m') >= 202103\n" +
            "        group by\n" +
            "          if(platform_shop_id is null, 'a', platform_shop_id),\n" +
            "          date_format(paid_time, '%Y-%m')\n" +
            "      ) a\n" +
            "      left join (\n" +
            "        select\n" +
            "          if(platform_shop_id is null, 'a', platform_shop_id) as shopid,\n" +
            "          date_format(paid_time, '%Y-%m') as month,\n" +
            "          sum(estimate_settlement_amount) as GMV\n" +
            "        from\n" +
            "          data_cube.redu_order\n" +
            "        where\n" +
            "          find_in_set('1', applet_peg) <> 0\n" +
            "          and date_format(paid_time, '%Y%m') >= 202103\n" +
            "        group by\n" +
            "          if(platform_shop_id is null, 'a', platform_shop_id),\n" +
            "          date_format(paid_time, '%Y-%m')\n" +
            "      ) b on a.shopid = b.shopid\n" +
            "      and a.month <= b.month\n" +
            "  ) a";

    public static void main(String[] args) throws JSQLParserException {
        StringBuilder sb = new StringBuilder();
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        for (SelectItem selectItem : selectBody.getSelectItems()) {
            SelectExpressionItem expressionItem = (SelectExpressionItem) selectItem;
            if (null == expressionItem.getAlias()) {
                Column column = (Column) expressionItem.getExpression();
                sb.append(column.getColumnName()).append(",");
            } else {
                String name = expressionItem.getAlias().getName();
                sb.append(name).append(",");
            }

        }
        System.out.println(sb);
    }

}
