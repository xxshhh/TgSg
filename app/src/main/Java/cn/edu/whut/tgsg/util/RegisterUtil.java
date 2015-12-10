package cn.edu.whut.tgsg.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Field;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.common.Constant;

/**
 * 显示注册对话框的类
 * <p/>
 * Created by ylj on 2015-11-26.
 */
public class RegisterUtil {

    public static EditText sEdtUsername;
    public static EditText sEdtPassword;
    //昵称
    public static EditText sEdtProfileName;

    public static String sUsernameStr;
    public static String sPasswordStr;
    public static String sProfileNameStr;

    public static Handler mHandler;
    public static DialogInterface okdialog;

    /**
     * 显示注册对话框
     * @param layoutSource:自定义对话框布局
     * @param context：dialog所在的context
     */
    public static void customRegisterDialog(int layoutSource, final Context context) {

        //LayoutInflater:加载找layout文件夹下的xml文件，并将布局实例化
        LayoutInflater inflater = LayoutInflater.from(context);
        final View registerForm = inflater.inflate(layoutSource, null);//把对话框的页面布局赋值给registerForm

        //将自定义View显示在对话框中
        new AlertDialog.Builder(context)
                //设置图标
                .setIcon(R.mipmap.ic_launcher)
                        //设置标题
                .setTitle("注册")
                        //设置对话框显示 的View对象
                .setView(registerForm)
                        //设置一个确定按钮
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        okdialog = dialog;
                        //执行注册操作

                        //对话框中的控件
                        sEdtUsername = (EditText) registerForm.findViewById(R.id.edt_username);
                        sEdtPassword = (EditText) registerForm.findViewById(R.id.edt_password);

                        sEdtProfileName = (EditText) registerForm.findViewById(R.id.edt_profile_name);

                        //从对话框中获得用户输入的值,将值转化为字符串
                        sUsernameStr = sEdtUsername.getText().toString().trim();
                        sPasswordStr = sEdtPassword.getText().toString().trim();
                        sProfileNameStr = sEdtProfileName.getText().toString().trim();

                        //判断输入框有没有为空的
                        if (sUsernameStr.equals("")
                                || sPasswordStr.equals("")
                                || sProfileNameStr.equals("")) {
                            mHandler.sendEmptyMessage(Constant.INPUT_IS_NULL);

                            return;

                        }

                        //若输入框全部都填好
                        ProgressDialogUtil.show(context);//在context中显示进度对话框
                        /**
                         * 三步：OkHttpClient——Request（RequestBody）——Response
                         */

                        //http——post提交
                        RequestBody registerFormBody = new FormEncodingBuilder()
                                .add("username", sUsernameStr)
                                .add("password", sPasswordStr)
                                .add("profileName", sProfileNameStr)
                                .build();
                        Request request = new Request.Builder().url(Constant.REGISTER_URL)
                                .post(registerFormBody)
                                .build();//实例化请求

                        OkHttpUtil.enqueue(request, new Callback() {//执行请求
                            @Override
                            public void onFailure(Request request, IOException e) {
                                mHandler.sendEmptyMessage(Constant.NET_ACCESS_ERROR);
//                                mHandler.sendEmptyMessage(LOGIN_SUCCEED);
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {

                                String result = response.body().string();
                                if (result.equals("success"))
                                    mHandler.sendEmptyMessage(Constant.SUCCEED);
                                else
                                    mHandler.sendEmptyMessage(Constant.FAILED);
                            }
                        });

                        /*if (sUsernameStr.equals("1") && sPasswordStr.equals("1")) {
                            Toast.makeText(context, "成功！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "失败！！！", Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                        //设置一个取消按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消注册，不做任何事情
                        Toast.makeText(context, "取消！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                        //创建并显示对话框
                .create()
                .show();

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ProgressDialogUtil.dismiss();
                switch (msg.what) {
                    case Constant.INPUT_IS_NULL:
                        Toast.makeText(context, "用户名或密码或昵称不能为空", Toast.LENGTH_SHORT).show();
                        sEdtUsername.setText("");
                        sEdtPassword.setText("");
                        break;
                    case Constant.NET_ACCESS_ERROR:
                        Toast.makeText(context, "网络访问错误！！！", Toast.LENGTH_SHORT).show();
                        sEdtUsername.setText("");
                        sEdtPassword.setText("");
                        break;
                    case Constant.FAILED:
                        Toast.makeText(context, "注册失败！！！", Toast.LENGTH_SHORT).show();
                        sEdtUsername.setText("");
                        sEdtPassword.setText("");
                        break;
                    case Constant.SUCCEED:
                        Toast.makeText(context, "恭喜你，注册成功。", Toast.LENGTH_SHORT).show();
                        break;
                }
                try {
                    Field field = okdialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(okdialog, false);
                    okdialog.dismiss();

                } catch (Exception e) {

                }
            }
        };

    }
}
