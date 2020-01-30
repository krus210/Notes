package com.krus210.notes;

public interface Keystore {
    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);
}
