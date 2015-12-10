package cn.edu.whut.tgsg.bean;

import java.util.List;

/**
 * 稿件详情
 * <p/>
 * Created by xwh on 2015/12/10.
 */
public class ManuscriptDetail {

    /**
     * title : 乖，摸摸头
     * versions :
     * comments :
     */

    String title;
    List<String> version;
    List<String> comment;

    public ManuscriptDetail(String title, List<String> version, List<String> comment) {
        this.title = title;
        this.version = version;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getVersion() {
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }
}
