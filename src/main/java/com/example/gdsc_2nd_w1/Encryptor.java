package com.example.gdsc_2nd_w1;

public interface Encryptor {
    String encrypt(String password);
    boolean isMatch(String password, String hashed);
}
