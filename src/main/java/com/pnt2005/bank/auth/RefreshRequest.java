package com.pnt2005.bank.auth;

public class RefreshRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
