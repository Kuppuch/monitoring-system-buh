package com.kuppuch.model;

import java.util.*;

public class Timespent {
    private int id;
    private Date created_at;
    private Date updated_at;
    private int issueId;
    private int userId;
    private int roleId;
    private double spent;

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

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public TimespentReport[] collapse(TimespentReport[] reports) {
        Map<String, TimespentReport> trCollapse = new HashMap<String, TimespentReport>();
        for (TimespentReport tr : reports) {
            if (trCollapse.size() == 0) {
                TimespentReport ntr = tr.clone();
                trCollapse.put(ntr.getIssueName() + ntr.getRole(), ntr);
                continue;
            }
            TimespentReport ntr = trCollapse.get(tr.getIssueName() + tr.getRole());
            if (ntr != null) {
                ntr.setSpent(ntr.getSpent() + tr.getSpent());
                trCollapse.replace(tr.getIssueName() + tr.getRole(), ntr);
            } else {
                trCollapse.put(tr.getIssueName() + tr.getRole(), tr);
            }

        }

        TimespentReport[] returnTR = new TimespentReport[trCollapse.size()];
        int i = 0;
        for (Map.Entry<String, TimespentReport> tr : trCollapse.entrySet()) {
            returnTR[i] = tr.getValue();
            i++;
        }
        return returnTR;
    }
}
