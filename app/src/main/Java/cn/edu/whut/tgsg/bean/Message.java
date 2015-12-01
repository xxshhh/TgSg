package cn.edu.whut.tgsg.bean;

/**
 * 消息
 * <p/>
 * Created by xwh on 2015/12/1.
 */
public class Message {

    private int icon;
    private int title;
    private String date;
    private String time;

    public Message(int icon, int title, String date, String time) {
        this.icon = icon;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
