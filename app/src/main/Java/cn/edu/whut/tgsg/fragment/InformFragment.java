package cn.edu.whut.tgsg.fragment;

import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.InformAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Inform;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 通知界面
 * <p/>
 * Created by ylj on 2015/12/25.
 */
public class InformFragment extends BaseFragment {

    @Bind(R.id.ptr_list_inform)
    PullToRefreshListView mPtrListInform;

    InformAdapter mAdapter;

    int mPageSize = 10;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    @Override
    protected String getTagName() {
        return "InformFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_inform;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化消息列表
        initInformList();
    }

    @Override
    protected void initListener() {
        /**
         * 通知点击
         */
        mPtrListInform.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Inform inform = mAdapter.getDataList().get(position);
                String content = inform.getContent().replaceAll("<.+?>", " ");
                // 设置标题位置颜色
                int first = content.indexOf(" ");
                int last = content.lastIndexOf(" ");
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary)), first + 1, last, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                new MaterialDialog.Builder(mContext)
                        .title("通知")
                        .content(spannableStringBuilder)
                        .positiveText(R.string.agree)
                        .show();

                OkHttpUtils
                        .post()
                        .url(Constant.URL + "updateInform")
                        .addParams("informId", String.valueOf(inform.getId()))
                        .addParams("source", "android")
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
                                try {
                                    JSONObject serverInfo = new JSONObject(response);
                                    T.show(mContext, "查看通知");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                mAdapter.getDataList().get(position).setRead(1);
                mAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListInform.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器发出请求第一页消息列表
                requestServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mCurrentPage == mTotalPage) {
                    // 没有更多数据
                    new NoMoreDataTask().execute();
                } else {
                    // 向服务器发出请求下一页消息列表
                    mCurrentPage++;
                    requestServer();
                }
            }
        });
    }

    /**
     * 初始化消息列表
     */
    private void initInformList() {
        mCurrentPage = 1;
        // 向服务器发出请求第一页消息列表
        requestServer();
    }

    /**
     * 向服务器请求系统通知
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "findInform")
                .addParams("curPage", String.valueOf(mCurrentPage))
                .addParams("pageSize", String.valueOf(mPageSize))
                .addParams("source", "android")
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
                        try {
                            JSONObject serverInfo = new JSONObject(response);
                            JSONObject data = serverInfo.getJSONObject("data");
                            mTotalPage = data.getInt("totalPage");
                            JSONArray pageList = data.getJSONArray("pageList");
                            // 将返回的json数组解析成List<Inform>
                            List<Inform> list = new Gson().fromJson(pageList.toString(), new TypeToken<List<Inform>>() {
                            }.getType());
                            if (mCurrentPage == 1) {// 第一次请求（或下拉刷新）
                                mAdapter = new InformAdapter(mContext, list);
                                mPtrListInform.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListInform.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListInform.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListInform.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListInform.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListInform.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListInform.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListInform.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListInform.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListInform.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
    }

    /**
     * 没有更多数据
     */
    private class NoMoreDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return "没有更多数据";
        }

        @Override
        protected void onPostExecute(String s) {
            T.show(mContext, s);
            mPtrListInform.onRefreshComplete();
            super.onPostExecute(s);
        }
    }
}
