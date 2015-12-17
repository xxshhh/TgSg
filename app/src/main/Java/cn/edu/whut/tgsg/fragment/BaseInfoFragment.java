package cn.edu.whut.tgsg.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
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
    }

    @Override
    protected void initListener() {
        /**
         * 取消投稿
         */
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "取消投稿");
            }
        });

        // 设置取消投稿按钮外观
        if (mManuscript.getState() == 1) {
            mBtnCancel.setBackgroundResource(R.drawable.btn_selector);
        } else {
            mBtnCancel.setBackgroundResource(R.drawable.btn_unclick_selector);
        }
        /**
         * 取消投稿
         */
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mManuscript.getState() == 1) {
                    T.show(mContext, "可以取消投稿！！！");
                } else {
                    T.show(mContext, "当前状态不能取消投稿");
                }
            }
        });
    }
}
