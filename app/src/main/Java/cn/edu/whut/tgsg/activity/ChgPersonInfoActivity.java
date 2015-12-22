package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 修改个人信息
 * <p/>
 * Created by ylj on 2015-12-20.
 */
public class ChgPersonInfoActivity extends BaseActivity implements View.OnClickListener {

    // 9个字段
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

    // 9个字段
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

    @Override
    protected String getTagName() {
        return "ChgPersonInfoActivity";
    }

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
        // 设置toolbar:titlt+<-
        mToolbar.setTitle("修改资料");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 初始化个人信息界面
        initPersonInfo();
    }

    /**
     * 初始化个人信息界面
     */
    private void initPersonInfo() {//9个字段
        User user = MyApplication.GLOBAL_USER;

        mTvEmail.setText(user.getEmail());
        mTvUsername.setText(user.getName());
        mTvAge.setText(String.valueOf(user.getAge()));

        mTvTel.setText(user.getPhone());
        mTvDegree.setText(user.getEducation());
        mTvMajor.setText(user.getProfessional());

        mTvResearchDirection.setText(user.getResearch());
        mTvWorkPlace.setText(user.getWork());
        mTvDesc.setText(user.getPersonal());
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
    public void onClick(View v) {
        // 显示对话框
        showMyDialog(v.getId());
    }

    /**
     * 显示对话框
     */
    private void showMyDialog(final int viewId) {
        String title = getTile(viewId);
        switch (viewId) {// 9个字段
            case R.id.layout_email://不能修改邮箱
                T.show(mContext, "邮箱不能修改");
                break;
            case R.id.layout_degree://学历
                showMaterialDialog(getTile(viewId), StateTable.getPersonDegreeRadio(), viewId);
                break;
            case R.id.layout_major://专业
                showMaterialDialog(getTile(viewId), StateTable.getPersonMajorRadio(), viewId);
                break;
            case R.id.layout_research_direction://研究方向
                showMaterialDialog(getTile(viewId), StateTable.getPersonResearchRadio(), viewId);
                break;
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

                        switch (viewId) {
                            case R.id.layout_username:
                                mTvUsername.setText(str);
                                break;
                            case R.id.layout_age:
                                mTvAge.setText(str);
                                break;
                            case R.id.layout_tel:
                                mTvTel.setText(str);
                                break;
                            case R.id.layout_work_place:
                                mTvWorkPlace.setText(str);
                                break;
                            case R.id.layout_desc:
                                mTvDesc.setText(str);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 显示有单选按钮的对话框
     *
     * @param title
     * @param dataSource
     * @return
     */
    private void showMaterialDialog(String title, String[] dataSource, final int viewId) {
        new MaterialDialog.Builder(mContext)
                .title(title)
                .items(dataSource)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (viewId) {
                            case R.id.layout_degree:
                                mTvDegree.setText(text);
                                break;
                            case R.id.layout_major:
                                mTvMajor.setText(text);
                                break;
                            case R.id.layout_research_direction:
                                mTvResearchDirection.setText(text);
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    /**
     * 得到dialog的标题
     *
     * @param viewId
     * @return
     */
    private String getTile(int viewId) {
        String title = "";
        switch (viewId) {//9个字段
            case R.id.layout_email:
                title = "邮箱";
                break;
            case R.id.layout_username:
                title = "姓名";
                break;
            case R.id.layout_age:
                title = "年龄";
                break;
            case R.id.layout_tel:
                title = "电话";
                break;
            case R.id.layout_degree:
                title = "学历";
                break;
            case R.id.layout_major:
                title = "专业";
                break;
            case R.id.layout_research_direction:
                title = "研究方向";
                break;
            case R.id.layout_work_place:
                title = "工作单位";
                break;
            case R.id.layout_desc:
                title = "个人简介";
                break;
        }
        return title;
    }

    /**
     * 加载布局
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        return true;
    }

    /**
     * toolbar的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ok) {
            ProgressDialogUtil.show(mContext);//显示进度条
            OkHttpUtils
                    .post()
                    .url(Constant.URL + "update")
                    .addParams("source", "android")

                    .addParams("email", mTvEmail.getText().toString().trim())
                    .addParams("name", mTvUsername.getText().toString().trim())
                    .addParams("age", mTvAge.getText().toString().trim())

                    .addParams("phone", mTvTel.getText().toString().trim())
                    .addParams("education", mTvDegree.getText().toString().trim())
                    .addParams("professional", mTvMajor.getText().toString().trim())

                    .addParams("research", mTvResearchDirection.getText().toString().trim())
                    .addParams("work", mTvWorkPlace.getText().toString().trim())
                    .addParams("personal", mTvDesc.getText().toString().trim())

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
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}



