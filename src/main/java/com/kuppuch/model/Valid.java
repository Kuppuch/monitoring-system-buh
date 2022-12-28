package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

public class Valid {
    @SerializedName("valid")
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
