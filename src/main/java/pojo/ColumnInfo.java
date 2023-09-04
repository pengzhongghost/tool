package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengzhong
 * @since 2023/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnInfo {

    /** 字段名 */
    private String name;
    /** 字段类型 */
    private String type;

}
