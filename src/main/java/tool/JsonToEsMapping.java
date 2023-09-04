package tool;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengzhong
 * @since 2023/6/27
 */
public class JsonToEsMapping {

    public static void main(String[] args) {
        List<Map<String, String>> list = get();

        JSONObject jsonObject = new JSONObject();
        JSONObject maps = new JSONObject();
        for (Map<String, String> stringMap : list) {
            Map<String, Object> typeMap = new HashMap<>();
            System.out.println();
            typeMap.put("type", stringMap.get("type"));
            maps.set(stringMap.get("name"), typeMap);
        }
        jsonObject.set("properties", maps);
        System.out.println(jsonObject);
    }

    public static List<Map<String, String>> get() {
        List<Map<String, String>> maps = JSONUtil.toBean("[\n" +
                "              {\n" +
                "                \"name\": \"orderId\",\n" +
                "                \"type\": \"id\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"productId\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"productName\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"productImg\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"authorAccount\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"authorShortId\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"authorBuyinId\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"flowPoint\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"shopId\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"shopName\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"itemNum\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"paySuccessTime\",\n" +
                "                \"type\": \"date\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"settleTime\",\n" +
                "                \"type\": \"date\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"refundTime\",\n" +
                "                \"type\": \"date\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"updateTime\",\n" +
                "                \"type\": \"date\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"confirmTime\",\n" +
                "                \"type\": \"date\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"totalPayAmount\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"payAoodsAmount\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"settledGoodsAmount\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"mediaType\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"mediaTypeName\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"hasSupermarketTag\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"hasSubsidyTag\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"materialId\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"pid\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"externalInfo\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsPromotionRate\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsEstimatedCommission\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsRealCommission\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsEstimatedTechServiceFee\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsFansType\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsDistributorId\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"adsDistributorName\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"distributionType\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"name\": \"rdType\",\n" +
                "                \"type\": \"keyword\"\n" +
                "              }\n" +
                "            ]", new TypeReference<List<Map<String, String>>>() {
        }, false);
        return maps;
    }

}
