package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected String getTagName() {
        return "ForgetPswActivity";
    }

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
    }

    @Override
    protected void initListener() {
        /**
         * 发送邮件
         */
        mBtnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkEmail = "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
                boolean flagEmail;
                String emailStr = mEdtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(emailStr)) {
                    T.show(mContext, "请输入");
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
                        T.show(mContext, "邮箱格式不对");
                    } else {
                        // 请求服务器
                        requestServer(emailStr);
                    }
                }
            }
        });
    }

    /**
     * 请求服务器，发送邮件
     *
     * @param emailStr
     */
    private void requestServer(String emailStr) {

        OkHttpUtils
                .post()
                .url(Constant.URL + "forgetPass")
                .addParams("email", emailStr)
                .addParams("source", "android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(getTagName(), "onError:" + e.getMessage());
                        T.show(mContext, "网络访问错误");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(getTagName(), "onResponse:" + response);
                        try {
                            JSONObject serverInfo = new JSONObject(response);
                            boolean isSuccess = serverInfo.getBoolean("isSuccess");
                            String msg = serverInfo.getString("msg");
                            T.show(mContext, msg);
                            if (isSuccess) {
                                T.show(mContext, msg);
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
