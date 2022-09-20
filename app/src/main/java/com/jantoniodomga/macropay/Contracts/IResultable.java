package com.jantoniodomga.macropay.Contracts;

import com.google.zxing.WriterException;

public interface IResultable <T>{
    void onSuccess(T result);
    void onFailure(Exception e);
}
