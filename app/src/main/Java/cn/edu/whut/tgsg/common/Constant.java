package cn.edu.whut.tgsg.common;

import cn.edu.whut.tgsg.bean.User;

/**
 * 常量
 * <p/>
 * Created by xwh on 2015/11/30.
 */
public class Constant {
    public static final String LOGIN_URL = "http://192.168.191.1:8080/android/login";
    public static final String REGISTER_URL = "http://192.168.191.1:8080/android/register";
    public static final String SAVE_PERSONINFO_URL = "http://192.168.191.1:8080/android/getPersonInfo";
    public static final String GET_PERSONINFO_URL = "http://192.168.191.1:8080/android/getPersonInfo";

    public static User GLOBAL_USER = new User();

    public static final int HTTP_ACCESS_OK = 1;
    public static final int HTTP_ACCESS_ERROR = 2;

    public static final int SUCCEED = 3;
    public static final int FAILED = 4;

    public static final int INPUT_IS_NULL = 5;
    public static final int NET_ACCESS_ERROR = 6;
    public static final int PSWNOTSAME = 7;

    public static final int EMAILFORMATERROR = 8;
    public static final int PSWFORAMTERROR = 9;
    public static final int GETJSON_SUCCEED = 10;
}
