package cn.edu.whut.tgsg.fragment.expert;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.DistributeExpert;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 专家已审核稿件界面
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class ExpertHandleFragment extends BaseFragment {

    @Bind(R.id.ptr_list_handle_manuscript)
    PullToRefreshListView mPtrListHandleManuscript;

    HandleManuscriptAdapter mAdapter;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    @Override
    protected String getTagName() {
        return "ExpertHandleFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_expert_handle;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化未受理稿件列表
        initManuscriptList();
    }

    @Override
    protected void initListener() {
        /**
         * 稿件点击
         */
        mPtrListHandleManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Intent intent = new Intent(mContext, ManuscriptDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", mAdapter.getItem(position).getArticleVersion());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListHandleManuscript.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器第一次请求专家已处理列表
                requestServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mCurrentPage == mTotalPage) {
                    // 没有更多数据
                    new NoMoreDataTask().execute();
                } else {
                    // 向服务器请求下一页专家已处理列表
                    mCurrentPage++;
                    requestServer();
                }
            }
        });
    }

    /**
     * 初始化已处理稿件列表
     */
    private void initManuscriptList() {
        mCurrentPage = 1;
        // 向服务器第一次请求专家已处理稿件
        requestServer();
    }

    /**
     * 向服务器请求专家已处理稿件
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "queryAuditedArticle")
                .addParams("currentPage", String.valueOf(mCurrentPage))
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
                            JSONArray array = data.getJSONArray("pageList");
                            Log.e(getTagName(), array.toString());
                            // 将返回的json数组解析成List<s>
                            List<DistributeExpert> list = new Gson().fromJson(array.toString(), new TypeToken<List<DistributeExpert>>() {
                            }.getType());
                            if (mCurrentPage == 1) {// 第一次请求（或下拉刷新）
                                mAdapter = new HandleManuscriptAdapter(mContext, list);
                                mPtrListHandleManuscript.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListHandleManuscript.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListHandleManuscript.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListHandleManuscript.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListHandleManuscript.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListHandleManuscript.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListHandleManuscript.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListHandleManuscript.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListHandleManuscript.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListHandleManuscript.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
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
            mPtrListHandleManuscript.onRefreshComplete();
            super.onPostExecute(s);
        }
    }

    /**
     * 已审核稿件adapter（审稿表）
     */
    public class HandleManuscriptAdapter extends CommonAdapter<DistributeExpert> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public HandleManuscriptAdapter(Context context, List<DistributeExpert> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_expert_handle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DistributeExpert distributeExpert = mDataList.get(position);
            ManuscriptVersion manuscriptVersion = distributeExpert.getArticleVersion();
            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
            viewHolder.mTvManuscriptDate.setText(distributeExpert.getDistributeTime());
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_handle_manuscript.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_manuscript_title)
            TextView mTvManuscriptTitle;
            @Bind(R.id.tv_manuscript_date)
            TextView mTvManuscriptDate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
