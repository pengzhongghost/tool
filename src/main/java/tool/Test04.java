package tool;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengzhong
 * @since 2024/2/22
 */
public class Test04 {

    public static void main(String[] args) {
        String sql = "7251876<#$>聚合账户<#$>收入<#$>'6919209363150017861<#$>广西津选食品电子商务有限公司<#$>【30包仅9.9】轻咔熊7种轻零食大礼包好吃休闲零食追剧健康小零食<#$>结算<#$>2023-06-10 12:26:18<#$>2023-07-07 01:00:00<#$>1.9<#$>0.0<#$>8.3<#$>2%<#$>0.17<#$>-0.02<#$>0%<#$>0.0<#$>0.15<#$><#$>0.0<#$>0.0<#$>0.0<#$>0.0<#$>0.0<#$>";
        String[] splits = sql.split("<#\\$>");
        System.out.println(splits.length);
        StringBuilder sb = new StringBuilder();
        for (String s : sql.split("\n")) {
            sb.append("\"").append(s).append("\",");
        }
        System.out.println(sb);
        System.out.println(JSONUtil.toJsonStr(JSONUtil.toBean("{\n" +
                "\t\"orderId\": \"123\"\n" +
                "}", JSONObject.class)));
    }
    
}
