package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 审稿
 * <p/>
 * Created by xwh on 2015/12/20.
 */
public class ExamineManuscript implements Serializable {

    /**
     * 编号 : 1
     * 审稿人 : 金庸
     * 稿件 : Manuscript
     * 审稿意见 : "这世界有另一种人，他们的生活模式与朝九晚五格格不入，却也活得有血有肉，有模有样。世界上还有另一种人，他们既可以朝九晚五，又可以浪荡天涯，比如大冰。"
     * 审稿结果 : 1
     * 审稿时间 : 2015-12-20 11:53:48
     */

    int id;
    User user;
    Manuscript manuscript;
    String opinion;
    int result;
    String date;

    public ExamineManuscript() {
    }

    public ExamineManuscript(int id, User user, Manuscript manuscript, String opinion, int result, String date) {
        this.id = id;
        this.user = user;
        this.manuscript = manuscript;
        this.opinion = opinion;
        this.result = result;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
