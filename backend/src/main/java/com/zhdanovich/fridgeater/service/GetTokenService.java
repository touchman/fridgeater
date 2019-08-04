package com.zhdanovich.fridgeater.service;

public interface GetTokenService {

    String getToken(String username, String password) throws Exception;
}
