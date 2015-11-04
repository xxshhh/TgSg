package cn.edu.whut.tgsg.activity;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by xwh on 2015/11/2.
 */
public class MyApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        // 加载图标字体文件
        TypefaceProvider.registerDefaultIconSets();
    }
}
