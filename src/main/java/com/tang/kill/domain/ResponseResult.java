package com.tang.kill.domain;

import lombok.Data;

/**
 * @Classname ResponseResult
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/20 23:16
 * @Created by ASUS
 */
@Data
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer state;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    public ResponseResult() { }

    public ResponseResult(Integer state) { }

    public ResponseResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public ResponseResult(Integer state, Throwable throwable) {
        this.state = state;
        this.message = throwable.getMessage();
    }

    public ResponseResult(Integer state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public static <M> ResponseResult<M> Ok( M data){
        return new ResponseResult<M>(200, "success!", data);
    }


    public static <M> ResponseResult<M> Fail( M data){
        return new ResponseResult<M>(400, "fail!", data);
    }


}