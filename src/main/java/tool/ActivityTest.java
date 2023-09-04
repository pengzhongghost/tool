package tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import pojo.ServiceResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengzhong
 * @since 2023/5/31
 */
public class ActivityTest {

    private static final String URL = "https://uat-e.reduxingxuan.com/bj/qingluan/web/test/testQueryActivityDetail";

    private static final String COOKIE = "sessionId=user:login:session:24f95aa2e11a4ab5b6a32e7a17a23960:06-fb-c5-7e-31-75:Chrome:730498e5193145968ee10f07277d40aa";

    public static void main(String[] args) {
        List<String> collect = Arrays.stream(getActivityIds().replace(",", "").split("\n")).collect(Collectors.toList());
        for (String s : collect) {
            Map<String, String> map = new HashMap<>();
            map.put("activityId", s);
            HttpResponse response = HttpUtil.createPost(URL).header("Cookie", COOKIE).header("app-key", "workbench").body(JSONUtil.toJsonStr(map)).execute();
            ServiceResult<Map<String, String>> serviceResult = JSONUtil.toBean(response.body(), new TypeReference<ServiceResult<Map<String, String>>>() {
            }, false);
            System.out.println(serviceResult.getData().get("activityName"));
        }

    }

    public static String getActivityIds(){
        return "685,356\n" +
                "774,039\n" +
                "902,819\n" +
                "362,551\n" +
                "24,475\n" +
                "872,746\n" +
                "824,452\n" +
                "507,593\n" +
                "797,649\n" +
                "843,864\n" +
                "732,037\n" +
                "647,429\n" +
                "797,197\n" +
                "268,170\n" +
                "713,186\n" +
                "895,923\n" +
                "846,485\n" +
                "820,319\n" +
                "827,010\n" +
                "19,321\n" +
                "801,406\n" +
                "814,695\n" +
                "868,090\n" +
                "814,346\n" +
                "730,249\n" +
                "827,012\n" +
                "824,095\n" +
                "794,603\n" +
                "845,396\n" +
                "795,838\n" +
                "881,220\n" +
                "174,257\n" +
                "881,776\n" +
                "797,195\n" +
                "681,127\n" +
                "752,748\n" +
                "835,880\n" +
                "692,711\n" +
                "781,859\n" +
                "648,238\n" +
                "827,519\n" +
                "526,853\n" +
                "771,100\n" +
                "600,640\n" +
                "644,530\n" +
                "833,823\n" +
                "522,992\n" +
                "788,252\n" +
                "865,284\n" +
                "340,463\n" +
                "840,829\n" +
                "304,046\n" +
                "283,092\n" +
                "775,791\n" +
                "219,337\n" +
                "880,606\n" +
                "578,535\n" +
                "835,939\n" +
                "733,500\n" +
                "317,460\n" +
                "423,026\n" +
                "865,285\n" +
                "897,120\n" +
                "719,063\n" +
                "313,943\n" +
                "830,525\n" +
                "759,138\n" +
                "798,260\n" +
                "842,057\n" +
                "831,825\n" +
                "117,270\n" +
                "845,403\n" +
                "336,351\n" +
                "313,748\n" +
                "780,771\n" +
                "857,740\n" +
                "763,530\n" +
                "840,341\n" +
                "799,904\n" +
                "358,766\n" +
                "80,281\n" +
                "374,153\n" +
                "158,600\n" +
                "850,802\n" +
                "398,084\n" +
                "853,484\n" +
                "251,868\n" +
                "697,723\n" +
                "843,912\n" +
                "793,682\n" +
                "339,709\n" +
                "222,630\n" +
                "441,421\n" +
                "334,790\n" +
                "726,703\n" +
                "126,605\n" +
                "797,637\n" +
                "726,585\n" +
                "878,870\n" +
                "644,502\n" +
                "777,793\n" +
                "692,553\n" +
                "299,542\n" +
                "303,997\n" +
                "199,892\n" +
                "784,938\n" +
                "358,757\n" +
                "844,627\n" +
                "766,736\n" +
                "288,797\n" +
                "306,767\n" +
                "620,118\n" +
                "179,696\n" +
                "648,259\n" +
                "491,308\n" +
                "844,671\n" +
                "862,443\n" +
                "809,480";
    }

}
