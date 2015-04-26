package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by tekin.omer on 7/14/2014.
 */
//@Entity
//@Table(name = "category", schema = "", catalog = "disertatie")
public class Category {
    private Integer id;
    private String name;

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
}
