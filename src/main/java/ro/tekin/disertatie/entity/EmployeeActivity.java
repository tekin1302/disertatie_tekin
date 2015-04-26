package ro.tekin.disertatie.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import ro.tekin.disertatie.util.TDateDeserializer;
import ro.tekin.disertatie.util.TDateSerializer;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by diana on 4/7/14.
 */
@Entity
@Table(name = "employee_activity", schema = "", catalog = "disertatie")
public class EmployeeActivity {
    private Integer id;
    private Employee employee;
    private Activity activity;
    private Date start;
    private Date end;

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
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @JsonSerialize(using = TDateSerializer.class)
    @JsonDeserialize(using=TDateDeserializer.class)
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @JsonSerialize(using = TDateSerializer.class)
    @JsonDeserialize(using=TDateDeserializer.class)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeActivity chatRoom = (EmployeeActivity) o;

        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
