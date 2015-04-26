package ro.tekin.disertatie.util;

/**
 * Created by tekin on 3/11/14.
 */
public class Picture {
    private String name;
    private long size;
    private String path;

    public Picture(){}
    public Picture(String name, long size) {
        this.name = name;
        this.size = size;
    }
    public Picture(String name, long size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture picture = (Picture) o;

        if (size != picture.size) return false;
        if (name != null ? !name.equals(picture.name) : picture.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}