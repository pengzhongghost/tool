package tool.columnsToEs;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.*;

/**
 * @author pengzhong
 * @since 2023/8/3
 */
public class ColumnsToEs {

    private static final String columns = "pay_success_time,order_id,author_openid,flow_point,product_img,product_name,author_short_id,commission_rate,estimated_commission,media_type,app_id,real_commission,settled_goods_amount,update_time,shop_id,total_pay_amount,app,author_account,author_buyin_id,estimated_tech_service_fee,pay_goods_amount,pick_source_client_key,product_id,shop_name,estimated_total_commission,item_num,refund_time,settle_time,buyer_openid,updated_at";

    private static final String json = "{\n" +
            "\t\"pay_success_time\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"order_id\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"author_openid\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"flow_point\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"product_img\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"product_name\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"author_short_id\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"commission_rate\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"estimated_commission\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"media_type\": {\n" +
            "\t\t\"type\": \"text\",\n" +
            "\t\t\"fields\": \"{\\\"keyword\\\":{\\\"ignore_above\\\":256,\\\"type\\\":\\\"keyword\\\"}}\"\n" +
            "\t},\n" +
            "\t\"app_id\": {\n" +
            "\t\t\"type\": \"text\",\n" +
            "\t\t\"fields\": \"{\\\"keyword\\\":{\\\"ignore_above\\\":256,\\\"type\\\":\\\"keyword\\\"}}\"\n" +
            "\t},\n" +
            "\t\"real_commission\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"settled_goods_amount\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"update_time\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"shop_id\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"total_pay_amount\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"app\": {\n" +
            "\t\t\"type\": \"text\"\n" +
            "\t},\n" +
            "\t\"author_account\": {\n" +
            "\t\t\"type\": \"text\"\n" +
            "\t},\n" +
            "\t\"author_buyin_id\": {\n" +
            "\t\t\"type\": \"text\",\n" +
            "\t\t\"fields\": {\n" +
            "\t\t\t\"keyword\": {\n" +
            "\t\t\t\t\"type\": \"keyword\",\n" +
            "\t\t\t\t\"ignore_above\": 256\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t},\n" +
            "\t\"estimated_tech_service_fee\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"pay_goods_amount\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"pick_source_client_key\": {\n" +
            "\t\t\"type\": \"text\",\n" +
            "\t\t\"fields\": \"{\\\"keyword\\\":{\\\"ignore_above\\\":256,\\\"type\\\":\\\"keyword\\\"}}\"\n" +
            "\t},\n" +
            "\t\"product_id\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"shop_name\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"estimated_total_commission\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"item_num\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"refund_time\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"settle_time\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t},\n" +
            "\t\"buyer_openid\": {\n" +
            "\t\t\"type\": \"keyword\"\n" +
            "\t},\n" +
            "\t\"updated_at\": {\n" +
            "\t\t\"type\": \"long\"\n" +
            "\t}\n" +
            "}";

    public static void main(String[] args) {
        LinkedHashMap<String, LinkedHashMap<String, String>> stringStringMap = JSONUtil.toBean(json, new TypeReference<LinkedHashMap<String, LinkedHashMap<String, String>>>() {
        }, false);
        List<Map<String, String>> resultList = new ArrayList<>();
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : stringStringMap.entrySet()) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("name", entry.getKey());
            map.put("type", entry.getValue().get("type"));
            resultList.add(map);
        }
        System.out.println(JSONUtil.toJsonStr(resultList));
        String[] split = columns.split(",");
        List<Map<String, String>> realResult = new ArrayList<>();
        for (String column : split) {
            for (Map<String, String> stringMap : resultList) {
                if (column.equals(stringMap.get("name"))) {
                    realResult.add(stringMap);
                }
            }
        }
        System.out.println(JSONUtil.toJsonStr(realResult));
//        System.out.println(stringStringMap);
//        Map<String, Map<String, String>> result = new LinkedHashMap<>();
//        int i = 0;
//        for (String s : columns.split(",")) {
//            boolean b = stringStringMap.keySet().stream().anyMatch(key -> key.equals(s));
//            for (Map.Entry<String, Map<String, String>> entry : stringStringMap.entrySet()) {
//                if (s.equals(entry.getKey())) {
//                    result.put(s, entry.getValue());
//                }
//            }
//            if (!b) {
//                System.out.println(s);
//                System.out.println(i);
//            }
//            i++;
//        }
//        System.out.println(JSONUtil.toJsonStr(result));
    }
    
}


