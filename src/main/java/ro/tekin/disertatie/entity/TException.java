package ro.tekin.disertatie.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ro.tekin.disertatie.util.TUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by tekin on 3/15/14.
 */
@Entity
@Table(name = "t_exception")
@JsonIgnoreProperties(ignoreUnknown = true, value = "image")
public class TException {
    private Integer id;
    private String stacktrace;
    private byte[] image;
    private Date date;
    private String imageString;

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
    @Column(name = "stacktrace", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    @Basic
    @Column(name = "image", nullable = true, insertable = true, updatable = true, length = 10485760, precision = 0)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TException exception = (TException) o;

        if (date != null ? !date.equals(exception.date) : exception.date != null) return false;
        if (id != null ? !id.equals(exception.id) : exception.id != null) return false;
        if (!Arrays.equals(image, exception.image)) return false;
        if (stacktrace != null ? !stacktrace.equals(exception.stacktrace) : exception.stacktrace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stacktrace != null ? stacktrace.hashCode() : 0);
        result = 31 * result + (image != null ? Arrays.hashCode(image) : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Transient
    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void initImageString() {
        this.imageString = TUtils.encode64(this.image, "image/png");
    }
}
