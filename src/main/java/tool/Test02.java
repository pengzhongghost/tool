package tool;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pengzhong
 * @since 2023/3/29
 */
public class Test02 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "刘逸凡\n" +
                "刘逸凡\n" +
                "彭钟\n" +
                "王艳芳\n" +
                "王永钢\n" +
                "冯浩海\n" +
                "刘逸凡\n" +
                "石俊雨\n" +
                "方宇\n" +
                "胡慧康\n" +
                "王艳芳\n" +
                "刘逸凡\n" +
                "彭钟\n" +
                "王艳芳\n" +
                "王艳芳\n" +
                "刘潘逸佛\n" +
                "方宇\n" +
                "彭钟\n" +
                "李新";
        HashSet<String> set = new HashSet<>();
        String[] split = s.split("\n");
        for (String str : split) {
            set.add("'"+str+"'");
        }
        System.out.println(set);
//        System.out.println(
//                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM3sVBIPJbvjzJbA" +
//                        "frDCKhYoRn4DkJN7LYwTo8dYrTmg/G6BQd6G/JlHWYxuxwgLsezUI12m3UAbzoho" +
//                        "1MF+j7FF2/erCpKeGolSXHTHBidi3cYXF2zymrmJF4uI/ieTWp25Ey4rx8BMhCg8" +
//                        "JtJknv96SiU4Lb4EcNhbpkP7IbSbAgMBAAECgYBYWjjsErG2/xNt7yOshXJ3VUqB" +
//                        "kNf8X1J4koin5IVWf5svcqgnaTWCMpnaZkFhU1tA6PhPm2O8uPGRl6EJcFsIB4V0" +
//                        "wfN2pVovDEneek4eWrXXZi1SyU01haYYIFYu+7CuKXbxWcLI574DdIjR7+MOlhhL" +
//                        "Wt/MojjYu/3h6KaNKQJBAP6JD25/0QzDeHrx8rf5PPA59e5ghA91ml5DVPChKKiH" +
//                        "hgnchGk50CdGH57vvMmlKKqHqH1pFqsQzh+HzA6NklUCQQDPG6kawSOTY9zTOEOo" +
//                        "Qk5Edjftmoqd+LtAGYskQUn0eA1eOyTg17+iOZtzQGcbyB3w5mPUAOaDfJT5hDVZ" +
//                        "gXsvAkEAqmZTYGRNLUBHDfefyC4g76m9zKHshvTyLRziZl1dfMWNpFhuhIthoIk/" +
//                        "bgWM22mn+dHrqFmMBWWwuEcPAscgKQJBAJuZtoUpUmcsAf6XKQ3DMzZwcziKgVaK" +
//                        "R3SzPDyy6LNJdxGy8dQThCCHk2kVm+HdidEx5xKi0o07DlogoExs4XUCQAGMGH/e" +
//                        "XjnL7ondrwguMmdmJ++x8c4rjkvpGB11Kxpx7ES/35x6wgq7cPdNEgwvHJVkoH/W" +
//                        "fStKEgfcjBE+hRI=");
////        String str = "1970-01-01 08:33:4350439127175987108531,2,33599191799471710981";
////        byte[] bytes = str.getBytes("GBK");
////        System.out.println(Arrays.toString(bytes));
////        System.out.println(bytes.length);
////        System.out.println(getEndTime("2023-02-01 00:00:59"));
////        JSONObject jsonObject = new JSONObject();
////        String str = "adsActivityId=0, adsEstimatedCommission=18, adsEstimatedTechServiceFee=null, adsFansType=null, adsPromotionRate=500, adsRealCommission=0, authorAccount=, authorBuyinId=, authorShortId=null, confirmTime=null, flowPoint=PAY_SUCC, itemNum=1, materialId=null, mediaType=others, orderId=6920215374750225754, payGoodsAmount=390, paySuccessTime=Thu Jul 20 14:41:50 GMT+08:00 2023, productActivityId=0, productId=3623400373584272871, productImg=https://p9-aio.ecombdimg.com/obj/ecom-shop-material/BnbCjjMM_m_c78863b306613a839e5c02c549f79d01_sx_220971_www800-800, productName=2023新款棉绸夏季女士薄款宽松大码显瘦可外穿家居服, refundTime=null, settleTime=null, settledGoodsAmount=0, shopId=88263737, shopName=依诺家居服, totalPayAmount=390, updateTime=Thu Jul 20 14:42:03 GMT+08:00 2023, adsDistributorId=7213425892680220939, adsDistributorName=null, distributionType=ProductDetail, authorUid=0, type=1, rdType=0, carrierPhone=null, payTime=null";
////        String[] split = str.split(",");
////        for (String s : split) {
////            String[] split1 = s.split("=");
////            if (split1.length == 2) {
////  jsonObject.set(split1[0].replace(" ", ""), split1[1]);
////            }else {
////  jsonObject.set(split1[0].replace(" ", ""), null);
////            }
////        }
////        JSONObject pidInfo = new JSONObject();
////        pidInfo.set("external_info", "7872093_F21637");
////        pidInfo.set("media_type_name", "ProductDetail");
////        pidInfo.set("pid", "dy_107213425892680220939_23555_385662386");
////        jsonObject.set("pidInfo", pidInfo);
////        HashMap<Object, Object> map = new HashMap<>();
////        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
////            map.put(StrUtil.toUnderlineCase(entry.getKey()), entry.getValue());
////        }
////        System.out.println(JSONUtil.toJsonStr(map));
////        String ids ="7148209012919681293,6945415474420564236,7213400391651262755,7214276239967895811,7213959917857898809,7213908745650094397,7208790062095745292,7226479731972210981,7219223777162641675,7213003911602340096,7213423651721396536,7208596594541953339,7225770538177659197,7208777663925567779,7213400391651262755,7213425892680220939,7211005634836431162,7208902332016722237,7213400391651262755,7223891484222718245,7235887559414415651,7211005634836431162,7213598362393690400,7220355912916287803,7208820215940088075,7208577402572833039,7215219703693427000,7215060942454620456,7208776047448506680,7223558665582739764,7212874682214449448,7213980282436370721,7212875815233585466,7213598362405437700,7213188452421435660,7231321934620459325,7213452839610417468,7212850798836826383";
////        System.out.println(ids.split(",").length);
////        String buyinIds= "7148209012919681293,6945415474420564236,7213400391651262755,7214276239967895811,7213959917857898809,7213908745650094397,7208790062095745292,7226479731972210981,7219223777162641675,7213003911602340096,7213423651721396536,7208596594541953339,7225770538177659197,7208777663925567779,7213400391651262755,7213425892680220939,7211005634836431162,7208902332016722237,7213400391651262755,7223891484222718245,7235887559414415651,7211005634836431162,7213598362393690400,7220355912916287803,7208820215940088075,7208577402572833039,7215219703693427000,7215060942454620456,6986905800367489292,7223558665582739764,7212874682214449448,7213980282436370721,7212875815233585466";
////        System.out.println(buyinIds.split(",").length);
//
//        LocalDateTime formalMonth = DateUtil.parseLocalDateTime(DateUtil.format(DateUtil.parseLocalDateTime("2023-06-22 00:00:00", DatePattern.NORM_DATETIME_PATTERN), DatePattern.NORM_MONTH_PATTERN), DatePattern.NORM_MONTH_PATTERN).plusMonths(2);
//        LocalDateTime nowMonth = DateUtil.parseLocalDateTime(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_MONTH_PATTERN), DatePattern.NORM_MONTH_PATTERN);
//        System.out.println(nowMonth.isAfter(formalMonth));
//        String s = "author_account," +
//                "author_openid," +
//                "estimated_tech_service_fee," +
//                "product_img," +
//                "update_time," +
//                "pay_success_time," +
//                "real_commission," +
//                "settled_goods_amount," +
//                "app," +
//                "commission_rate," +
//                "estimated_commission," +
//                "estimated_total_commission," +
//                "flow_point," +
//                "order_id," +
//                "product_id," +
//                "shop_id," +
//                "shop_name," +
//                "total_pay_amount," +
//                "buyer_openid," +
//                "item_num," +
//                "pay_goods_amount," +
//                "product_name," +
//                "author_short_id," +
//                "order_id";
//        String[] split = s.split(",");
//        System.out.println(split.length);
    }

        private static String getEndTime(String startTime) {
        LocalDateTime localDateTime = DateUtil.parseLocalDateTime(startTime);
        LocalDateTime plus = localDateTime.plus(1, ChronoUnit.SECONDS);
        return DateUtil.format(plus, DatePattern.NORM_DATETIME_PATTERN);
    }

}
