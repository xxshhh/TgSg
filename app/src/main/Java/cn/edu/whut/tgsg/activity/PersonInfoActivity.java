package cn.edu.whut.tgsg.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.FileHandleUtil;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息界面
 * <p/>
 * Created by xwh on 2015-12-22 11:32:12.
 */
public class PersonInfoActivity extends BaseActivity {

    @Bind(R.id.profile_image)
    CircleImageView mProfileImage;
    @Bind(R.id.tv_username)
    TextView mTvUsername;
    @Bind(R.id.layout_change_psw)
    LinearLayout mLayoutChangePsw;
    @Bind(R.id.layout_change_email)
    LinearLayout mLayoutChangeEmail;
    @Bind(R.id.layout_change_info)
    LinearLayout mLayoutChangeInfo;

    private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

    @Override
    protected String getTagName() {
        return "PersonInfoActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personinfo;
    }

    @Override
    protected Context getContext() {
        return PersonInfoActivity.this;
    }

    @Override
    protected void initData() {
        // 显示头像
        displayProfileImage();
    }

    @Override
    protected void initListener() {
        /**
         * 上传头像
         */
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "上传头像");
                // 选择图片
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        /**
         * 修改密码
         */
        mLayoutChangePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title("修改密码")
                        .customView(R.layout.dialog_custom_change_psw, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .cancelable(false)
                        .show();
                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String checkPsw = "^[a-zA-Z0-9]\\w{5,15}$";
                        boolean flagPsw;
                        String oldPswStr = ((EditText) dialog.findViewById(R.id.edt_old_password)).getText().toString().trim();
                        String newPswStr = ((EditText) dialog.findViewById(R.id.edt_new_password)).getText().toString().trim();
                        String newPswAgainStr = ((EditText) dialog.findViewById(R.id.edt_new_password_again)).getText().toString().trim();
                        if (TextUtils.isEmpty(oldPswStr) || TextUtils.isEmpty(newPswStr) || TextUtils.isEmpty(newPswAgainStr)) {
                            T.show(mContext, "输入不能为空");
                        } else {
                            if (!(newPswStr.equals(newPswAgainStr))) {
                                T.show(mContext, "两次输入密码不同，请检查");
                            } else {
                                // 验证密码
                                try {
                                    Pattern regex = Pattern.compile(checkPsw);
                                    Matcher matcher = regex.matcher(newPswStr);
                                    flagPsw = matcher.matches();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    flagPsw = false;
                                }
                                if (flagPsw) {
                                    // 请求服务器
                                    requestServer(oldPswStr, newPswStr);
                                    dialog.dismiss();
                                } else {
                                    T.show(mContext, "密码格式不对，必须为6-16位，由大小写字母数字组成");
                                }
                            }
                        }
                    }
                });
            }
        });

        /**
         * 更换邮箱
         */
        mLayoutChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title("更换邮箱")
                        .customView(R.layout.dialog_custom_change_email, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .cancelable(false)
                        .show();
                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String checkEmail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                        boolean flagEmail;
                        String oldEmailStr = ((EditText) dialog.findViewById(R.id.edt_old_email)).getText().toString().trim();
                        String newEmailStr = ((EditText) dialog.findViewById(R.id.edt_new_email)).getText().toString().trim();
                        String newEmailAgainStr = ((EditText) dialog.findViewById(R.id.edt_new_email_again)).getText().toString().trim();
                        if (TextUtils.isEmpty(oldEmailStr) || TextUtils.isEmpty(newEmailStr) || TextUtils.isEmpty(newEmailAgainStr)) {
                            T.show(mContext, "输入不能为空");
                        } else {
                            if (!(newEmailStr.equals(newEmailAgainStr))) {
                                T.show(mContext, "两次输入邮箱不同，请检查");
                            } else {
                                // 验证密码
                                try {
                                    Pattern regex = Pattern.compile(checkEmail);
                                    Matcher matcher = regex.matcher(newEmailStr);
                                    flagEmail = matcher.matches();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    flagEmail = false;
                                }
                                if (flagEmail) {
                                    // 请求服务器
                                    requestServer(newEmailStr);
                                    dialog.dismiss();
                                } else {
                                    T.show(mContext, "邮箱格式不对,请检查");
                                }
                            }
                        }
                    }
                });
            }
        });

        /**
         * 修改个人信息
         */
        mLayoutChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChgPersonInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示头像
     */
    private void displayProfileImage() {
        OkHttpUtils
                .get()
                .url(Constant.STATIC_URL + "img/" + MyApplication.GLOBAL_USER.getPhoto())
                .addParams("source", "android")
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(getTagName(), "加载头像失败！！！使用默认头像。");
                    }

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e(getTagName(), "加载头像成功！！！");
                        mProfileImage.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 向服务器发出修改密码请求
     *
     * @param oldPswStr
     * @param newPswStr
     */
    private void requestServer(String oldPswStr, String newPswStr) {
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "updatePassword")
                .addParams("oldPass", oldPswStr)
                .addParams("newPass", newPswStr)
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

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 向服务器发出修改邮箱请求
     *
     * @param newEmailStr
     */
    private void requestServer(String newEmailStr) {
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "updateEmail")
                .addParams("nEmail", newEmailStr)
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 上传头像到服务器
     *
     * @param file
     * @param bitmap
     */
    private void requestServer(File file, final Bitmap bitmap) {
        Log.e(getTagName(), "file," + "bitmap");
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "updatePhoto")
                .addParams("source", "android")
                .addFile("fileField", "profileImage.png", file)
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
                            if (isSuccess) {
                                T.show(mContext, "头像上传成功");
                                MyApplication.GLOBAL_USER.setPhoto(serverInfo.getString("photo"));
                                mProfileImage.setImageBitmap(bitmap);
                            } else {
                                T.show(mContext, "头像上传失败");
                            }
                            // 返回图片更新信息
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("isUpdatePhoto", isSuccess);
                            PersonInfoActivity.this.setResult(Activity.RESULT_OK, returnIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                ContentResolver resolver = getContentResolver();
                File file = null;
                Bitmap bitmap = null;
                try {
                    Uri uri = data.getData();
                    file = new File(FileHandleUtil.getImageAbsolutePath(mContext, uri));
                    bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null && bitmap != null) {
                    // 上传头像到服务器
                    requestServer(file, bitmap);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 更新昵称
        if (MyApplication.GLOBAL_USER.getName() != null)
            mTvUsername.setText(MyApplication.GLOBAL_USER.getName());
    }
}
