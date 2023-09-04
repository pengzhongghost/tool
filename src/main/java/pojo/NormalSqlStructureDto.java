package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author pengzhong
 * @since 2023/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NormalSqlStructureDto {

    /**
     * SQL语句
     */
    private String sql;
    /**
     * 表名
     */
    private List<String> tableNames;
    /**
     * 检索项
     */
    private List<String> selectItems;
    /**
     * 字段和表的映射关系
     */
    private List<ColMappingDto> colMappings;
    /**
     * 表别名映射
     */
    private Map<String, Object> tableAliasMapping;


}
