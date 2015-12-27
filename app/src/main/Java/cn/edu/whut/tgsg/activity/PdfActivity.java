package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * pdf阅读
 * <p/>
 * Created by xwh on 2015/12/16.
 */
public class PdfActivity extends BaseActivity implements OnPageChangeListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.pdfView)
    PDFView mPdfView;

    ManuscriptVersion mManuscriptVersion;

    @Override
    protected String getTagName() {
        return "PdfActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected Context getContext() {
        return PdfActivity.this;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mToolbar);
        // 获取传来的ManuscriptVersion对象
        Bundle bundle = getIntent().getExtras();
        mManuscriptVersion = (ManuscriptVersion) bundle.getSerializable("manuscriptversion");
        if (!mManuscriptVersion.getPath().endsWith(".pdf")) {
            T.show(mContext, "本系统当前只支持pdf格式的稿件阅读。");
        } else {
            // 请求服务器下载稿件并查看
            requestServer();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        mToolbar.setTitle(String.format("%s %s / %s", mManuscriptVersion.getTitle(), page, pageCount));
    }

    /**
     * 请求服务器下载稿件并查看
     */
    private void requestServer() {
        OkHttpUtils
                .get()
                .url(Constant.STATIC_URL + "upload/" + mManuscriptVersion.getPath())
                .addParams("source", "android")
                .tag(this)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), mManuscriptVersion.getPath()) {
                    @Override
                    public void inProgress(float progress) {

                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(getTagName(), "下载文件失败！！！");
                        T.show(mContext, "下载文件失败！！！");
                    }

                    @Override
                    public void onResponse(File response) {
                        Log.e(getTagName(), "下载文件成功！！！");
                        T.show(mContext, "下载文件成功！！！");
                        // pdf展示
                        display(response);
                    }
                });
    }

    /**
     * pdf展示
     *
     * @param file
     */
    private void display(File file) {
        mPdfView.fromFile(file)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onPageChange(this)
                .load();
    }
}
