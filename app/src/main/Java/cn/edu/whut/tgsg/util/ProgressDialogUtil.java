package cn.edu.whut.tgsg.util;

import android.content.Context;

import cn.edu.whut.tgsg.widget.MyProgressDialog;

/**
 * 进度条对话框工具类
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class ProgressDialogUtil {
    private static MyProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     *
     * @param context
     */
    public static void show(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = MyProgressDialog.show(context, false, null);
        } else {
            mProgressDialog.show();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismiss() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}

