package cn.edu.whut.tgsg.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.ViewpagerAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;

/**
 * 编辑稿件界面
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class EditorManuscriptFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_manuscript;
    }

    @Override
    protected void initData() {
        // 设置Viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EditorUnhandleFragment(), "未受理");
        adapter.addFragment(new EditorHandleFragment(), "已受理");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }
}
