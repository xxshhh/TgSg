package cn.edu.whut.tgsg.fragment.expert;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.ViewpagerAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;

/**
 * 专家稿件界面
 * <p/>
 * Created by ylj on 2015/12/17.
 */
public class ExpertManuscriptFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    ExpertUnhandleFragment mExpertUnhandleFragment;

    ExpertHandleFragment mExpertHandleFragment;

    @Override
    protected String getTagName() {
        return "ExpertManuscriptFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_expert_manuscript;
    }

    @Override
    protected void initData() {
        // 设置Viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager());
        mExpertUnhandleFragment = new ExpertUnhandleFragment();
        mExpertHandleFragment = new ExpertHandleFragment();
        adapter.addFragment(mExpertUnhandleFragment, "未审稿");
        adapter.addFragment(mExpertHandleFragment, "已审稿");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 返回结果分发给专家未审核界面
        mExpertUnhandleFragment.onActivityResult(requestCode, resultCode, data);
    }
}
