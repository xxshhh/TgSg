package cn.edu.whut.tgsg.bean;

import java.io.Serializable;

/**
 * 角色
 * <p/>
 * Created by ylj on 2015/12/21.
 */
public class Role implements Serializable {

    /**
     * 角色编号 :    1    2    3   4
     * 角色描述 : 管理员 编辑 作者 专家
     * 角色描述 : 备注
     */
    int id;
    String role;
    String note;

    public Role() {
    }

    public Role(int id, String role, String note) {
        this.id = id;
        this.role = role;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
