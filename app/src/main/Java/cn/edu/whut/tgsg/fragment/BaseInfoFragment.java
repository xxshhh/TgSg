package cn.edu.whut.tgsg.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.activity.PdfActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
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
    protected int getContentLayoutId() {
        return R.layout.fragment_baseinfo;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件对象
        mManuscript = ((ManuscriptDetailActivity) getActivity()).getManuscript();
        // 初始化页面数据
        mManuscriptVersion = mManuscript.getManuscriptVersion();
        String info = "标题：" + mManuscriptVersion.getTitle() + "\n"
                + "投稿时间：" + mManuscript.getDate() + "\n"
                + "类别：" + mManuscript.getSort() + "\n"
                + "关键词：" + mManuscriptVersion.getKeywords().toString() + "\n"
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
        switch (Constant.GLOBAL_USER.getRole()) {
            case 1:
                // 设置取消投稿按钮外观
                if (mManuscript.getState() == 1) {
                    mBtnCancel.setBackgroundResource(R.drawable.btn_common_selector);
                    mBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialogWrapper.Builder(mContext)
                                    .setTitle("取消投稿")
                                    .setMessage("真的要取消投稿吗？")
                                    .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            T.show(mContext, "已经取消投稿！！！");
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton(R.string.disagree, null)
                                    .show();
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
            case 2:
                mBtnCancel.setVisibility(View.INVISIBLE);
                break;
            case 3:
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
}
