package com.google.maps.android;


public interface Command<T> {
    void execute(T data);
}
