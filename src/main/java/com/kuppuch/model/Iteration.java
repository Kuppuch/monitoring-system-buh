package com.kuppuch.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Iteration {
    @SerializedName("ID")
    private int id;
    @SerializedName("CreatedAt")
    private Date created_at;
    @SerializedName("UpdatedAt")
    private Date updated_at;
    private String name;
    @SerializedName("ext_id")
    private int extId;
    @SerializedName("project_id")
    private int projectId;
    @SerializedName("start_at")
    private Date startAt;
    @SerializedName("end_at")
    private Date endAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExtId() {
        return extId;
    }

    public void setExtId(int extId) {
        this.extId = extId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
}
