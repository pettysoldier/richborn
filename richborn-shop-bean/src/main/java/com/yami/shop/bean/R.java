package com.yami.shop.bean;

/**
 * @author ly
 * @desc
 * @date 2021/9/16
 */
public class R<T> {

    private int returncode;
    private String message;
    private T result;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static R builder(){
        return new R();
    }

    public R result(T t){
        this.result=t;
        return this;
    }
    public R returncode(int code){
        this.returncode=code;
        return this;
    }

    public R message(String message){
        this.message=message;
        return this;
    }


    public  R build(){
        return this;
    }

    public static <T> R ok(T t){
        return R.builder()
                .result(t)
                .returncode(0)
                .build();
    }



    public static  R fail(int returncode,String message){
        return R.builder()
                .returncode(returncode)
                .message(message)
                .build();
    }

    public static  R fail(String message){
        return R.builder()
                .returncode(-1)
                .message(message)
                .build();
    }

}

