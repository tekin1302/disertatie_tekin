package ro.tekin.disertatie.util.wrapper;

/**
 * Created by tekin on 3/9/14.
 */
public class TBoolean {
    private Boolean value;

    public TBoolean() {}
    public TBoolean(Boolean b) {
        this.value = b;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
