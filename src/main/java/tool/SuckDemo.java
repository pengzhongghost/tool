package tool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SuckDemo {

    private static final String COOKIE = "global_sessionId=user:login:global:session:6ba1250fd9eb4484bab9e28eb91d5ead; sessionId=user:login:session:505bb22b43764c7b89d0aa0030d83177:0a-d3-5d-f7-12-51:Chrome:2a694870e6764e2081fa0efb50c4771e";

    //2021-03-08 20:15:39
    //2023-05-10 23:47:46
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        while (!startDate.equals(endDate)) {
            Map<String, Object> map = new HashMap<>();
            //1.按天的
            //String date = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
            String date = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //1.按天的
//            map.put("sql", "INSERT INTO member_talent_douyin_order_bak\n" +
//                    "SELECT * FROM member_talent_douyin_order partition (p" + date + ");");
            map.put("sql", "INSERT INTO member_talent_douyin_order\n" +
                    "SELECT * FROM member_talent_douyin_order_bak2 partition (p2022) where pay_success_time between '" + date + "' and '" + startDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'");
            HttpRequest httpRequest = HttpUtil.createPost("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/createTable")
                    .header("cookie", COOKIE)
                    .form(map);
            HttpResponse response = httpRequest.execute();
            System.out.println(date);
            System.out.println(response.body());
            startDate = startDate.plusDays(1);
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("sql", "INSERT INTO cdouyin_order_pay_zhong\n" +
//                "SELECT * FROM cdouyin_order_pay partition (p20230506);");
//        HttpRequest httpRequest = HttpUtil.createPost("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/createTable")
//                .header("cookie", COOKIE)
//                .form(map);
//        HttpResponse response = httpRequest.execute();
//        System.out.println(response.body());
    }


}