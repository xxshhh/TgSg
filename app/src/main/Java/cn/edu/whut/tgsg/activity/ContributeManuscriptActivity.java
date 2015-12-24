package cn.edu.whut.tgsg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
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

    File mFile;

    String mFileName;

    private static final int REQUEST_CODE_PICK_FILE = 1;//选择文件

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
        // 获得传来的Intent
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mToolbar.setTitle("上传新版本");
        } else {
            mToolbar.setTitle("我要投稿");
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
    }

    @Override
    protected void initListener() {
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
                        dialog.dismiss();
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
                // 选择文件
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"), REQUEST_CODE_PICK_FILE);
            }
        });

        /**
         * 投稿
         */
        mBtnContribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleStr = mEdtManuscriptTitle.getText().toString().trim();
                if (TextUtils.isEmpty(titleStr)) {
                    T.show(mContext, "稿件标题不能为空");
                    return;
                }
                if (mSpinnerState.getSelectedItemPosition() == 0) {
                    T.show(mContext, "请选择稿件类别");
                    return;
                }
                int sortInt = mSpinnerState.getSelectedItemPosition();
                if (mKeywords == null || mKeywords.size() == 0) {
                    T.show(mContext, "请选择添加稿件关键词");
                    return;
                }
                String keyword = "";
                for (int i = 0; i < mKeywords.size(); i++) {
                    if (i == 0) {
                        keyword += mKeywords.get(i);
                    } else {
                        keyword += "," + mKeywords.get(i);
                    }
                }
                if (mFile == null) {
                    T.show(mContext, "请添加稿件");
                    return;
                }
                String summaryStr = mEdtSummary.getText().toString().trim();
                if (TextUtils.isEmpty(summaryStr)) {
                    T.show(mContext, "稿件摘要不能为空");
                    return;
                }
                // 获得传来的Intent
                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    // 向服务器发出请求上传新版本
                    requestServer(titleStr, sortInt, keyword, summaryStr, getIntent().getExtras().getInt("articleId"));
                } else {
                    // 向服务器发出请求投稿
                    requestServer(titleStr, sortInt, keyword, summaryStr);
                }
            }
        });
    }

    /**
     * 向服务器发出请求投稿
     */
    private void requestServer(String title, int type, String keyword, String summary) {
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "contributeA")
                .addParams("title", title)
                .addParams("type", String.valueOf(type))
                .addParams("keyword", keyword)
                .addParams("summary", summary)
                .addFile("articleFile", mFileName, mFile)
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
                            if (isSuccess) {
                                T.show(mContext, "投稿成功！！！");
                                // 返回投稿信息
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("isContributeManuscript", isSuccess);
                                ContributeManuscriptActivity.this.setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 向服务器发出上传新版本
     */
    private void requestServer(String title, int type, String keyword, String summary, int id) {
        ProgressDialogUtil.show(mContext);
        OkHttpUtils
                .post()
                .url(Constant.URL + "recontribute")
                .addParams("articleId", String.valueOf(id))
                .addParams("title", title)
                .addParams("type", String.valueOf(type))
                .addParams("keyword", keyword)
                .addParams("summary", summary)
                .addFile("articleFile", mFileName, mFile)
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
                            if (isSuccess) {
                                T.show(mContext, "上传新版本成功！！！");
                                // 返回上传新版本信息
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("isContributeNewManuscript", isSuccess);
                                ContributeManuscriptActivity.this.setResult(Activity.RESULT_OK, returnIntent);
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
        String[] array = StateTable.getManuscriptSortSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK_FILE) {
                File file = null;
                try {
                    Uri uri = data.getData();
                    String url = uri.getPath();
                    Log.e(getTagName(), url);
                    file = new File(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    // 选择文件成功
                    mFile = file;
                    mFileName = mFile.getAbsolutePath().substring(mFile.getAbsolutePath().lastIndexOf("/") + 1);
                    mTvFileName.setText(mFileName);
                    T.show(mContext, "选择文件成功");
                }
            }
        }
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
