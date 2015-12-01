package cn.edu.whut.tgsg.util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * OkHttp框架使用工具类
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class OkHttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

}
