package cn.edu.whut.tgsg.bean;

/**
 * Created by ylj on 2015-11-27.
 */
public class Author {

    private int authorId;
    private String username;
    private String email;
    private String tel;
    private String edubkg;
    private String desc;//个人简介

    public Author(){

    }



    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEdubkg() {
        return edubkg;
    }

    public void setEdubkg(String edubkg) {
        this.edubkg = edubkg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
