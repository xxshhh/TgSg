package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 留言消息
 * <p/>
 * Created by xwh on 2015/12/27.
 */
public class Message implements Serializable {

    int id;
    User sender;
    User receiver;
    String message;
    int status;
    Manuscript article;
    String messageTime;

    public Message() {
    }

    public Message(int id, User sender, User receiver, String message, int status, Manuscript article, String messageTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.status = status;
        this.article = article;
        this.messageTime = messageTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Manuscript getArticle() {
        return article;
    }

    public void setArticle(Manuscript article) {
        this.article = article;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}