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
     * 时间 : contributeTime
     */

    int id;
    ManuscriptVersion articleVersion;
    User editor;
    User expert;
    String distributeTime;

    public DistributeExpert() {
    }

    public DistributeExpert(int id, ManuscriptVersion articleVersion, User editor, User expert, String distributeTime) {
        this.id = id;
        this.articleVersion = articleVersion;
        this.editor = editor;
        this.expert = expert;
        this.distributeTime = distributeTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ManuscriptVersion getArticleVersion() {
        return articleVersion;
    }

    public void setArticleVersion(ManuscriptVersion articleVersion) {
        this.articleVersion = articleVersion;
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

    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
    }
}
