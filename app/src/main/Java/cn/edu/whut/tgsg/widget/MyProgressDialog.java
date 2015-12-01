package cn.edu.whut.tgsg.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import cn.edu.whut.tgsg.R;

/**
 * 自定义的进度条对话框
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class MyProgressDialog extends Dialog {
    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static MyProgressDialog show(Context context, boolean cancelable, OnCancelListener cancelListener) {
        MyProgressDialog dialog = new MyProgressDialog(context, R.style.My_Progress_Dialog);
        dialog.setTitle("");
        dialog.setContentView(R.layout.my_progress_dialog);
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.3f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }
}