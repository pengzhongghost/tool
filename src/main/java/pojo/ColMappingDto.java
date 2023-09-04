package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengzhong
 * @since 2023/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColMappingDto {

    /** 字段名 */
    private String name;
    /** 字段别名 */
    private String alias;
    /** 关联表 */
    private Object table;
    /** 表别名 */
    private Object tableAlias;
    /** 字段类型 */
    private String type;


}
