package ro.tekin.disertatie.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import ro.tekin.disertatie.util.TUtils;

import javax.persistence.*;
import java.io.IOException;

/**
 * Created by tekin on 3/15/14.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String fax;
    private String city;
    private TFile logo;
    private User user;
    private String logoStr;

    public Company() {}
    public Company(Integer id) {
        this.id = id;
    }
    public static Company getInstance(String json) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return m.readValue(json, Company.class);
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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 130, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "fax", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "city", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(name = "logo", referencedColumnName = "id")
    public TFile getLogo() {
        return logo;
    }

    public void setLogo(TFile logo) {
        this.logo = logo;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (address != null ? !address.equals(company.address) : company.address != null) return false;
        if (city != null ? !city.equals(company.city) : company.city != null) return false;
        if (fax != null ? !fax.equals(company.fax) : company.fax != null) return false;
        if (id != null ? !id.equals(company.id) : company.id != null) return false;
        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (phone != null ? !phone.equals(company.phone) : company.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Transient
    public String getLogoStr() {
        return logoStr;
    }

    public void setLogoStr(String logoStr) {
        this.logoStr = logoStr;
    }
    public void initLogoData() {
        if (this.logo != null) {
            this.logoStr = TUtils.encode64(this.logo.getData(), this.logo.getMimeType());
        }
    }
}
