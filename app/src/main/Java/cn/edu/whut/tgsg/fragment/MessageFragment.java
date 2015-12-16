package cn.edu.whut.tgsg.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.MessageAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.util.T;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 消息界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class MessageFragment extends BaseFragment {

    @Bind(R.id.list_message)
    ListView mListMessage;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    MessageAdapter mAdapter;

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
        mListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "消息" + position);
            }
        });

        /**
         * 下拉刷新
         */
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getDataList().add(0, new Message("测试", "您的稿件“乖，摸摸头”状态已变更为“刊登”", "17:55", false));
                        frame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
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
        mListMessage.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        final StoreHouseHeader header = new StoreHouseHeader(mContext);
        header.setPadding(0, 15, 0, 0);
        header.initWithString("loading...");
        header.setTextColor(getResources().getColor(R.color.primary));
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(false);
            }
        }, 100);
    }
}
