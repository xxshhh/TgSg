package cn.edu.whut.tgsg.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import cn.edu.whut.tgsg.R;

/**
 * Created by xwh on 2015/11/4.
 */
public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        AwesomeTextView awesomeTextView = (AwesomeTextView) findViewById(R.id.icon_spinner);
        awesomeTextView.startRotate(true, AwesomeTextView.AnimationSpeed.MEDIUM);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static CustomProgressDialog show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        CustomProgressDialog dialog = new CustomProgressDialog(context, R.style.Custom_Progress_Dialog);
        dialog.setTitle("");
        dialog.setContentView(R.layout.custom_progress_dialog);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.tv_message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.tv_message);
            txt.setText(message);
        }
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.6f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }
}
