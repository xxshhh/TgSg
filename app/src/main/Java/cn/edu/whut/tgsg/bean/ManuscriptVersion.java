package cn.edu.whut.tgsg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 稿件版本
 * <p/>
 * Created by xwh on 2015/12/11.
 */
public class ManuscriptVersion implements Serializable {

    /**
     * 编号 : 1
     * // 稿件编号 : Manuscript
     * 标题 : 乖，摸摸头
     * 摘要 : 真实的故事自有万钧之力，本书讲述了12个真实的故事。或许会让你看到那些你永远无法去体会的生活，见识那些可能你永远都无法结交的人。
     * 关键字 : 大冰 旅行 治愈 散文随笔
     * 存储路径 : url
     * 版本时间 : 2015-12-11 10:45:21
     */

    int id;
    String title;
    String summary;
    List<String> keywords;
    String url;
    String date;

    public ManuscriptVersion(int id, String title, String summary, List<String> keywords, String url, String date) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.keywords = keywords;
        this.url = url;
        this.date = date;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
