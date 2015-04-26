package ro.tekin.disertatie.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by diana on 4/6/14.
 */
@Entity
@Table(name = "t_message")
public class TMessage {
    private Integer id;
    private String text;
    private Employee employee;
    private ChatRoom chatRoom;
    private Date date;

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
    @Column(name = "text", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id", nullable = false)
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TMessage that = (TMessage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
