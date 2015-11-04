package cn.edu.whut.tgsg.util;

import android.content.Context;

import cn.edu.whut.tgsg.widget.CustomProgressDialog;

/**
 * Created by xwh on 2015/11/4.
 */
public class ProgressDialogUtil {
    private static CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = CustomProgressDialog.show(context, message, false, null);
        } else {
            mProgressDialog.show();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}

