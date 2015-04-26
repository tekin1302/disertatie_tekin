package ro.tekin.disertatie.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tekin on 6/3/14.
 */
public class TSStatisticsBean {
    private String employeeName;
    private Long start;
    private Long end;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
