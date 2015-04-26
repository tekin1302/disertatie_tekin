package ro.tekin.disertatie.bean;

/**
 * Created by diana on 4/13/14.
 */
public class NewMessageFlag {
    private Integer chatRoomId;
    private Integer employeeId;

    public NewMessageFlag(){}
    public NewMessageFlag(Integer chatRoomId, Integer employeeId){
        this.chatRoomId = chatRoomId;
        this.employeeId = employeeId;
    }
    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewMessageFlag that = (NewMessageFlag) o;

        if (chatRoomId != null ? !chatRoomId.equals(that.chatRoomId) : that.chatRoomId != null) return false;
        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chatRoomId != null ? chatRoomId.hashCode() : 0;
        result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
        return result;
    }
}
