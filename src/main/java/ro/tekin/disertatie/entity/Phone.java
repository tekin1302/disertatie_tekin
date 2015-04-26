package ro.tekin.disertatie.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by diana on 4/7/14.
 */
//@Entity
//@Table(name = "phone", schema = "", catalog = "disertatie")
public class Phone {
    private Integer id;
    private String name;
    private Float price;
    private TFile photo;
    private String description;
    private String url;
    private List<PhoneAttribute> attributeList;

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

    @Column(name = "description", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "photo", referencedColumnName = "id")
    public TFile getPhoto() {
        return photo;
    }

    public void setPhoto(TFile photo) {
        this.photo = photo;
    }

    @OneToMany (mappedBy = "phone", cascade = CascadeType.ALL)
    public List<PhoneAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<PhoneAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    @Column (name = "model_url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone chatRoom = (Phone) o;

        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
