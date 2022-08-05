package org.framework.integration.common.core.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * TODO  通过HttpStatus  进行优化
 * Created  on 2022/7/29 11:11:21
 *
 * @author zl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseEntity<T> {

    private int code;

    private String message;

    private T data;

    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<T>(HttpStatus.OK.value(), "请求成功", null);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity<T>(HttpStatus.OK.value(), "请求成功", data);
    }

    public static <T> ResponseEntity<T> ok(T data, String message) {
        return new ResponseEntity<T>(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResponseEntity<T> fail(T data, String message) {
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

    public static <T> ResponseEntity<T> fail(int code, String message) {
        return new ResponseEntity<T>(code, message, null);
    }

    public static <T> ResponseEntity<T> fail(String message) {
        return new ResponseEntity<T>().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(message);
    }

}
