package cn.edu.whut.tgsg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.fragment.EmptyFragment;
import cn.edu.whut.tgsg.fragment.HomeFragment;
import cn.edu.whut.tgsg.fragment.InformFragment;
import cn.edu.whut.tgsg.fragment.SettingsFragment;
import cn.edu.whut.tgsg.fragment.author.AuthorManuscriptFragment;
import cn.edu.whut.tgsg.fragment.editor.EditorManuscriptFragment;
import cn.edu.whut.tgsg.fragment.expert.ExpertManuscriptFragment;
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

    ActionBarDrawerToggle mDrawerToggle;

    private static final int REQUEST_CODE_UPDATE_IMAGE = 1;//获取图片更新信息

    @Override
    protected String getTagName() {
        return "ForgetPswActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected void initData() {
        mToolbar.setTitle("首页");
        // toolbar替换actionbar
        setSupportActionBar(mToolbar);
        // 设置抽屉开关
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 设置抽屉视图
        setupDrawerContent(mNavigationView);
        // 跳到首页
        switchToHome();
        // 显示头像
        displayProfileImage();
    }

    @Override
    protected void initListener() {
        /**
         * 个人信息
         */
        mNavigationView.getHeaderView(0).findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "person");
                Intent intent = new Intent(mContext, PersonInfoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_IMAGE);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    private Fragment mFragment;
    private android.app.Fragment settingFragment;

    private void switchToHome() {
        mFragment = new HomeFragment();
        if (settingFragment != null) {
            getFragmentManager().beginTransaction().remove(settingFragment).commit();
            settingFragment = null;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mFragment).commit();
        mToolbar.setTitle("首页");
    }

    private void switchToManuscript() {
        // 1-管理员 2-编辑 3-作者 4-专家
        Fragment fragment = null;
        switch (MyApplication.GLOBAL_USER.getRole().getId()) {
            case 2:
                fragment = new EditorManuscriptFragment();
                break;
            case 3:
                fragment = new AuthorManuscriptFragment();
                break;
            case 4:
                fragment = new ExpertManuscriptFragment();
                break;
            default:
                fragment = new EmptyFragment();
        }
        mFragment = fragment;
        if (settingFragment != null) {
            getFragmentManager().beginTransaction().remove(settingFragment).commit();
            settingFragment = null;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
        mToolbar.setTitle("稿件管理");
    }

    private void switchToMessage() {
        mFragment = new InformFragment();
        if (settingFragment != null) {
            getFragmentManager().beginTransaction().remove(settingFragment).commit();
            settingFragment = null;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mFragment).commit();
        mToolbar.setTitle("通知");
    }

    private void switchToSetting() {
        settingFragment = new SettingsFragment();
        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
            mFragment = null;
        }
        getFragmentManager().beginTransaction().replace(R.id.frame_content, settingFragment).commit();
        mToolbar.setTitle("设置");
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
                            case R.id.navigation_inform:
                                T.show(mContext, "message");
                                switchToMessage();
                                break;
                            case R.id.navigation_setting:
                                T.show(mContext, "setting");
                                switchToSetting();
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    /**
     * 显示头像
     */
    private void displayProfileImage() {
        OkHttpUtils
                .get()
                .url(Constant.STATIC_URL + "img/" + MyApplication.GLOBAL_USER.getPhoto())
                .addParams("source", "android")
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(getTagName(), "加载头像失败！！！使用默认头像。");
                    }

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e(getTagName(), "加载头像成功！！！");
                        ((CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageBitmap(bitmap);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_UPDATE_IMAGE) {
                if (data.getExtras().getBoolean("isUpdatePhoto")) {
                    // 显示头像
                    displayProfileImage();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 更新昵称
        if (MyApplication.GLOBAL_USER.getName() != null)
            ((TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_username)).setText(MyApplication.GLOBAL_USER.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem ok = menu.findItem(R.id.action_ok);
        ok.setVisible(false);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
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
