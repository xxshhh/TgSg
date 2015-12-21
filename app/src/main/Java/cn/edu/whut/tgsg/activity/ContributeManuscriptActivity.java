package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.next.tagview.TagCloudView;

/**
 * 投稿界面
 * <p/>
 * Created by ylj on 2015-12-20.
 */
public class ContributeManuscriptActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_manuscript_title)
    TextView mTvManuscriptTitle;
    @Bind(R.id.edt_manuscript_title)
    EditText mEdtManuscriptTitle;
    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.btn_add_keywords)
    Button mBtnAddKeywords;
    @Bind(R.id.tcv_manuscript_keywords)
    TagCloudView mTcvManuscriptKeywords;
    @Bind(R.id.btn_add_file)
    Button mBtnAddFile;
    @Bind(R.id.tv_file_name)
    TextView mTvFileName;
    @Bind(R.id.edt_summary)
    EditText mEdtSummary;
    @Bind(R.id.btn_contribute)
    Button mBtnContribute;

    List<String> mKeywords;

    @Override
    protected String getTagName() {
        return "ContributeManuscriptActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_contribute_manuscript;
    }

    @Override
    protected Context getContext() {
        return ContributeManuscriptActivity.this;
    }

    @Override
    protected void initData() {
        // 设置toolbar
        mToolbar.setTitle("我要投稿");
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
         * 添加关键词
         */
        mBtnAddKeywords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeywords != null && mKeywords.size() >= 5) {
                    T.show(mContext, "最多只能有5个关键词");
                    return;
                }
                final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title("添加")
                        .input(null, null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                            }
                        })
                        .positiveText(R.string.agree)
                        .show();
                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mKeywords == null) {
                            mKeywords = new ArrayList<>();
                        }
                        if (mKeywords.contains(dialog.getInputEditText().getText().toString().trim())) {
                            T.show(mContext, "该关键词存在！！！");
                            return;
                        }
                        mKeywords.add(dialog.getInputEditText().getText().toString().trim());
                        mTcvManuscriptKeywords.setTags(mKeywords);
                    }
                });
            }
        });

        /**
         * 删除关键词
         */
        mTcvManuscriptKeywords.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(final int position) {
                new AlertDialogWrapper.Builder(mContext)
                        .setTitle("删除")
                        .setMessage("真的要删除该关键词吗？")
                        .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mKeywords.remove(position);
                                mTcvManuscriptKeywords.setTags(mKeywords);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.disagree, null)
                        .show();
            }
        });

        /**
         * 添加文件
         */
        mBtnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 文件上传
            }
        });


        /**
         * 投稿
         */
        mBtnContribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getManuscriptSortSpinner();
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
