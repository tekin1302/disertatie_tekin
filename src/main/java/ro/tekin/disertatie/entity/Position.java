package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by tekin on 3/15/14.
 */
@Entity
public class Position {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private Company createdBy;

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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 150, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "active", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean global) {
        this.active = global;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (description != null ? !description.equals(position.description) : position.description != null)
            return false;
        if (active != null ? !active.equals(position.active) : position.active != null) return false;
        if (id != null ? !id.equals(position.id) : position.id != null) return false;
        if (name != null ? !name.equals(position.name) : position.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    public Company getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Company createdBy) {
        this.createdBy = createdBy;
    }
}
