package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.fragment.ExpertPendingFragment;
import cn.edu.whut.tgsg.fragment.ExpertSolvedFragment;

/**
 * 专家的稿件管理界面
 * <p/>
 * Created by ylj on 2015-12-07.
 */
public class ExpertCKGJActivity extends BaseActivity implements View.OnClickListener {

    public static final int TABSOLVED = 0;
    public static final int TABPENDING = 1;

    //声明并初始化
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.btn_solved)
    ImageButton mBtnSolved;
    @Bind(R.id.linear_solved)
    LinearLayout mLinearSolved;
    @Bind(R.id.btn_pending)
    ImageButton mBtnPending;
    @Bind(R.id.linear_pending)
    LinearLayout mLinearPending;


    private Fragment mTabSolvedFrag;
    private Fragment mTabPendingFrag;

    private FragmentPagerAdapter mAdapter;//适配器声明
    private List<Fragment> mFragments;//数据源初始化

    @Override
    protected int getContentLayoutId() {

        return R.layout.activity_expert_ckgj;
    }

    @Override
    protected Context getContext() {
        return ExpertCKGJActivity.this;
    }

    @Override
    protected void initListener() {

        mLinearSolved.setOnClickListener(this);
        mLinearPending.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {

        //初始化数据源
        mFragments = new ArrayList<Fragment>();
        mTabSolvedFrag = new ExpertSolvedFragment();//实例化
        mTabPendingFrag = new ExpertPendingFragment();//实例化
        mFragments.add(mTabSolvedFrag);
        mFragments.add(mTabPendingFrag);
        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("稿件管理");

        setSelected(TABSOLVED);
    }


    /**
     * 建立菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * 对选中的菜单进行操作
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(ExpertCKGJActivity.this, "search", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 把所有的图片都变暗色
     */
    private void resetImg() {
        mBtnSolved.setImageResource(R.mipmap.ic_launcher);
        mBtnPending.setImageResource(R.mipmap.ic_launcher);

    }

    /**
     * 当选中时：
     * 1，切换为对应的显示内容
     * 2，对应图标点亮
     */
    private void setSelected(int i) {

        setTab(i);

        mViewPager.setCurrentItem(i);
    }

    /**
     * 设置下部显示正确的图标
     *
     * @param item
     */
    private void setTab(int item) {
        resetImg();
      /*  FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();*/
//        hideFragments(transaction);//先隐藏起来
        switch (item) {
            case TABSOLVED:
//                mViewPager.setCurrentItem(TABSOLVED);
//                transaction.replace(R.id.tab_content, new ExpertSolvedFragment());

                mBtnSolved.setImageResource(R.mipmap.ic_launcher);

                break;
            case TABPENDING:
                mBtnPending.setImageResource(R.mipmap.ic_launcher);

                break;
        }
    }

    @Override
    public void onClick(View v) {

//点击时：Fragment+Img对应变化
        switch (v.getId()) {
            case R.id.linear_solved:
                setSelected(TABSOLVED);
                break;
            case R.id.linear_pending:
                setSelected(TABPENDING);
                break;
        }
    }


}
