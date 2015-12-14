package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.ViewpagerAdapter;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.fragment.BaseInfoFragment;
import cn.edu.whut.tgsg.fragment.EmptyFragment;
import cn.edu.whut.tgsg.fragment.HistoryVersionFragment;

/**
 * 稿件详情界面
 * <p/>
 * Created by xwh on 2015/12/9.
 */
public class ManuscriptDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    Manuscript mManuscript;

    /**
     * 获取当前稿件
     *
     * @return
     */
    public Manuscript getManuscript() {
        return mManuscript;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_manuscriptdetail;
    }

    @Override
    protected Context getContext() {
        return ManuscriptDetailActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的Manuscript对象并设置toolbar
        Bundle bundle = getIntent().getExtras();
        mManuscript = (Manuscript) bundle.getSerializable("manuscript");
        mToolbar.setTitle(mManuscript.getManuscriptVersion().getTitle());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 设置Viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BaseInfoFragment(), "基本信息");
        adapter.addFragment(new HistoryVersionFragment(), "历史版本");
        adapter.addFragment(new EmptyFragment(), "留言");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
