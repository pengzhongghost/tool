package pojo;

/**
 * @author pengzhong
 * @since 2023/3/30
 */
public enum CodeEnum {

    /**
     *
     **/
    PARAM_EMPTY("10000", "您的输入,缺少必填参数!"), DATA_CREATE_FAIL("10002", "数据插入失败!"), DATA_UPDATE_FAIL("10003",
                                                                                                         "数据更新失败!"), DATA_DELETE_FAIL("10004", "数据删除失败!"), DATA_QUERY_FAIL("10006", "数据查询失败!"), DATA_PARAM_FAIL("10007",
            "参数错误!"), DATA_PERMISSION_IN_USED("10005", "该权限还在使用中,请先解除关联!"), DATA_REPEAT("10001", "数据已经存在!"),

    ERROR_NEED_CHOSE_USER("20001", "请选择登录用户!"),

    PERMISSION_DENIED_FAIL("10009", "权限不足"), INTERNAL_ERROR("500", "系统内部错误"), NOT_FOUND_ERROR("404",
                                                                                                      "找不到请求内容"), EXIST_APPROVAL("10011", "已存在字段申请表单，请耐心等候审批！"), EMPTY_FIELDS("10012",
                                                                                                                                                                                      "无法解析SQL字段，SELECT请避免使用*"), LOGIN_ERROR("10013", "登陆失败"), //限流
    ERROR_QUERY_LIMIT("20003", "服务器忙，请稍后重试");

    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
