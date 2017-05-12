package util;

import lombok.Data;

/**
 * common response
 *
 * @author STH
 * @create 2017-05-12
 **/
@Data
public class Response {
    private int code;
    private String msg;
    private Object obj;
}
