package cn.edu.whut.tgsg;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.edu.whut.tgsg.bean.User;

/**
 * 自定义application
 * <p/>
 * Created by xwh on 2015/12/21.
 */
public class MyApplication extends Application {

    public static User GLOBAL_USER = new User();

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.getInstance().getOkHttpClient().setConnectTimeout(10000, TimeUnit.MILLISECONDS);
    }
}
