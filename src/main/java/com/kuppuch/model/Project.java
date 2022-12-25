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
    private int manager;

    @SerializedName(value = "budget")
    private String iteration;
    @SerializedName(value = "ext_id")
    private int external;

    public Project() {}

    public Project(String name, String description, int manager, int status, String iteration, int external) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.status = status;
        this.iteration = iteration;
        this.external = external;
    }

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

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public int getExternal() {
        return external;
    }

    public void setExternal(int external) {
        this.external = external;
    }
}
