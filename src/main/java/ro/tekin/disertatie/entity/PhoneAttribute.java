package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by diana on 4/7/14.
 */
//@Entity
//@Table(name = "phone_attribute", schema = "", catalog = "disertatie")
public class PhoneAttribute {
    private Integer id;
    private String name;
    private String value;
    private Phone phone;
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @ManyToOne
    @JoinColumn(name = "phone_id", referencedColumnName = "id", nullable = false)
    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneAttribute chatRoom = (PhoneAttribute) o;

        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
