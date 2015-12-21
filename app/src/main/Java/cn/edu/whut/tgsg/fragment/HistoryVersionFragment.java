package cn.edu.whut.tgsg.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
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
        // 设置底部按钮
        setupButton();
    }

    @Override
    protected void initListener() {
        /**
         * 历史版本点击
         */
        mListHistoryVersion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "历史版本" + position);
            }
        });
    }

    /**
     * 初始化历史版本列表
     */
    private void initHistoryVersionList() {
        List<ManuscriptVersion> list = new ArrayList<>();
        ManuscriptVersion manuscriptVersion = mManuscript.getManuscriptVersion();
        list.add(new ManuscriptVersion(1, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeyword(), manuscriptVersion.getPath(), manuscriptVersion.getVersionTime()));
        list.add(new ManuscriptVersion(2, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeyword(), manuscriptVersion.getPath(), "2015-12-07 15:19:11"));
        list.add(new ManuscriptVersion(3, manuscriptVersion.getTitle(), manuscriptVersion.getSummary(), manuscriptVersion.getKeyword(), manuscriptVersion.getPath(), "2015-12-05 09:19:11"));
        mAdapter = new HistoryVersionAdapter(mContext, list);
        mListHistoryVersion.setAdapter(mAdapter);
    }

    /**
     * 设置底部按钮
     */
    private void setupButton() {
        switch (MyApplication.GLOBAL_USER.getRole().getId()) {
            case 2:
                mBtnUpload.setVisibility(View.INVISIBLE);
                break;
            case 3:
                // 设置上传新版本按钮外观
                if (mManuscript.getState() == 1 || mManuscript.getState() == 6) {
                    mBtnUpload.setBackgroundResource(R.drawable.btn_common_selector);
                    mBtnUpload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            T.show(mContext, "可以上传新版本！！！");

                        }
                    });
                } else {
                    mBtnUpload.setBackgroundResource(R.drawable.btn_unclick_selector);
                    mBtnUpload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            T.show(mContext, "当前状态不能上传新版本");
                        }
                    });
                }
                break;
            default:
        }
    }
}
