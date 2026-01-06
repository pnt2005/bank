package com.pnt2005.bank.auth;

public class RefreshResponse {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public RefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
