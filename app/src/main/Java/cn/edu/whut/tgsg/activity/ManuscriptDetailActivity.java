package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.ViewpagerAdapter;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.fragment.BaseInfoFragment;
import cn.edu.whut.tgsg.fragment.EmptyFragment;
import cn.edu.whut.tgsg.fragment.ExamineOpinionFragment;
import cn.edu.whut.tgsg.fragment.HistoryVersionFragment;
import cn.edu.whut.tgsg.fragment.MessageFragment;

/**
 * 稿件详情界面（包括作者、编辑、）
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

    ManuscriptVersion mManuscriptVersion;

    Intent mReturnIntent = new Intent();

    /**
     * 获取当前返回的Intent
     *
     * @return
     */
    public Intent getReturnIntent() {
        return mReturnIntent;
    }

    /**
     * 获取当前稿件版本对象
     *
     * @return
     */
    public ManuscriptVersion getManuscriptVersion() {
        return mManuscriptVersion;
    }

    @Override
    protected String getTagName() {
        return "ManuscriptDetailActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_manuscript_detail;
    }

    @Override
    protected Context getContext() {
        return ManuscriptDetailActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的ManuscriptVersion对象并设置toolbar
        Bundle bundle = getIntent().getExtras();
        mManuscriptVersion = (ManuscriptVersion) bundle.getSerializable("manuscriptversion");
        mToolbar.setTitle(mManuscriptVersion.getTitle());
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 根据用户角色设置Viewpager
        initViewPager();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 根据用户角色设置Viewpager
     */
    private void initViewPager() {
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        switch (MyApplication.GLOBAL_USER.getRole().getId()) {
            case 2:
                adapter.addFragment(new BaseInfoFragment(), "基本信息");
                adapter.addFragment(new HistoryVersionFragment(), "历史版本");
                adapter.addFragment(new ExamineOpinionFragment(), "审稿意见");
                adapter.addFragment(new MessageFragment(), "留言信息");
                break;
            case 3:
                adapter.addFragment(new BaseInfoFragment(), "基本信息");
                adapter.addFragment(new HistoryVersionFragment(), "历史版本");
                adapter.addFragment(new MessageFragment(), "留言信息");
                break;
            case 4:
                adapter.addFragment(new BaseInfoFragment(), "基本信息");
                adapter.addFragment(new ExamineOpinionFragment(), "审稿意见");
            default:
        }
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
