package com.macro.mall.tiny.modules.dlm.exception;

public class MyException extends RuntimeException{

    private long code;

    public MyException(long code, String msg){
        super(msg);
        this.code = code;
    }

    public MyException(MyEumException e){
        super(e.getMessage());
        this.code = e.getCode();
    }

    public long getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
