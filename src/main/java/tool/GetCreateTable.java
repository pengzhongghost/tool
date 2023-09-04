package tool;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import pojo.ServiceResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengzhong
 * @since 2023/3/30
 */
public class GetCreateTable {

    private static final String COOKIE = "global_sessionId=user:login:global:session:e594e2975e174fdeb236e0529be9d622; sessionId=user:login:session:505bb22b43764c7b89d0aa0030d83177:12-79-2d-99-82-b0:Chrome:d01720a74d644194ac1701d2ba58c940; _csrf-backend=b2b9206f787b89644728b5e7bf2a112cdf0378f838fbb29d0f5cb4ecc6201e84a%3A2%3A%7Bi%3A0%3Bs%3A13%3A%22_csrf-backend%22%3Bi%3A1%3Bs%3A32%3A%226-dz_TrK_66mc7eQTNYHz0X-ppuDN5tl%22%3B%7D; admin_sso_login=1; backend_role_list=9e97116003a3a2bbb47ce7a56b0a2713c12097503e9e880a2f31d8ecdc1b5701a%3A2%3A%7Bi%3A0%3Bs%3A17%3A%22backend_role_list%22%3Bi%3A1%3Bs%3A155%3A%22%5B%7B%22role_list%22%3A%5B%7B%22role_id%22%3A669569%2C%22role_name%22%3A%22%E6%98%9F%E9%80%89%E6%80%BB%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E5%91%98%22%7D%5D%2C%22current_role%22%3A%7B%22role_id%22%3A669569%2C%22role_name%22%3A%22%E6%98%9F%E9%80%89%E6%80%BB%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E5%91%98%22%7D%7D%2C288000%5D%22%3B%7D; _identity_backend=f40e8955a49ebd1680a4c017997e60aaa352c0146099bc86c6c514ed8df287b0a%3A2%3A%7Bi%3A0%3Bs%3A17%3A%22_identity_backend%22%3Bi%3A1%3Bs%3A124%3A%22%5B161%2C%22user%3Alogin%3Asession%3A505bb22b43764c7b89d0aa0030d83177%3A12-79-2d-99-82-b0%3AChrome%3Ad01720a74d644194ac1701d2ba58c940%22%2C288000%5D%22%3B%7D; partner_sso_login=0; partner_login_auth=3XyodrHvzZbCb5SdnPeYXmjWeCk2-WGn; advanced-backend=9n2rmcndko4oga1cd0c22tlf89; _csrf=8604f82ca71477629877747da45541e5e0573cb2ccb9f11acfe056feee48a593a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22m0bZ-Vp8sQhAC75Xs5RiqlrZZh9oSiZd%22%3B%7D";

    public static void main(String[] args) {
        List<String> tables = getTables();
        Map<String, Object> param = Maps.newHashMap();
        for (String table : tables) {
            param.put("sql", "show create table " + table + ";");
            HttpResponse response = HttpUtil.createGet("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/showTableInfo").header("cookie", COOKIE).form(param).execute();
            String createSql = JSONUtil.toBean(response.body(), new TypeReference<ServiceResult<List<Map<String, String>>>>() {
            }, false).getData().get(0).get("Create Table");
            System.out.println(createSql);
        }
    }

    private static List<String> getTables() {
        String tables = "eb_douyin_order";
                return Arrays.stream(tables.split("\n")).collect(Collectors.toList());
    }


}
