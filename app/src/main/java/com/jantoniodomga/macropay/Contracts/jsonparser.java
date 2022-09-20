package com.jantoniodomga.macropay.Contracts;

public interface jsonparser<T> {
    public String toJson();
    public T fromJson(String jsonvalue);
}
