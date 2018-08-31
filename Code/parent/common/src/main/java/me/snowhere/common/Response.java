package me.snowhere.common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Setter
    @Getter
    private int flag;
    private String message;
    private Object data;

    public static Response OK = new Response(Constant.SUCCESS_NUM, Constant.SUCCESS_MESSAGE, null);

    public static Response FAIL = new Response(Constant.FAIL_NUM, Constant.FAIL_MESSAGE, null);

    public static Response ok(Object data){
        return new Response(Constant.SUCCESS_NUM, Constant.SUCCESS_MESSAGE, data);
    }
}
