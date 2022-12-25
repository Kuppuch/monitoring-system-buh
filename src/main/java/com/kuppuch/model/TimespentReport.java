package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

public class TimespentReport implements Cloneable {

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

    @SerializedName("role_id")
    private int roleID;

    private double coast;

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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public double getCoast() {
        return coast;
    }

    public void setCoast(double coast) {
        this.coast = coast;
    }

    @Override
    public TimespentReport clone() {
        try {
            TimespentReport clone = (TimespentReport) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
