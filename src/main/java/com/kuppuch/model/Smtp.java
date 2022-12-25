package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

public class Smtp {

    @SerializedName(value = "recipient")
    private String email;

    @SerializedName(value = "code")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
