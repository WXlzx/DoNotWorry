package notworry.rj.edu.notworry.Entity;

public class ChatMsgEntity {

    private String name;//消息来自
    private String date;//消息日期
    private String message;//消息内容
    private boolean isSend = true;//是否为发送的消息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getMsgType() {
        return isSend;
    }

    public void setMsgType(boolean isSend) {
        this.isSend = isSend;
    }

    public ChatMsgEntity() {

    }

    public ChatMsgEntity(String name, String date, String message,
                         boolean isSend,int chatdetails_id,int chatgroup_id,int user_id) {
        this.name = name;
        this.date = date;
        this.message = message;
        this.isSend = isSend;
    }
}
