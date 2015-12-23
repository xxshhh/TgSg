package cn.edu.whut.tgsg.fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.MessageAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 消息界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class MessageFragment extends BaseFragment {

    @Bind(R.id.ptr_list_message)
    PullToRefreshListView mPtrListMessage;

    MessageAdapter mAdapter;

    @Override
    protected String getTagName() {
        return "MessageFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        // 初始化消息列表
        initMessageList();
        // 初始化下拉刷新控件
        initPtrFrame();
    }

    @Override
    protected void initListener() {
        /**
         * 消息点击
         */
        mPtrListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "消息" + position);
            }
        });

        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListMessage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + DateHandleUtil.convertToStandard(new Date()));
                // 加载数据操作
                new GetDataTask().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mAdapter.getDataList().add(new Message("测试", "您的稿件“乖，摸摸头”状态已变更为“刊登”", "17:55", false));
                mAdapter.notifyDataSetChanged();
                mPtrListMessage.onRefreshComplete();
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mAdapter.getDataList().add(0, new Message("测试", "您的稿件“乖，摸摸头”状态已变更为“刊登”", "17:55", false));
            mAdapter.notifyDataSetChanged();
            mPtrListMessage.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    /**
     * 初始化消息列表
     */
    private void initMessageList() {
        List<Message> list = new ArrayList<>();
        list.add(new Message("系统消息", "您的稿件“乖，摸摸头”状态已变更为“刊登”", "17:55", false));
        list.add(new Message("系统消息", "您的稿件“乖，摸摸头”状态已变更为“录用”", "12/03", false));
        list.add(new Message("系统消息", "您的稿件“三体”状态已变更为“专家审核”", "12/01", false));
        list.add(new Message("系统消息", "您的稿件“三体”状态已变更为“专家审核”", "11/30", false));
        list.add(new Message("系统消息", "您的稿件“小王子”状态已变更为“编辑初审”", "11/29", true));
        list.add(new Message("系统消息", "您的稿件“小王子”状态已变更为“投稿”", "12/27", true));
        mAdapter = new MessageAdapter(getContext(), list);
        mPtrListMessage.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListMessage.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListMessage.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListMessage.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListMessage.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListMessage.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListMessage.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListMessage.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
    }
}
