package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.fragment.EmptyFragment;
import cn.edu.whut.tgsg.fragment.HomeFragment;
import cn.edu.whut.tgsg.util.T;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主界面
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.profile_image)
    CircleImageView mProfileImage;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mUsernameStr;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected void initListener() {
        /**
         * 个人信息
         */
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "person");
                switchToPerson();
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    protected void initData() {
        // toolbar替换actionbar
        setSupportActionBar(mToolbar);
        // 设置抽屉开关
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 设置抽屉视图
        setupDrawerContent(mNavigationView);

        Intent intent = getIntent();
        mUsernameStr = intent.getStringExtra("username");

        switchToHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void switchToPerson() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        Intent intent = new Intent(mContext, PersonInfoActivity.class);
        intent.putExtra("username", mUsernameStr);
        startActivity(intent);
        mToolbar.setTitle("个人信息");
    }

    private void switchToHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
        mToolbar.setTitle("首页");
    }

    private void switchToManuscript() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ManuscriptFragment()).commit();
        mToolbar.setTitle("稿件管理");
        Intent intent = new Intent(mContext, ExpertCKGJActivity.class);
        startActivity(intent);
    }

    private void switchToMessage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("消息");
    }

    private void switchToSetting() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("设置");
    }

    private void switchToTheme() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("主题");
    }

    private void switchToContact() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("联系我们");
    }

    /**
     * 设置抽屉视图
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_home:
                                T.show(mContext, "home");
                                switchToHome();
                                break;
                            case R.id.navigation_manuscript:
                                T.show(mContext, "manuscript");
                                switchToManuscript();
                                break;
                            case R.id.navigation_message:
                                T.show(mContext, "message");
                                switchToMessage();
                                break;
                            case R.id.navigation_setting:
                                T.show(mContext, "setting");
                                switchToSetting();
                                break;
                            case R.id.navigation_theme:
                                T.show(mContext, "theme");
                                switchToTheme();
                                break;
                            case R.id.navigation_contact:
                                T.show(mContext, "contact");
                                switchToContact();
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_message2 clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            T.show(mContext, "search");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            doExitApp();
        }
    }

}
