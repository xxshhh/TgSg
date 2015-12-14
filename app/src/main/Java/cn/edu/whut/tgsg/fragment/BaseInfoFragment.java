package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;

/**
 * 稿件基本信息界面
 * <p/>
 * Created by xwh on 2015/12/14.
 */
public class BaseInfoFragment extends BaseFragment {

    @Bind(R.id.tv_baseInfo)
    TextView mTvInfo;

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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
