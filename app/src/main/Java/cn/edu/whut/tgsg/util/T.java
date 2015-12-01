package cn.edu.whut.tgsg.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * <p/>
 * Created by xwh on 2015/11/30.
 */
public class T {

    /**
     * 显示Toast(短时间)
     *
     * @param context
     * @param message
     */
    public static void show(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
