package cn.edu.whut.tgsg.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.activity.PdfActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.ProgressDialogUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 稿件基本信息界面
 * <p/>
 * Created by xwh on 2015/12/14.
 */
public class BaseInfoFragment extends BaseFragment {

    @Bind(R.id.tv_baseInfo)
    TextView mTvInfo;
    @Bind(R.id.btn_cancel)
    Button mBtnCancel;

    Manuscript mManuscript;
    ManuscriptVersion mManuscriptVersion;

    @Override
    protected String getTagName() {
        return "BaseInfoFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_baseinfo;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件版本对象
        mManuscriptVersion = ((ManuscriptDetailActivity) getActivity()).getManuscriptVersion();
        mManuscript = mManuscriptVersion.getArticle();
        // 初始化页面数据
        String info = "标题：" + mManuscriptVersion.getTitle() + "\n"
                + "投稿时间：" + mManuscript.getContributeTime() + "\n"
                + "类别：" + mManuscript.getType() + "\n"
                + "关键词：" + mManuscriptVersion.getKeyword() + "\n"
                + "摘要：" + mManuscriptVersion.getSummary() + "\n";
        mTvInfo.setText(info);
        // 设置底部按钮
        setupButton();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 设置底部按钮
     */
    private void setupButton() {
        switch (MyApplication.GLOBAL_USER.getRole().getId()) {
            case 2:
                mBtnCancel.setVisibility(View.INVISIBLE);
                break;
            case 3:
                // 设置取消投稿按钮外观
                if (mManuscript.getState() == 1) {
                    mBtnCancel.setBackgroundResource(R.drawable.btn_common_selector);
                    mBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(mContext)
                                    .title("取消投稿")
                                    .content("真的要取消投稿吗？")
                                    .positiveText(R.string.agree)
                                    .negativeText(R.string.disagree)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                            materialDialog.dismiss();
                                            // 向服务器请求取消投稿
                                            requestServer();
                                        }
                                    }).show();
                        }
                    });
                } else {
                    mBtnCancel.setBackgroundResource(R.drawable.btn_unclick_selector);
                    mBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            T.show(mContext, "当前状态不能取消投稿");
                        }
                    });
                }
                break;
            case 4:
                mBtnCancel.setText("查看稿件");
                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PdfActivity.class);
                        startActivity(intent);
                    }
                });
            default:
        }
    }

    /**
     * 向服务器请求取消投稿
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "cancelContribute")
                .addParams("articleId", String.valueOf(mManuscript.getId()))
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
                                T.show(mContext, "取消投稿成功！！！");
                                getActivity().finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
