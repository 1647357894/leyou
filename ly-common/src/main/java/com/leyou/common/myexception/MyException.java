package com.leyou.common.myexception;

public class MyException extends RuntimeException {
    public MyException(LyException exception) {
        super(exception.toString());
    }
}
