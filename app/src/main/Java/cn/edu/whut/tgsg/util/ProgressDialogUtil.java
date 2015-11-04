package cn.edu.whut.tgsg.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by xwh on 2015/11/4.
 */
public class ProgressDialogUtil {
    private static ProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(context, "提示", message);
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

