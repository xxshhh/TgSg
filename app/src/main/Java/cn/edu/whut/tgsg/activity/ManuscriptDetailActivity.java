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
import cn.edu.whut.tgsg.fragment.EmptyFragment;

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

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_manuscriptdetail;
    }

    @Override
    protected Context getContext() {
        return ManuscriptDetailActivity.this;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        mToolbar.setTitle("乖，摸摸头");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EmptyFragment(), "历史版本");
        adapter.addFragment(new EmptyFragment(), "意见评论");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
