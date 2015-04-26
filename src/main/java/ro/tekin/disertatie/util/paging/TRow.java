package ro.tekin.disertatie.util.paging;

/**
 * Created by tekin on 3/8/14.
 */
public class TRow {
    private Integer id;
    private Object cell;

    public TRow(){}
    public TRow(Integer id, Object cell) {
        this.id = id;
        this.cell = cell;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCell() {
        return cell;
    }

    public void setCell(Object cell) {
        this.cell = cell;
    }
}
