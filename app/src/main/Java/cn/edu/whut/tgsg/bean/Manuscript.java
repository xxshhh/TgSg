package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 稿件
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class Manuscript implements Serializable {

    /**
     * 编号 : 1
     * 类别 : 随笔
     * 投稿人 : 当前用户
     * 投稿时间 : 2015-12-11 10:35:10
     * 状态 : 1-6
     * 最新版本 : ManuscriptVersion
     */

    int id;
    String sort;
    User user;
    String date;
    int state;
    ManuscriptVersion manuscriptVersion;

    public Manuscript() {
    }

    public Manuscript(int id, String sort, User user, String date, int state, ManuscriptVersion manuscriptVersion) {
        this.id = id;
        this.sort = sort;
        this.user = user;
        this.date = date;
        this.state = state;
        this.manuscriptVersion = manuscriptVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ManuscriptVersion getManuscriptVersion() {
        return manuscriptVersion;
    }

    public void setManuscriptVersion(ManuscriptVersion manuscriptVersion) {
        this.manuscriptVersion = manuscriptVersion;
    }
}
