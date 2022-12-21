package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

public class TimespentReport {

    @SerializedName("ID")
    private int id;
    @SerializedName("spent")
    private double spent;
    @SerializedName("issue_name")
    private String issueName;
    @SerializedName("developer")
    private String developer;
    @SerializedName("role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
