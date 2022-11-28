package com.kuppuch.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName(value = "ID")
    private int id;
    private String name;
    private String description;
    private boolean isPublic;
    private int status;
    @SerializedName(value = "IssuesCnt")
    private int issuesCnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIssuesCnt() {
        return issuesCnt;
    }

    public void setIssuesCnt(int issuesCnt) {
        this.issuesCnt = issuesCnt;
    }
}
