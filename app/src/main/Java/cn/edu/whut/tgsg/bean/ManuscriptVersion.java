package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 稿件版本
 * <p/>
 * Created by xwh on 2015/12/11.
 */
public class ManuscriptVersion implements Serializable {

    /**
     * 编号 : 1
     * 稿件编号 : Manuscript
     * 标题 : 乖，摸摸头
     * 摘要 : 真实的故事自有万钧之力，本书讲述了12个真实的故事。或许会让你看到那些你永远无法去体会的生活，见识那些可能你永远都无法结交的人。
     * 关键字 : 大冰 旅行 治愈 散文随笔
     * 存储路径 : path
     * 版本时间 : 2015-12-11 10:45:21
     */

    int id;
    Manuscript article;
    String title;
    String summary;
    String keyword;
    String path;
    String versionTime;

    public ManuscriptVersion() {
    }

    public ManuscriptVersion(int id, Manuscript article, String title, String summary, String keyword, String path, String versionTime) {
        this.id = id;
        this.article = article;
        this.title = title;
        this.summary = summary;
        this.keyword = keyword;
        this.path = path;
        this.versionTime = versionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Manuscript getArticle() {
        return article;
    }

    public void setArticle(Manuscript article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(String versionTime) {
        this.versionTime = versionTime;
    }
}
