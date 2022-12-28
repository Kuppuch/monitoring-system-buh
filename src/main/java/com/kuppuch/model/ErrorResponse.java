package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("Code")
    private int code;

    @SerializedName("Status")
    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
