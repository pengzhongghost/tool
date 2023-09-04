package pojo;

import java.util.Objects;

/**
 * @author pengzhong
 * @since 2023/8/11
 */
public class TestPojo implements Comparable<TestPojo>{

    private Integer id;

    private String name;

    private Integer age;

    @Override
    public int compareTo(TestPojo o) {
        return 0;
    }
}
