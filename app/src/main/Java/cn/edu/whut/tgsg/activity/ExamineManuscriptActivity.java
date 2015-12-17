package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 审核稿件
 * <p/>
 * Created by ylj on 2015-12-16.
 */
public class ExamineManuscriptActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_submit_suggestion)
    Button mBtnSubmitSuggestion;
    @Bind(R.id.edt_suggestion)
    TextView mEdtSuggestion;
    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.btn_read)
    Button mBtnRead;

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
        //设置toolbar
        mToolbar.setTitle("审稿");
        setSupportActionBar(mToolbar);
        //设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 初始化状态下拉框
        initSpinnerState();
    }

    @Override
    protected void initListener() {
        mBtnRead.setOnClickListener(this);
        mBtnSubmitSuggestion.setOnClickListener(this);
    }


    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getExpertSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read:
                //查看稿件内容
                Intent intent = new Intent(mContext, PdfActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit_suggestion:
                //提交评审意见
                subMmitSuggestion();
                break;
        }
    }

    /**
     * 提交评审意见
     */
    private void subMmitSuggestion() {
        T.show(mContext, "提交评审意见");
    }
}
