package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 审核稿件
 * <p/>
 * Created by ylj on 2015-12-16.
 */
public class ExamineManuscriptActivity extends BaseActivity {

    Manuscript mManuscript;
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
        // 设置toolbar
        mToolbar.setTitle("审稿");
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 获取传来的Manuscript对象
        Bundle bundle = getIntent().getExtras();
        mManuscript = (Manuscript) bundle.getSerializable("manuscript");
        // 初始化状态下拉框
        initSpinnerState();
        // 标题获得焦点,防止自动弹出软键盘
        mTvManuscriptTitle.requestFocus();
        //mTvManuscriptTitle.setText(mManuscript.getManuscriptVersion().getTitle());
    }

    @Override
    protected void initListener() {
        /**
         * 状态下拉框
         */
        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "你点击的是:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 查看稿件
         */
        mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PdfActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 提交审稿结果
         */
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinnerState.getSelectedItemPosition() == -1) {
                    T.show(mContext, "审稿结果不能为空！！！");
                    return;
                } else if (mEdtSuggestion.getText().toString().equals("")) {
                    T.show(mContext, "审稿意见不能为空！！！");
                    return;
                }
                String opinion = mEdtSuggestion.getText().toString();
                int result = mSpinnerState.getSelectedItemPosition() == 0 ? 1 : 0;
//                ExamineManuscript examineManuscript = new ExamineManuscript(1, MyApplication.GLOBAL_USER, mManuscript.getManuscriptVersion(), opinion, result, DateHandleUtil.convertToStandard(new Date()));
//                T.show(mContext, examineManuscript.toString());
                finish();
            }
        });
    }

    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getExpertExamineResultSpinner();
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
