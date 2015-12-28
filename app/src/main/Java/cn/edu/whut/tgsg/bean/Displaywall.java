package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 展示墙
 * <p/>
 * Created by xwh on 2015/12/28.
 */
public class Displaywall implements Serializable {

    /**
     * 公告编号
     * 公告标题
     * 公告内容
     * 公告发布时间
     * 图片
     * 概要
     */
    int id;
    String title;
    String content;
    String publishtime;
    String imgpath;
    String summary;

    public Displaywall() {
    }

    public Displaywall(String summary, int id, String title, String content, String publishtime, String imgpath) {
        this.summary = summary;
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishtime = publishtime;
        this.imgpath = imgpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
