package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 公告
 * <p/>
 * Created by xwh on 2015/12/16.
 */
public class Notice implements Serializable {

    /**
     * 编号 : 1
     * 标题 : 关于余区管委会工人技师评审组拟推荐评聘万荣为技师的情况公示
     * 时间 : 2015-12-15
     * 内容 : 为做好2015年工人技师岗位聘用工作，根据《武汉理工大学工人技师岗位聘用管理办法》和学校《关于开展2015年工人技师岗位聘用工作的通知》精神，余区管委会评审组严格按照学校要求，结合《余区管委会工人技师岗位聘用评审细则》（试行）、《余区管委会工人技师评聘综合量化计分标准》，在听取个人述职的基础上，根据个人条件、工作表现和业绩，综合最终得分情况，将排序第一的万荣进行公示，公示期12月16日—12月18日，公示期间受理申诉。
     */

    int id;
    String title;
    String noticeTime;
    String content;

    public Notice() {
    }

    public Notice(int id, String title, String noticeTime, String content) {
        this.id = id;
        this.title = title;
        this.noticeTime = noticeTime;
        this.content = content;
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

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
