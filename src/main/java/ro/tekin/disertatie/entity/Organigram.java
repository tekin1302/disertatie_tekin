package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by diana on 4/6/14.
 */
@Entity
public class Organigram {
    private Integer id;
    private Company company;
    private String data;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyByCompanyId) {
        this.company = companyByCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organigram that = (Organigram) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
