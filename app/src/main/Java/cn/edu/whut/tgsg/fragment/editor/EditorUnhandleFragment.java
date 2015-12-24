package cn.edu.whut.tgsg.fragment.editor;

import android.content.Context;
import android.os.AsyncTask;
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
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 编辑未受理稿件界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class EditorUnhandleFragment extends BaseFragment {

    @Bind(R.id.ptr_list_unhandle_manuscript)
    PullToRefreshListView mPtrListUnhandleManuscript;

    UnhandleManuscriptAdapter mAdapter;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    @Override
    protected String getTagName() {
        return "EditorUnhandleFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_unhandle;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化未受理稿件列表
        initUnhandleManuscriptList();
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
                // 向服务器发出第一次请求编辑未受理稿件
                requestServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mCurrentPage == mTotalPage) {
                    // 没有更多数据
                    new NoMoreDataTask().execute();
                } else {
                    // 向服务器发出请求下一页编辑未受理稿件
                    mCurrentPage++;
                    requestServer();
                }
            }
        });
    }

    /**
     * 初始化未受理稿件列表
     */
    private void initUnhandleManuscriptList() {
        mCurrentPage = 1;
        // 向服务器发出第一次请求编辑未受理稿件
        requestServer();
    }

    /**
     * 向服务器发出请求编辑未受理稿件
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "queryNotAcceptArticle")
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
                            // 将返回的json数组解析成List<ManuscriptVersion>
                            List<ManuscriptVersion> list = new Gson().fromJson(array.toString(), new TypeToken<List<ManuscriptVersion>>() {
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
     * 编辑受理稿件操作
     *
     * @param manuscriptVersion
     */
    private void handleManuscript(final ManuscriptVersion manuscriptVersion) {
        OkHttpUtils
                .post()
                .url(Constant.URL + "acceptArticle")
                .addParams("articleId", String.valueOf(manuscriptVersion.getArticle().getId()))
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
                            boolean isSuccess = serverInfo.getBoolean("message");
                            if (isSuccess) {
                                T.show(mContext, "受理稿件");
                                mAdapter.getDataList().remove(manuscriptVersion);
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 未受理稿件adapter
     */
    public class UnhandleManuscriptAdapter extends CommonAdapter<ManuscriptVersion> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public UnhandleManuscriptAdapter(Context context, List<ManuscriptVersion> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_editor_unhandle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ManuscriptVersion manuscriptVersion = mDataList.get(position);
            Manuscript manuscript = manuscriptVersion.getArticle();
            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
            viewHolder.mTvManuscriptUser.setText(manuscript.getContributor().getName());
            viewHolder.mTvManuscriptDate.setText(manuscript.getContributeTime());
            viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用编辑受理稿件操作
                    handleManuscript(manuscriptVersion);
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
            @Bind(R.id.tv_manuscript_user)
            TextView mTvManuscriptUser;
            @Bind(R.id.tv_manuscript_date)
            TextView mTvManuscriptDate;
            @Bind(R.id.btn_handle)
            Button mBtnHandle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
