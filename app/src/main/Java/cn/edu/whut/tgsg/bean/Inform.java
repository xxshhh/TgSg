package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 通知
 * <p/>
 * Created by ylj on 2015-12-25.
 */
public class Inform implements Serializable {

    int id;
    User receiver;
    String time;
    int read;
    String title;
    String content;

    public Inform() {
    }

    public Inform(int id, User receiver, String time, int read, String title, String content) {
        this.id = id;
        this.receiver = receiver;
        this.time = time;
        this.read = read;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
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
}
