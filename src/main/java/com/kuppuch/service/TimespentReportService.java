package com.kuppuch.service;

import com.kuppuch.model.TimespentReport;

import java.util.HashMap;
import java.util.Map;

public class TimespentReportService {

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

    public double getSum(TimespentReport[] reports) {
        double sum = 0;
        for (TimespentReport tr : reports) {
            sum += tr.getCoast();
        }
        return sum;
    }

    public TimespentReport[] deepClone(TimespentReport[] timespentReports) {
        TimespentReport[] deepClone = new TimespentReport[timespentReports.length];

        for (int i = 0; i < timespentReports.length; i++) {
            deepClone[i] = timespentReports[i].clone();
        }

        return deepClone;
    }

}
