package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 用户
 * <p/>
 * Created by ylj on 2015/12/14.
 */
public class User implements Serializable {

    /**
     * id：1
     * email：107603@163.com
     * password：123
     * name：张三
     * age：12
     * phone：182123392
     * profileImage：path
     * role: Role
     * degree：大学
     * major：计算机
     * researchDirection：机器学习
     * workPlace：武汉理工
     * desc：一名医生
     */

    int id;
    String email;
    String password;
    String name;
    int age;
    String phone;
    String photo;
    Role role;
    String education;//学历
    String professional;//专业
    String research;//研究方向
    String work;//工作单位
    String personal;//个人简介

    public User() {

    }

    public User(int id, String email, String password, String name, int age, String phone, String photo, Role role, String education, String professional, String research, String work, String personal) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.photo = photo;
        this.role = role;
        this.education = education;
        this.professional = professional;
        this.research = research;
        this.work = work;
        this.personal = personal;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }
}
