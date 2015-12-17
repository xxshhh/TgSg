package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 忘记密码界面
 * <p/>
 * Created by ylj on 2015-12-13.
 */
public class ForgetPswActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edt_email)
    EditText mEdtEmail;
    @Bind(R.id.btn_send_email)
    Button mBtnSendEmail;

    private Handler mHandler;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected Context getContext() {
        return ForgetPswActivity.this;
    }

    @Override
    protected void initData() {
        // 设置toolbar
        mToolbar.setTitle("忘记密码");
        setSupportActionBar(mToolbar);
        /**
         * 处理消息
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ProgressDialogUtil.dismiss();
                switch (msg.what) {
                    case Constant.INPUT_IS_NULL:
                        T.show(mContext, "亲，请输入");
                        break;
                    case Constant.EMAILFORMATERROR:
                        T.show(mContext, "邮箱格式不对");
                        break;
                    case Constant.HTTP_ACCESS_ERROR:
                        T.show(mContext, "网络访问错误");
                        break;
                    case Constant.FAILED:
                        T.show(mContext, "请求失败");
                        mEdtEmail.setText("");
                        break;
                    case Constant.SUCCEED:
                        T.show(mContext, "请转到电脑端进行处理");
                        finish();
                        break;
                }
            }
        };
    }

    @Override
    protected void initListener() {
        /**
         * 发送邮件
         */
        mBtnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkEmail = "";
                boolean flag;
                String emailStr = mEdtEmail.getText().toString().trim();
                if (emailStr.equals("")) {
                    mHandler.sendEmptyMessage(Constant.INPUT_IS_NULL);
                } else {
                    //验证邮箱
                    try {
              /*  Pattern regex = Pattern.compile(checkEmail);
                Matcher matcher = regex.matcher(emailStr);
                flag = matcher.matches();*/
                        flag = emailStr.equals("123456");
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false;
                    }
                    if (!flag) {
                        mHandler.sendEmptyMessage(Constant.EMAILFORMATERROR);
                    } else {
                        requestServer(emailStr);
                    }
                }
            }
        });
    }

    /**
     * 发送邮件
     *
     * @param emailStr
     */
    private void requestServer(String emailStr) {
        //发送邮件
        mHandler.sendEmptyMessage(Constant.SUCCEED);
    }
}
