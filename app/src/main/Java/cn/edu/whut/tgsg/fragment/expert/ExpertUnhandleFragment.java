package cn.edu.whut.tgsg.fragment.expert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import cn.edu.whut.tgsg.activity.ExamineManuscriptActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.DistributeExpert;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 专家未审核稿件界面
 * <p/>
 * Created by ylj on 2015/12/15.
 */
public class ExpertUnhandleFragment extends BaseFragment {

    @Bind(R.id.ptr_list_unhandle_manuscript)
    PullToRefreshListView mPtrListUnhandleManuscript;

    UnhandleManuscriptAdapter mAdapter;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    private static final int REQUEST_CODE_EXAMINE_MANUSCRIPT = 1;//获取审稿信息

    @Override
    protected String getTagName() {
        return "ExpertUnhandleFragment";
    }

    @Override

    protected int getContentLayoutId() {
        return R.layout.fragment_expert_unhandle;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化未处理稿件列表
        initManuscriptList();
    }

    @Override
    protected void initListener() {
        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListUnhandleManuscript.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器第一次请求专家未处理列表
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
     * 初始化未处理稿件列表
     */
    private void initManuscriptList() {
        mCurrentPage = 1;
        // 向服务器第一次请求专家未处理稿件
        requestServer();
    }

    /**
     * 向服务器请求专家未处理稿件
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "queryAuditingArticle")
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
                            // 将返回的json数组解析成List<DistributeExpert>
                            List<DistributeExpert> list = new Gson().fromJson(array.toString(), new TypeToken<List<DistributeExpert>>() {
                            }.getType());
                            if (mCurrentPage == 1) {// 第一次请求（或下拉刷新）
                                mAdapter = new UnhandleManuscriptAdapter(mContext, list);
                                mPtrListUnhandleManuscript.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListUnhandleManuscript.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListUnhandleManuscript.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListUnhandleManuscript.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListUnhandleManuscript.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
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
            mPtrListUnhandleManuscript.onRefreshComplete();
            super.onPostExecute(s);
        }
    }

    /**
     * 专家审稿操作
     *
     * @param distributeExpert
     */
    public void handleManuscript(DistributeExpert distributeExpert) {
        Intent intent = new Intent(mContext, ExamineManuscriptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("distributeexpert", distributeExpert);
        bundle.putInt("isFlag", 3);
        intent.putExtras(bundle);
        getParentFragment().startActivityForResult(intent, REQUEST_CODE_EXAMINE_MANUSCRIPT);
    }

    /**
     * 未审核稿件adapter（审稿表）
     */
    public class UnhandleManuscriptAdapter extends CommonAdapter<DistributeExpert> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public UnhandleManuscriptAdapter(Context context, List<DistributeExpert> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_expert_unhandle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final DistributeExpert distributeExpert = mDataList.get(position);
            ManuscriptVersion manuscriptVersion = distributeExpert.getArticleVersion();
            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
            viewHolder.mTvManuscriptDate.setText(distributeExpert.getDistributeTime());
            viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用专家审稿操作
                    handleManuscript(distributeExpert);
                }
            });
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_unhandle_manuscript.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_manuscript_title)
            TextView mTvManuscriptTitle;
            @Bind(R.id.tv_manuscript_date)
            TextView mTvManuscriptDate;
            @Bind(R.id.btn_handle)
            Button mBtnHandle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_EXAMINE_MANUSCRIPT) {
                if (data.getExtras().getBoolean("isExamineManuscript")) {
                    mCurrentPage = 1;
                    requestServer();
                }
            }
        }
    }
}
