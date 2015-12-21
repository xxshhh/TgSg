package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

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

    private Handler mHandler;

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
                    case Constant.PSWNOTSAME:
                        T.show(mContext, "两次密码不同，请检查密码");
                        break;
                    case Constant.EMAILFORMATERROR:
                        T.show(mContext, "邮箱格式不对");
                        break;
                    case Constant.PSWFORAMTERROR:
                        T.show(mContext, "密码格式不对，必须为6-16位，由大小写字母数字组成");
                        break;
                    case Constant.HTTP_ACCESS_ERROR:
                        T.show(mContext, "网络访问错误");
                        break;
                    case Constant.FAILED:
                        T.show(mContext, "注册失败");
                        mEdtEmail.setText("");
                        mEdtPassword.setText("");
                        mEdtPasswordAgain.setText("");
                        break;
                    case Constant.SUCCEED:
                        T.show(mContext, "恭喜你，注册成功。");
                        finish();
                        break;
                }
            }
        };

    }

    @Override
    protected void initListener() {
        /**
         * 注册
         */
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkEmail = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\\\.][A-Za-z]{2,3}([\\\\.][A-Za-z]{2})?$";
                String checkPsw = "^[a-zA-Z]\\w{5,17}$";//长度是6-18位，以字母开头，只能包含字符、数字和下划线
                boolean flagEmail;
                boolean flagPsw;
                String emailStr = mEdtEmail.getText().toString().trim();
                String pswStr = mEdtPassword.getText().toString().trim();
                String againPswStr = mEdtPasswordAgain.getText().toString().trim();
                if (emailStr.equals("") || pswStr.equals("") || againPswStr.equals("")) {
                    mHandler.sendEmptyMessage(Constant.INPUT_IS_NULL);
                } else {

                    if (!(pswStr.equals(againPswStr))) {
                        mHandler.sendEmptyMessage(Constant.PSWNOTSAME);
                    } else {
                        //验证邮箱
                        try {
                            Pattern regex = Pattern.compile(checkEmail);
                            Matcher matcher = regex.matcher(emailStr);
                            flagEmail = matcher.matches();

                        } catch (Exception e) {
                            e.printStackTrace();
                            flagEmail = false;
                        }

                        if (!flagEmail) {
                            mHandler.sendEmptyMessage(Constant.EMAILFORMATERROR);
                        } else {

                            //验证密码
                            try {
                                Pattern regex = Pattern.compile(checkPsw);
                                Matcher matcher = regex.matcher(pswStr);
                                flagPsw = matcher.matches();
                            } catch (Exception e) {
                                e.printStackTrace();
                                flagPsw = false;
                            }
                            if (flagPsw) {
                                requestServer(emailStr, pswStr);
                            } else {
                                mHandler.sendEmptyMessage(Constant.PSWFORAMTERROR);
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
        ProgressDialogUtil.show(mContext);
        RequestBody RegisterFormBody = new FormEncodingBuilder()
                .add("email", emailStr)
                .add("password", pswStr)
                .build();
        Request request = new Request.Builder().url(Constant.REGISTER_URL).post(RegisterFormBody).build();
//        OkHttpUtil.enqueue(request, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                mHandler.sendEmptyMessage(Constant.SUCCEED);
////                mHandler.sendEmptyMessage(Constant.HTTP_ACCESS_ERROR);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String result = response.body().string();
//                if (result.equals("success"))
//                    mHandler.sendEmptyMessage(Constant.SUCCEED);
//                else
//                    mHandler.sendEmptyMessage(Constant.FAILED);
//            }
//        });
    }
}
