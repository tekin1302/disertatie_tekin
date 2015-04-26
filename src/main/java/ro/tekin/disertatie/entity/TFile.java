package ro.tekin.disertatie.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by diana on 3/17/14.
 */
@Entity
@Table(name = "t_file")
@JsonIgnoreProperties(ignoreUnknown = true, value = "data")
public class TFile {
    private Integer id;
    private String name;
    private byte[] data;
    private Long size;
    private String mimeType;
    private Boolean resized = false;

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
    @Column(name = "data", nullable = true, insertable = true, updatable = true, length = 10485760, precision = 0)
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Basic
    @Column(name = "size")
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Basic
    @Column(name = "mime_type")
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Basic
    @Column(name = "resized", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getResized() {
        return resized;
    }

    public void setResized(Boolean resized) {
        this.resized = resized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFile tFile = (TFile) o;

        if (!Arrays.equals(data, tFile.data)) return false;
        if (id != null ? !id.equals(tFile.id) : tFile.id != null) return false;
        if (name != null ? !name.equals(tFile.name) : tFile.name != null) return false;
        if (size != null ? !size.equals(tFile.size) : tFile.size != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (data != null ? Arrays.hashCode(data) : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }
}
