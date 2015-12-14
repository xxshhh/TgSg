package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
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

    Manuscript mManuscript;
    List<ManuscriptVersion> mManuscriptVersionList;

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
                T.show(mContext, String.valueOf(mManuscriptVersionList.size() - position));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化历史版本列表
     */
    private void initHistoryVersionList() {
        mManuscriptVersionList = new ArrayList<>();
        ManuscriptVersion manuscriptVersion = mManuscript.getManuscriptVersion();
        mManuscriptVersionList.add(new ManuscriptVersion(1, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), manuscriptVersion.getDate()));
        mManuscriptVersionList.add(new ManuscriptVersion(2, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-07 15:19:11"));
        mManuscriptVersionList.add(new ManuscriptVersion(3, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-05 09:19:11"));
        mManuscriptVersionList.add(new ManuscriptVersion(4, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeywords(), manuscriptVersion.getUrl(), "2015-12-02 12:19:11"));
        HistoryVersionAdapter adapter = new HistoryVersionAdapter(mContext, mManuscriptVersionList);
        mListHistoryVersion.setAdapter(adapter);
    }
}
