package tool;

import cn.hutool.http.HttpUtil;

/**
 * @author pengzhong
 * @since 2023/4/8
 */
public class FundOpenApiTest {



    public static void main(String[] args) {
        String url = "https://uat-e.reduxingxuan.com/bj/fund/openapi/testSendToQueue";
        HttpUtil.createPost(url).header("appId", "");
    }

}
