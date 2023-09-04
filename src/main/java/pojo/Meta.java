package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengzhong
 * @since 2023/5/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meta {

    private String EN;

    private String CN;

    private String Des;

}
