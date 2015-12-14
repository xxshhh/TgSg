package cn.edu.whut.tgsg.bean;

/**
 * 用户
 * <p/>
 * Created by ylj on 2015/12/14.
 */
public class User {

    /**
     * id：1
     * email：107603@163.com
     * password：123
     * username：张三
     * age：12
     * tel：182123392
     * profileImage：url
     * role:1-------
     * degree：大学
     * major：计算机
     * researchDirection：机器学习
     * workPlace：武汉理工
     * desc：一名医生
     */

    int id;
    String email;
    String password;
    String username;
    int age;
    String tel;
    String profileImage;
    int role;
    String degree;//学历
    String major;//专业
    String researchDirection;//研究方向
    String workPlace;//工作单位
    String desc;//个人简介

    public User(int id, String email, String password, String username, int age, String tel, String profileImage, int role, String degree, String major, String researchDirection, String workPlace, String desc) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
        this.tel = tel;
        this.profileImage = profileImage;
        this.role = role;
        this.degree = degree;
        this.major = major;
        this.researchDirection = researchDirection;
        this.workPlace = workPlace;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResearchDirection() {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
