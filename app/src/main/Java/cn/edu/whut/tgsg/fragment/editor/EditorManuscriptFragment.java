package cn.edu.whut.tgsg.fragment.editor;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

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

    EditorHandleFragment mEditorHandleFragment;
    EditorUnhandleFragment mEditorUnhandleFragment;

    @Override
    protected String getTagName() {
        return "EditorManuscriptFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_manuscript;
    }

    @Override
    protected void initData() {
        // 设置Viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager());
        mEditorHandleFragment = new EditorHandleFragment();
        mEditorUnhandleFragment = new EditorUnhandleFragment();
        adapter.addFragment(mEditorUnhandleFragment, "未受理");
        adapter.addFragment(mEditorHandleFragment, "已受理");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 返回结果分发给编辑已受理界面
        mEditorHandleFragment.onActivityResult(requestCode, resultCode, data);
    }
}
