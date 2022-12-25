package com.kuppuch.model;

import javax.persistence.*;

@Entity
public class RoleWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int roleId;

    @ManyToOne
    @JoinColumn(name = "workId", insertable = false, updatable = false)
    private Work workId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int role) {
        this.roleId = role;
    }

    public Work getWorkId() {
        return workId;
    }

    public void setWorkId(Work workId) {
        this.workId = workId;
    }


}
