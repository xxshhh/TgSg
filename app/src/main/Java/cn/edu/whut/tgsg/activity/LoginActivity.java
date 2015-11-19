package cn.edu.whut.tgsg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.util.OkHttpUtil;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;

/**
 * Created by xwh on 2015/11/3.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_username)
    EditText username;
    @Bind(R.id.edt_password)
    EditText password;

    private Handler mHandler;

    public static final String LOGIN_URL = "http://192.168.191.1:8080/android/login";
    public static final int INPUT_IS_NULL = -2;
    public static final int NET_ACCESS_ERROR = -1;
    public static final int LOGIN_FAILED = 0;
    public static final int LOGIN_SUCCEED = 1;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 失去焦点


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ProgressDialogUtil.dismissProgressDialog();
                switch (msg.what) {
                    case INPUT_IS_NULL:
                        Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        break;
                    case NET_ACCESS_ERROR:
                        Toast.makeText(LoginActivity.this, "网络访问错误！！！", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        break;
                    case LOGIN_FAILED:
                        Toast.makeText(LoginActivity.this, "登录失败！！！", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        break;
                    case LOGIN_SUCCEED:
                        Toast.makeText(LoginActivity.this, "恭喜你，登录成功。", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username.getText().toString());
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
    }

    @OnClick(R.id.btn_login)
    void login() {
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        if (usernameStr.equals("") || passwordStr.equals("")) {
            mHandler.sendEmptyMessage(INPUT_IS_NULL);
            return;
        }
        ProgressDialogUtil.showProgressDialog(LoginActivity.this);
        RequestBody loginFormBody = new FormEncodingBuilder()
                .add("username", usernameStr)
                .add("password", passwordStr)
                .build();
        Request request = new Request.Builder().url(LOGIN_URL).post(loginFormBody).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //mHandler.sendEmptyMessage(NET_ACCESS_ERROR);
                mHandler.sendEmptyMessage(LOGIN_SUCCEED);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("success"))
                    mHandler.sendEmptyMessage(LOGIN_SUCCEED);
                else
                    mHandler.sendEmptyMessage(LOGIN_FAILED);
            }
        });
    }

    @OnClick(R.id.tv_forget)
    void forget() {
        Toast.makeText(LoginActivity.this, "抱歉！！！", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_register)
    void register() {
        Toast.makeText(LoginActivity.this, "暂时不提供注册", Toast.LENGTH_SHORT).show();
    }
}
