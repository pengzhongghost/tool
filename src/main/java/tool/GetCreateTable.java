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

    private static final String COOKIE = "global_sessionId=user:login:global:session:8673d5684f32457f84ccaca8c33f249a; sessionId=user:login:session:505bb22b43764c7b89d0aa0030d83177:66-bb-7f-82-f4-fd:Chrome:8000682c349748a5993633d7a2e3022d";

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
        String tables = "re_du_dian_shang_ke320";
                return Arrays.stream(tables.split("\n")).collect(Collectors.toList());
    }


}
