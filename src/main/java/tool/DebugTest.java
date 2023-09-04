package tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengzhong
 * @since 2023/6/27
 */
public class DebugTest {

    public static void main(String[] args) {
        String finalDynamicColumnField= "达人昵称 ";
        List<Map<String, Object>> ckDataList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("sum_签约达人机构GMV", new BigDecimal("373.38"));
        param.put("达人昵称", "琪琪美食分享");
        ckDataList.add(param);
        List<Object> dynamicColumnValueList = ckDataList.stream()
                .map(item -> item.get(finalDynamicColumnField)).distinct().sorted().collect(Collectors.toList());
        System.out.println(dynamicColumnValueList);

    }

}
