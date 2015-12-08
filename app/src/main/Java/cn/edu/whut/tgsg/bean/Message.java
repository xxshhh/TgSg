package cn.edu.whut.tgsg.bean;

/**
 * 消息
 * <p/>
 * Created by xwh on 2015/12/1.
 */
public class Message {

    /**
     * title : 系统消息
     * content : "您的稿件“乖，摸摸头”状态已变更为“刊登”"
     * date : "17:55"
     * check : false
     */

    private String title;
    private String content;
    private String date;
    private boolean check;

    public Message(String title, String content, String date, boolean check) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
