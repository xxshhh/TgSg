package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 注册界面
 * <p/>
 * Created by ylj on 2015-12-11.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edt_email)
    EditText mEdtEmail;
    @Bind(R.id.edt_password)
    EditText mEdtPassword;
    @Bind(R.id.edt_password_again)
    EditText mEdtPasswordAgain;
    @Bind(R.id.btn_register)
    Button mBtnRegister;

    @Override
    protected String getTagName() {
        return "RegisterActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected Context getContext() {
        return RegisterActivity.this;
    }

    @Override
    protected void initData() {
        // 设置toolbar
        mToolbar.setTitle("注册");
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initListener() {
        /**
         * 注册
         */
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkEmail = "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
                String checkPsw = "^[a-zA-Z0-9]\\w{5,15}$";
                boolean flagEmail;
                boolean flagPsw;
                String emailStr = mEdtEmail.getText().toString().trim();
                String pswStr = mEdtPassword.getText().toString().trim();
                String againPswStr = mEdtPasswordAgain.getText().toString().trim();
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(pswStr) || TextUtils.isEmpty(againPswStr)) {
                    T.show(mContext, "输入不能为空");
                } else {
                    if (!(pswStr.equals(againPswStr))) {
                        T.show(mContext, "两次输入密码不同，请检查");
                    } else {
                        // 验证邮箱
                        try {
                            Pattern regex = Pattern.compile(checkEmail);
                            Matcher matcher = regex.matcher(emailStr);
                            flagEmail = matcher.matches();
                        } catch (Exception e) {
                            e.printStackTrace();
                            flagEmail = false;
                        }
                        if (!flagEmail) {
                            T.show(mContext, "邮箱格式不对,请检查");
                        } else {
                            // 验证密码
                            try {
                                Pattern regex = Pattern.compile(checkPsw);
                                Matcher matcher = regex.matcher(pswStr);
                                flagPsw = matcher.matches();
                            } catch (Exception e) {
                                e.printStackTrace();
                                flagPsw = false;
                            }
                            if (flagPsw) {
                                // 请求服务器
                                requestServer(emailStr, pswStr);
                            } else {
                                T.show(mContext, "密码格式不对，必须为6-16位，由大小写字母数字组成");
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 邮箱密码输入格式正确，向服务器发出注册请求
     *
     * @param emailStr
     * @param pswStr
     */
    private void requestServer(String emailStr, String pswStr) {
        Log.e(getTagName(), emailStr + "," + pswStr);
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "register")
                .addParams("email", emailStr)
                .addParams("password", pswStr)
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
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                                // 存数据到SharedPreferences
                                SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("user_email", "");
                                editor.putString("user_password", "");
                                editor.commit();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
