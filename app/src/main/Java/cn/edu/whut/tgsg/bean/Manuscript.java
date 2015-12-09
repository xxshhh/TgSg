package cn.edu.whut.tgsg.bean;

import java.util.List;

/**
 * 稿件
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class Manuscript {

    /**
     * title : 乖，摸摸头
     * keywords : 大冰 旅行 治愈 散文随笔
     * summary : 讲述了12个真实的传奇故事,或许会让你看到那些你永远无法去体... （更多）（30字）
     * state : 6
     */

    String title;
    List<String> keywords;
    String summary;
    int state;

    public Manuscript(String title, List<String> keywords, String summary, int state) {
        this.title = title;
        this.keywords = keywords;
        this.summary = summary;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
