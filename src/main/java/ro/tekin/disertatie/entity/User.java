package ro.tekin.disertatie.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

/**
 * Created by tekin on 3/15/14.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Integer id;
    private String email;
    @JsonIgnore
    private String password;
    private Integer role;
    private Boolean active;

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
    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @JsonIgnore
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Basic
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

        User user = (User) o;

        if (active != null ? !active.equals(user.active) : user.active != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}
