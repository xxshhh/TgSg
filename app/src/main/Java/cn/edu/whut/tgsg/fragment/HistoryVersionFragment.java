package cn.edu.whut.tgsg.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.activity.PdfActivity;
import cn.edu.whut.tgsg.adapter.HistoryVersionAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.util.T;

/**
 * 历史版本界面
 * <p/>
 * Created by xwh on 2015/12/14.
 */
public class HistoryVersionFragment extends BaseFragment {

    @Bind(R.id.list_historyVersion)
    ListView mListHistoryVersion;
    @Bind(R.id.btn_upload)
    Button mBtnUpload;

    Manuscript mManuscript;

    HistoryVersionAdapter mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_historyversion;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件对象
        mManuscript = ((ManuscriptDetailActivity) getActivity()).getManuscript();
        // 初始化历史版本列表
        initHistoryVersionList();
    }

    @Override
    protected void initListener() {
        /**
         * 历史版本点击
         */
        mListHistoryVersion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, PdfActivity.class));
            }
        });

        // 设置上传新版本按钮外观
        if (mManuscript.getState() == 1 || mManuscript.getState() == 6) {
            mBtnUpload.setBackgroundResource(R.drawable.btn_selector);
        } else {
            mBtnUpload.setBackgroundResource(R.drawable.btn_unclick_selector);
        }
        /**
         * 上传新版本
         */
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mManuscript.getState() == 1 || mManuscript.getState() == 6) {
                    T.show(mContext, "可以上传新版本！！！");
                } else {
                    T.show(mContext, "当前状态不能上传新版本");
                }
            }
        });
    }

    /**
     * 初始化历史版本列表
     */
    private void initHistoryVersionList() {
        List<ManuscriptVersion> list = new ArrayList<>();
        ManuscriptVersion manuscriptVersion = mManuscript.getManuscriptVersion();
        list.add(new ManuscriptVersion(1, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), manuscriptVersion.getDate()));
        list.add(new ManuscriptVersion(2, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-07 15:19:11"));
        list.add(new ManuscriptVersion(3, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-05 09:19:11"));
        list.add(new ManuscriptVersion(4, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(5, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(6, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(7, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(8, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(9, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(10, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        list.add(new ManuscriptVersion(11, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        HistoryVersionAdapter adapter = new HistoryVersionAdapter(mContext, list);
        mListHistoryVersion.setAdapter(adapter);
    }
}
