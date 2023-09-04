package pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhong
 * @since 2023/3/30
 */
@Data
public class ServiceResult<T> implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;

    private T data;

    public static <T> ServiceResult<T> fail(String code, String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ServiceResult<T> fail(ServiceResult serviceResult) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setCode(serviceResult.getCode());
        result.setMessage(serviceResult.getMessage());
        return result;
    }

    public static <T> ServiceResult<T> fail(CodeEnum code) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        return result;
    }

    public static <T> ServiceResult<T> fail(String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setCode(CodeEnum.INTERNAL_ERROR.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> ServiceResult<T> success(T data) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ServiceResult<T> success() {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(true);
        return result;
    }

    public static <T> ServiceResult<T> fail() {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        return result;
    }
}