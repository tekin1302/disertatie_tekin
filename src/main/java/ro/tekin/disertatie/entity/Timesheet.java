package ro.tekin.disertatie.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import ro.tekin.disertatie.util.TDateDeserializer;
import ro.tekin.disertatie.util.TDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by diana on 4/7/14.
 */
@Entity
@Table(name = "timesheet", schema = "", catalog = "disertatie")
public class Timesheet {
    private Integer id;
    private EmployeeActivity employeeActivity;
    private String description;
    private Float hours;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "employee_activity_id", referencedColumnName = "id", nullable = false)
    public EmployeeActivity getEmployeeActivity() {
        return employeeActivity;
    }

    public void setEmployeeActivity(EmployeeActivity employeeActivity) {
        this.employeeActivity = employeeActivity;
    }

    @Column
    @Size(max = 2000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @JsonSerialize(using = TDateSerializer.class)
    @JsonDeserialize(using=TDateDeserializer.class)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timesheet chatRoom = (Timesheet) o;

        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
