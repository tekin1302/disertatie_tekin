package ro.tekin.disertatie.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by diana on 4/7/14.
 */
@Entity
@Table(name = "chat_room", schema = "", catalog = "disertatie")
public class ChatRoom {
    private Integer id;
    private String name;
    private Date dateCreated;
    private Company company;

    public ChatRoom() {}
    public ChatRoom(Integer id) {
        this.id = id;
    }
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

        ChatRoom chatRoom = (ChatRoom) o;

        if (dateCreated != null ? !dateCreated.equals(chatRoom.dateCreated) : chatRoom.dateCreated != null)
            return false;
        if (id != null ? !id.equals(chatRoom.id) : chatRoom.id != null) return false;
        if (name != null ? !name.equals(chatRoom.name) : chatRoom.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
}
