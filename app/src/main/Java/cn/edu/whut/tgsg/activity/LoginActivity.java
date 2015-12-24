package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 登录界面
 * <p/>
 * Created by xwh on 2015/11/3.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_email)
    EditText mEdtEmail;
    @Bind(R.id.edt_password)
    EditText mEdtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_forget)
    TextView mTvForget;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected String getTagName() {
        return "LoginActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Context getContext() {
        return LoginActivity.this;
    }

    @Override
    protected void initData() {
        // 取SharedPreferences数据
        String user_email = null;
        String user_password = null;
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("user_email", "");
        user_password = sharedPreferences.getString("user_password", "");
        // 自动登录
        if (!user_email.equals("") && !user_password.equals("")) {
            Log.e(getTagName(), user_email + "," + user_password);
            OkHttpUtils
                    .post()
                    .url(Constant.URL + "login")
                    .addParams("email", user_email)
                    .addParams("password", user_password)
                    .addParams("remember", "true")
                    .addParams("source", "android")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.e(getTagName(), "onError:" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.e(getTagName(), "onResponse:" + response);
                            try {
                                JSONObject serverInfo = new JSONObject(response);
                                boolean isSuccess = serverInfo.getBoolean("isSuccess");
                                if (isSuccess) {
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    MyApplication.GLOBAL_USER = new Gson().fromJson(serverInfo.getString("user"), User.class);
                                    Log.e(getTagName(), "User:" + new Gson().toJson(MyApplication.GLOBAL_USER));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 存数据到SharedPreferences
                                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("user_email", "");
                                    editor.putString("user_password", "");
                                    editor.commit();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    @Override
    protected void initListener() {
        /**
         * 登录
         */
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = mEdtEmail.getText().toString().trim();
                String passwordStr = mEdtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)) {
                    T.show(mContext, "邮箱或密码不能为空!!!!!!!");
                    return;
                }
                ProgressDialogUtil.show(mContext);
                OkHttpUtils
                        .post()
                        .url(Constant.URL + "login")
                        .addParams("email", emailStr)
                        .addParams("password", passwordStr)
                        .addParams("remember", "true")
                        .addParams("source", "android")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                ProgressDialogUtil.dismiss();
                                Log.e(getTagName(), "onError:" + e.getMessage());
                                T.show(mContext, "网络访问错误");
                            }

                            @Override
                            public void onResponse(String response) {
                                ProgressDialogUtil.dismiss();
                                Log.e(getTagName(), "onResponse:" + response);
                                try {
                                    JSONObject serverInfo = new JSONObject(response);
                                    boolean isSuccess = serverInfo.getBoolean("isSuccess");
                                    String msg = serverInfo.getString("msg");
                                    T.show(mContext, msg);
                                    if (isSuccess) {
                                        MyApplication.GLOBAL_USER = new Gson().fromJson(serverInfo.getString("user"), User.class);
                                        Log.e(getTagName(), "User:" + new Gson().toJson(MyApplication.GLOBAL_USER));
                                        // 存数据到SharedPreferences
                                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("user_email", MyApplication.GLOBAL_USER.getEmail());
                                        editor.putString("user_password", mEdtPassword.getText().toString().trim());
                                        editor.commit();
                                        // 跳转主界面
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        mEdtEmail.setText("");
                                        mEdtPassword.setText("");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                Intent intent = new Intent(mContext, ForgetPswActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 注册
         */
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
