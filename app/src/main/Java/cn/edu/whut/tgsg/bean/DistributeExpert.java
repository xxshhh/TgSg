package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 分配专家
 * <p/>
 * Created by xwh on 2015/12/20.
 */
public class DistributeExpert implements Serializable {

    /**
     * 编号 : 1
     * 稿件版本 : ManuscriptVersion
     * 编辑 : editor
     * 专家 : expert
     * 时间 : date
     */

    int id;
    ManuscriptVersion manuscriptVersion;
    User editor;
    User expert;
    String date;

    public DistributeExpert() {
    }

    public DistributeExpert(int id, ManuscriptVersion manuscriptVersion, User editor, User expert, String date) {
        this.id = id;
        this.manuscriptVersion = manuscriptVersion;
        this.editor = editor;
        this.expert = expert;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ManuscriptVersion getManuscriptVersion() {
        return manuscriptVersion;
    }

    public void setManuscriptVersion(ManuscriptVersion manuscriptVersion) {
        this.manuscriptVersion = manuscriptVersion;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public User getExpert() {
        return expert;
    }

    public void setExpert(User expert) {
        this.expert = expert;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
