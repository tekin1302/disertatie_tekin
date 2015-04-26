package ro.tekin.disertatie.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by tekin on 3/15/14.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private Integer id;
    private String name;
    private Position position;
    private String phone;
    private TFile photo;
    private Company company;
    private User user;
    private Boolean active;
    private Float rate;

    public Employee() {}
    public Employee(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = false)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 15, precision = 0)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne
    @JoinColumn(name = "photo", referencedColumnName = "id")
    public TFile getPhoto() {
        return photo;
    }

    public void setPhoto(TFile photo) {
        this.photo = photo;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "active", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyByCompanyId) {
        this.company = companyByCompanyId;
    }
}
