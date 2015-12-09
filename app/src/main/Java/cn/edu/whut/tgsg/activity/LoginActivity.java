package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.OkHttpUtil;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 登录界面
 * <p/>
 * Created by xwh on 2015/11/3.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_username)
    EditText mEdtUsername;
    @Bind(R.id.edt_password)
    EditText mEdtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_forget)
    TextView mTvForget;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    private Handler mHandler;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Context getContext() {
        return LoginActivity.this;
    }

    @Override
    protected void initListener() {
        /**
         * 登录
         */
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = mEdtUsername.getText().toString();
                String passwordStr = mEdtPassword.getText().toString();
                if (usernameStr.equals("") || passwordStr.equals("")) {
                    T.show(mContext, "用户名或密码不能为空");
                    return;
                }
                ProgressDialogUtil.show(mContext);
                RequestBody loginFormBody = new FormEncodingBuilder()
                        .add("username", usernameStr)
                        .add("password", passwordStr)
                        .build();
                Request request = new Request.Builder().url(Constant.LOGIN_URL).post(loginFormBody).build();
                OkHttpUtil.enqueue(request, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendEmptyMessage(Constant.LOGIN_SUCCEED);
                        //mHandler.sendEmptyMessage(Constant.HTTP_ACCESS_ERROR);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String result = response.body().string();
                        if (result.equals("success"))
                            mHandler.sendEmptyMessage(Constant.LOGIN_SUCCEED);
                        else
                            mHandler.sendEmptyMessage(Constant.LOGIN_FAILED);
                    }
                });
            }
        });

        /**
         * 忘记密码
         */
        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "抱歉");
            }
        });

        /**
         * 注册
         */
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "暂时不提供注册");
            }
        });
    }

    @Override
    protected void initData() {
        /**
         * 处理消息
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ProgressDialogUtil.dismiss();
                switch (msg.what) {
                    case Constant.HTTP_ACCESS_ERROR:
                        T.show(mContext, "网络访问错误");
                        break;
                    case Constant.LOGIN_FAILED:
                        T.show(mContext, "用户名或密码错误");
                        mEdtUsername.setText("");
                        mEdtPassword.setText("");
                        break;
                    case Constant.LOGIN_SUCCEED:
                        T.show(mContext, "恭喜你，登录成功。");
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("username", mEdtUsername.getText().toString());
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
