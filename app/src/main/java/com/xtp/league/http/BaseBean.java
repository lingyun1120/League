package com.xtp.league.http;

public class BaseBean {
    private boolean error;
    private int errorCode;
    private String errorMessage;

    public boolean isSuccess() {
        return !error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
