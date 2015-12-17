package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;

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

    String pdfName = "sample.pdf";

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
        display(pdfName);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        mToolbar.setTitle(String.format("%s %s / %s", pdfName, page, pageCount));
    }

    /**
     * pdf展示
     *
     * @param assetFileName
     */
    private void display(String assetFileName) {
        mToolbar.setTitle(pdfName = assetFileName);

        mPdfView.fromAsset(assetFileName)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onPageChange(this)
                .load();
    }
}
