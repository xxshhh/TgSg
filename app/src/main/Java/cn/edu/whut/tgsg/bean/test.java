package cn.edu.whut.tgsg.bean;

import java.util.List;

/**
 * Created by xwh on 2015/12/21.
 */
public class test {


    /**
     * msg : 登录成功
     * data : {"currentPage":1,"pageList":[],"totalPage":0}
     * contributor : {"activation":0,"age":23,"email":"1943309689@qq.com","id":3,"name":"yangzhi","password":"123456","role":{"id":3,"note":"作者","role":"作者"}}
     * isSuccess : true
     */

    private String msg;
    /**
     * currentPage : 1
     * pageList : []
     * totalPage : 0
     */

    private DataEntity data;
    /**
     * activation : 0
     * age : 23
     * email : 1943309689@qq.com
     * id : 3
     * name : yangzhi
     * password : 123456
     * role : {"id":3,"note":"作者","role":"作者"}
     */

    private UserEntity user;
    private boolean isSuccess;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public static class DataEntity {
        private int currentPage;
        private int totalPage;
        private List<?> pageList;

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setPageList(List<?> pageList) {
            this.pageList = pageList;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public List<?> getPageList() {
            return pageList;
        }
    }

    public static class UserEntity {
        private int activation;
        private int age;
        private String email;
        private int id;
        private String name;
        private String password;
        /**
         * id : 3
         * note : 作者
         * role : 作者
         */

        private RoleEntity role;

        public void setActivation(int activation) {
            this.activation = activation;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(RoleEntity role) {
            this.role = role;
        }

        public int getActivation() {
            return activation;
        }

        public int getAge() {
            return age;
        }

        public String getEmail() {
            return email;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public RoleEntity getRole() {
            return role;
        }

        public static class RoleEntity {
            private int id;
            private String note;
            private String role;

            public void setId(int id) {
                this.id = id;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public int getId() {
                return id;
            }

            public String getNote() {
                return note;
            }

            public String getRole() {
                return role;
            }
        }
    }
}
