package ro.tekin.disertatie.entity;

import javax.persistence.*;

/**
 * Created by diana on 4/6/14.
 */
@Entity
@Table(name = "organigram_element")
public class OrganigramElement {
    private Integer id;
    private Employee up;
    private Employee down;
    private Organigram organigram;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "up", referencedColumnName = "id", nullable = false)
    public Employee getUp() {
        return up;
    }

    public void setUp(Employee up) {
        this.up = up;
    }

    @ManyToOne
    @JoinColumn(name = "down", referencedColumnName = "id", nullable = false)
    public Employee getDown() {
        return down;
    }

    public void setDown(Employee down) {
        this.down = down;
    }

    @ManyToOne
    @JoinColumn(name = "organigram_id", referencedColumnName = "id", nullable = false)
    public Organigram getOrganigram() {
        return organigram;
    }

    public void setOrganigram(Organigram organigram) {
        this.organigram = organigram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganigramElement that = (OrganigramElement) o;

        if (down != null ? !down.equals(that.down) : that.down != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (up != null ? !up.equals(that.up) : that.up != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (up != null ? up.hashCode() : 0);
        result = 31 * result + (down != null ? down.hashCode() : 0);
        return result;
    }
}
