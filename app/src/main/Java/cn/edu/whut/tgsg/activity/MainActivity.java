package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.fragment.EmptyFragment;
import cn.edu.whut.tgsg.fragment.HomeFragment;

/**
 * 主界面
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // toolbar替换actionbar
        setSupportActionBar(mToolbar);
        // 设置抽屉开关
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 设置抽屉视图
        setupDrawerContent(mNavigationView);

        switchToHome();
    }

    private void switchToPerson() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("个人信息");
    }


    private void switchToHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
        mToolbar.setTitle("首页");
    }

    private void switchToManuscript() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
        mToolbar.setTitle("稿件管理");
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

    @OnClick(R.id.profile_image)
    void person() {
        Toast.makeText(MainActivity.this, "person", Toast.LENGTH_SHORT).show();
        switchToPerson();
        mDrawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_home:
                                Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                                switchToHome();
                                break;
                            case R.id.navigation_manuscript:
                                Toast.makeText(MainActivity.this, "manuscript", Toast.LENGTH_SHORT).show();
                                switchToManuscript();
                                break;
                            case R.id.navigation_message:
                                Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                                switchToMessage();
                                break;
                            case R.id.navigation_setting:
                                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                                switchToSetting();
                                break;
                            case R.id.navigation_theme:
                                Toast.makeText(MainActivity.this, "theme", Toast.LENGTH_SHORT).show();
                                switchToTheme();
                                break;
                            case R.id.navigation_contact:
                                Toast.makeText(MainActivity.this, "contact", Toast.LENGTH_SHORT).show();
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
        // Handle action bar item_message clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
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
