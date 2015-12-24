package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.Notice;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * 公告详情界面
 * <p/>
 * Created by xwh on 2015/12/16.
 */
public class NoticeDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.web_notice)
    WebView mWebNotice;

    Notice mNotice;

    @Override
    protected String getTagName() {
        return "NoticeDetailActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected Context getContext() {
        return NoticeDetailActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的Notice对象并设置toolbar
        Bundle bundle = getIntent().getExtras();
        mNotice = (Notice) bundle.getSerializable("notice");
        mToolbar.setTitle(mNotice.getTitle());
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 请求服务器，并渲染公告展示界面
        requestServer();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 渲染公告展示界面
     */
    private void initWeb() {
        mWebNotice.loadDataWithBaseURL(null, mNotice.getContent(), "text/html", "utf-8", null);
        WebSettings settings = mWebNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    /**
     * 请求服务器
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "findNoticeDetail")
                .addParams("source", "android")
                .addParams("id", String.valueOf(mNotice.getId()))
                .addParams("title", mNotice.getTitle())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(getTagName(), "onError:" + e.getMessage());
                        T.show(mContext, "网络访问错误");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(getTagName(), "onResponse:" + response);
                        mNotice.setContent(response.substring(1,response.length()-3));
                        // 渲染公告展示界面
                        initWeb();
                    }
                });
    }
}
