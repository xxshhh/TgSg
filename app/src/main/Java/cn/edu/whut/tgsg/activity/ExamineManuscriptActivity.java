package cn.edu.whut.tgsg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.DistributeExpert;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 审核稿件
 * <p/>
 * Created by ylj on 2015-12-16.
 */
public class ExamineManuscriptActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_manuscript_title)
    TextView mTvManuscriptTitle;
    @Bind(R.id.btn_read)
    Button mBtnRead;
    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.edt_suggestion)
    EditText mEdtSuggestion;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;

    ExamineManuscript mExamineManuscript;

    DistributeExpert mDistributeExpert;

    ManuscriptVersion mManuscriptVersion;

    int mFlag;

    @Override
    protected String getTagName() {
        return "ExamineManuscriptActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_examine_manuscript;
    }

    @Override
    protected Context getContext() {
        return ExamineManuscriptActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的ManuscriptVersion对象
        Bundle bundle = getIntent().getExtras();
        mFlag = bundle.getInt("isFlag");
        // 设置toolbar
        switch (mFlag) {
            case 1:
                mToolbar.setTitle("编辑初审");
                mExamineManuscript = (ExamineManuscript) bundle.getSerializable("examinemanuscript");
                mManuscriptVersion = mExamineManuscript.getArticleVersion();
                break;
            case 2:
                mToolbar.setTitle("编辑复审");
                mExamineManuscript = (ExamineManuscript) bundle.getSerializable("examinemanuscript");
                mManuscriptVersion = mExamineManuscript.getArticleVersion();
                break;
            case 3:
                mToolbar.setTitle("专家审稿");
                mDistributeExpert = (DistributeExpert) bundle.getSerializable("distributeexpert");
                mManuscriptVersion = mDistributeExpert.getArticleVersion();
                break;
        }
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 初始化状态下拉框
        initSpinnerState();
        // 标题获得焦点,防止自动弹出软键盘
        mTvManuscriptTitle.requestFocus();
        mTvManuscriptTitle.setText(mManuscriptVersion.getTitle());
    }

    @Override
    protected void initListener() {
        /**
         * 查看稿件
         */
        mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PdfActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", mManuscriptVersion);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /**
         * 提交审稿结果
         */
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinnerState.getSelectedItemPosition() == 0) {
                    T.show(mContext, "审稿结果不能为空");
                    return;
                } else if (TextUtils.isEmpty(mEdtSuggestion.getText().toString().trim())) {
                    T.show(mContext, "审稿意见不能为空");
                    return;
                }
                int result = 0;
                switch (mFlag) {
                    case 1:
                        if (mSpinnerState.getSelectedItemPosition() == 1)
                            result = 2;
                        else
                            result = 1;
                        break;
                    case 2:
                        if (mSpinnerState.getSelectedItemPosition() == 1)
                            result = 4;
                        else if (mSpinnerState.getSelectedItemPosition() == 2)
                            result = 5;
                        else
                            result = 3;
                        break;
                    case 3:
                        if (mSpinnerState.getSelectedItemPosition() == 1)
                            result = 6;
                        else
                            result = 7;
                        break;
                }
                String opinion = mEdtSuggestion.getText().toString().trim();
                // 向服务器发出请求审稿
                requestServer(opinion, result);
            }
        });
    }

    /**
     * 向服务器发出请求审稿
     */
    private void requestServer(String opinion, int result) {
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "auditingArticle")
                .addParams("articleId", String.valueOf(mManuscriptVersion.getArticle().getId()))
                .addParams("opinion", opinion)
                .addParams("result", String.valueOf(result))
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
                            boolean isSuccess = serverInfo.getBoolean("message");
                            if (isSuccess) {
                                T.show(mContext, "审稿成功！！！");
                                // 返回审稿信息
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("isExamineManuscript", isSuccess);
                                ExamineManuscriptActivity.this.setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = new String[]{};
        switch (mFlag) {
            case 1:
                array = StateTable.getEditorExamineSpinner();
                break;
            case 2:
                array = StateTable.getEditorReExamineSpinner();
                break;
            case 3:
                array = StateTable.getExpertExamineSpinner();
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }
}
