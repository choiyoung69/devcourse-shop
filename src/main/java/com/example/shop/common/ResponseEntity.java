package com.example.shop.common;

public class ResponseEntity<T> {
    private final int status;
    private final T data;
    private final long count;

    public ResponseEntity(int status, T data, long count) {
        this.status = status;
        this.data = data;
        this.count = count;
    }
}
