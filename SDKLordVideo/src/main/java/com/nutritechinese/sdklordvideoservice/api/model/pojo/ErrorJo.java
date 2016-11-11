package com.nutritechinese.sdklordvideoservice.api.model.pojo;

public class ErrorJo {
    private int errorCode;
    private String errorMessage;

    public ErrorJo() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
