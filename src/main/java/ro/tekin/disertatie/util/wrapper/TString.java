package ro.tekin.disertatie.util.wrapper;

/**
 * Created by tekin on 3/9/14.
 */
public class TString {
    private String value;

    public TString(){}
    public TString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TString{" +
                "value='" + value + '\'' +
                '}';
    }
}
