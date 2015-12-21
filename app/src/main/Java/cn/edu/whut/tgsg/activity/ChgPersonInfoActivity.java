package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.util.T;

/**
 * 修改个人信息
 * <p/>
 * Created by ylj on 2015-12-20.
 */
public class ChgPersonInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_email)
    TextView mTvEmail;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.tv_tel)
    TextView mTvTel;
    @Bind(R.id.tv_degree)
    TextView mTvDegree;
    @Bind(R.id.tv_major)
    TextView mTvMajor;
    @Bind(R.id.tv_research_direction)
    TextView mTvResearchDirection;
    @Bind(R.id.tv_work_place)
    TextView mTvWorkPlace;
    @Bind(R.id.tv_desc)
    TextView mTvDesc;
    @Bind(R.id.tv_username)
    TextView mTvUsername;
    @Bind(R.id.layout_profile_image)
    RelativeLayout mLayoutProfileImage;
    @Bind(R.id.layout_email)
    RelativeLayout mLayoutEmail;
    @Bind(R.id.layout_username)
    RelativeLayout mLayoutUsername;
    @Bind(R.id.layout_age)
    RelativeLayout mLayoutAge;
    @Bind(R.id.layout_tel)
    RelativeLayout mLayoutTel;
    @Bind(R.id.layout_degree)
    RelativeLayout mLayoutDegree;
    @Bind(R.id.layout_major)
    RelativeLayout mLayoutMajor;
    @Bind(R.id.layout_research_direction)
    RelativeLayout mLayoutResearchDirection;
    @Bind(R.id.layout_work_place)
    RelativeLayout mLayoutWorkPlace;
    @Bind(R.id.layout_desc)
    RelativeLayout mLayoutDesc;


    private String jsonString = "{\"authorId\":1,\"username\":\"小杨\"," +
            "\"email\":\"110@163.com\",\"tel\":\"5511952\"," +
            "\"edubkg\":\"大学\",\"desc\":\"我有一头小毛驴，从来也不骑\"}";
    private User user = new User();//初始化

    private Handler mHandler;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chgpersoninfo;
    }

    @Override
    protected Context getContext() {
        return ChgPersonInfoActivity.this;
    }

    @Override
    protected void initData() {
        //设置toolbar:titlt+<-
        mToolbar.setTitle("修改资料");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //初始化个人信息界面
        initPersonInfo();

        /**
         * 处理消息
         */
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String input = msg.getData().getString("input");
                switch (msg.what) {
                    case R.id.layout_email:
                        mTvEmail.setText(input);
                        break;
                    case R.id.layout_username:
                        mTvUsername.setText(input);
                        break;
                    case R.id.layout_age:
                        mTvAge.setText(input);
                        break;
                    case R.id.layout_tel:
                        mTvTel.setText(input);
                        break;
                    case R.id.layout_degree:
                        mTvDegree.setText(input);
                        break;
                    case R.id.layout_major:
                        mTvMajor.setText(input);
                        break;
                    case R.id.layout_research_direction:
                        mTvResearchDirection.setText(input);
                        break;
                    case R.id.layout_work_place:
                        mTvWorkPlace.setText(input);
                        break;
                    case R.id.layout_desc:
                        mTvDesc.setText(input);
                        break;
                }
            }
        };

    }


    /**
     * 初始化个人信息界面
     */
    private void initPersonInfo() {//8个字段
        Gson gson = new Gson();
        user = gson.fromJson(jsonString, User.class);
        mTvEmail.setText(user.getEmail());
        mTvUsername.setText(user.getName());
        mTvAge.setText(String.valueOf(user.getAge()));
        mTvTel.setText(user.getPhone());
      /*  mTvDegree.setText(user.getDegree().toString());
        mTvMajor.setText(user.getMajor().toString());
        mTvResearchDirection.setText(user.getResearchDirection().toString());
        mTvWorkPlace.setText(user.getWorkPlace().toString());
        mTvDesc.setText(user.getDesc().toString());*/

        if (mTvEmail.getText().equals("") || mTvEmail.getText().equals(null)) {
            mTvEmail.setText("未设置");
        }
        if (mTvUsername.getText().equals("") || mTvUsername.getText().equals(null)) {
            mTvUsername.setText("未设置");
        }
        if (mTvAge.getText().equals("") || mTvAge.getText().equals(null)) {
            mTvAge.setText("未设置");
        }
        if (mTvTel.getText().equals("") || mTvTel.getText().equals(null)) {
            mTvTel.setText("未设置");
        }
        if (mTvDegree.getText().equals("") || mTvDegree.getText().equals(null)) {
            mTvDegree.setText("未设置");
        }
        if (mTvMajor.getText().equals("") || mTvMajor.getText().equals(null)) {
            mTvMajor.setText("未设置");
        }
        if (mTvResearchDirection.getText().equals("") || mTvResearchDirection.getText().equals(null)) {
            mTvResearchDirection.setText("未设置");
        }
        if (mTvWorkPlace.getText().equals("") || mTvWorkPlace.getText().equals(null)) {
            mTvWorkPlace.setText("未设置");
        }
        if (mTvDesc.getText().equals("") || mTvDesc.getText().equals(null)) {
            mTvDesc.setText("未设置");
        }

    }

    @Override
    protected void initListener() {
        mLayoutEmail.setOnClickListener(this);
        mLayoutUsername.setOnClickListener(this);
        mLayoutAge.setOnClickListener(this);
        mLayoutTel.setOnClickListener(this);
        mLayoutDegree.setOnClickListener(this);
        mLayoutMajor.setOnClickListener(this);
        mLayoutResearchDirection.setOnClickListener(this);
        mLayoutWorkPlace.setOnClickListener(this);
        mLayoutDesc.setOnClickListener(this);

    }

    @Override
    protected String getTagName() {
        return "ChgPersonInfoActivity";
    }

    @Override
    public void onClick(View v) {
        showMyDialog(v.getId());//以该自定义布局显示对话框
    }

    /**
     * 显示对话框
     */
    private void showMyDialog(final int viewId) {
        String title = getTile(viewId);
        switch (viewId) {//10个字段
          /*  case R.id.edt_degree://学历
                break;
            case R.id.tv_major://专业
                break;
            case R.id.tv_research_direction://研究方向
                break;
            case R.id.tv_work_place://工作单位
                break;*/

            default:
                final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title(title)
                        .input(null, null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                            }
                        })
                        .positiveText(R.string.agree)
                        .show();
                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = dialog.getInputEditText().getText().toString().trim();
                        if (viewId == R.id.layout_email) {
                            if (!str.matches("^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
                                T.show(mContext, "邮箱格式不对");
                                return;
                            }
                        }
                        Message msg = new Message();
                        msg.what = viewId;
                        Bundle bundle = new Bundle();
                        bundle.putString("input", str);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                        dialog.dismiss();
                    }
                });
                break;
        }

    }

    /**
     * 得到dialog的标题
     *
     * @param viewId
     * @return
     */
    private String getTile(int viewId) {
        String title = "";
        String tagName = "";
        switch (viewId) {//10个字段
            case R.id.layout_email:
                tagName = "email";
                title = "邮箱";
                break;
            case R.id.layout_username:
                tagName = "username";
                title = "姓名";
                break;
            case R.id.layout_age:
                tagName = "age";
                title = "年龄";
                break;
            case R.id.layout_tel:
                tagName = "tel";
                title = "电话";
                break;
            case R.id.layout_degree:
                tagName = "degree";
                title = "学历";
                break;
            case R.id.layout_major:
                tagName = "major";
                title = "专业";
                break;
            case R.id.layout_research_direction:
                tagName = "researchDirection";
                title = "研究方向";
                break;
            case R.id.layout_work_place:
                tagName = "workPlace";
                title = "工作单位";
                break;
            case R.id.layout_desc:
                tagName = "desc";
                title = "个人简介";
                break;
            /*case R.id.edt_psw:
                tagName = "password";
                title = "密码";
                break;*/
        }
        return title;

    }


}



