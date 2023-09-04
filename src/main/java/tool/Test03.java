package tool;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengzhong
 * @since 2023/5/10
 */
public class Test03 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestVO {

        private Boolean isFlag;

    }

    public static void main(String[] args) {
        String str = "{\"isFlag\": \"true\"}";
        System.out.println(str);
        System.out.println(JSONUtil.toJsonStr(JSONUtil.toBean(str, TestVO.class)));
//        Map<String, Object> map = new HashMap<>();
//        System.out.println(map.get(""));
//        String d1 = "2.11";
//        String d2 = "2.10";
//        System.out.println(String.format("%.2f", new BigDecimal(d1).doubleValue()));
//        String fileds = ("\"ds\",\n" +
//                "\"platform\",\n" +
//                "\"key_type\",\n" +
//                "\"pid\",\n" +
//                "\"external\",\n" +
//                "\"pay_success_time\",\n" +
//                "\"order_num\",\n" +
//                "\"pay_goods_amount\",\n" +
//                "\"ads_real_commission\",\n" +
//                "\"key_id\",\n" +
//                "\"key_name\"");
//        System.out.println(fileds);
//        System.out.println((char)999 );
//        String str = "69182503876964575152023-05-07 13:18:44231106109062353741805696594887434481573886061623770.020.8000.100.080热度星选73024910000(null)0000农心韩国进口牛肉味碗面便携自带筷子方便面桶面韩剧同款86g*6168343672439.90036167736743琪琪格美好生活0011725223全球食品馆1(null)7112257750717513992https://p9-aio.ecombdimg.com/obj/ecom-shop-material/iAIpnFMM_m_43a406dc0def63dccb0115a4e7828caf_sx_526200_www800-8001683436734抖音0video1672134682810766271682160.020.8031.003.000(null)(null)0168359986239.90(null)(null)(null)(null)(null)10";
//        String[] fields = fileds.split("");
//        String[] orderFields = str.split("");
//        StringBuilder sb = new StringBuilder();
//        StringBuilder f = new StringBuilder();
//        for (int i = 0; i < fields.length; i++) {
//            sb.append(fields[i]).append(",");
//        }
//        for (int i = 0; i < orderFields.length; i++) {
//            if ("(null)".equals(orderFields[i])) {
//                f.append("null").append(",");
//            } else {
//                f.append(orderFields[i]).append(",");
//            }
//        }
//        System.out.println(sb);
//        System.out.println("_-");
//        System.out.println(f);
    }


}
