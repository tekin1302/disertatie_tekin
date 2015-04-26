package ro.tekin.disertatie.bean;

/**
 * Created by diana on 4/13/14.
 */
public class MessBean {
    private Integer id;
    private String user;
    private String text;

    public MessBean() {}
    public MessBean(Integer id, String user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
