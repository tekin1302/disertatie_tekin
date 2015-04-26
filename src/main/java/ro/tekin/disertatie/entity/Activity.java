package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by diana on 4/7/14.
 */
@Entity
@Table(name = "activity", schema = "", catalog = "disertatie")
public class Activity {
    private Integer id;
    private Company company;
    private String name;
    private String description;
    private Float value;
    private Float duration;
    private Boolean active;

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
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
    @Column(name = "active", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity chatRoom = (Activity) o;

        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
