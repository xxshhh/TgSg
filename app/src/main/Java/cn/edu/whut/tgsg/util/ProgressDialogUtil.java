package cn.edu.whut.tgsg.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * 进度条对话框工具类
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class ProgressDialogUtil {
    private static MaterialDialog sProgress;

    /**
     * 显示ProgressDialog
     *
     * @param context
     */
    public static void show(final Context context) {
        if (sProgress == null) {
            sProgress = new MaterialDialog.Builder(context)
                    .content("loading...")
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
        } else {
            sProgress.show();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismiss() {
        if (sProgress != null) {
            sProgress.dismiss();
            sProgress = null;
        }
    }
}

