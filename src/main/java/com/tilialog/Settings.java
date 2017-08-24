package com.tilialog;

public interface Settings {

    void put(String key, Object value);

    String get(String key);
}