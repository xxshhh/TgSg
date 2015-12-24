package cn.edu.whut.tgsg.fragment.author;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import cn.edu.whut.tgsg.activity.ContributeManuscriptActivity;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.ManuscriptAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 作者稿件界面
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class AuthorManuscriptFragment extends BaseFragment {

    @Bind(R.id.ptr_list_manuscript)
    PullToRefreshListView mPtrListManuscript;
    @Bind(R.id.btn_add)
    FloatingActionButton mBtnAdd;

    ManuscriptAdapter mAdapter;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    private static final int REQUEST_CODE_CONTRIBUTE_MANUSCRIPT = 1;//获取投稿信息
    private static final int REQUEST_CODE_UPDATE_MANUSCRIPT = 2;//获取更新稿件信息

    @Override
    protected String getTagName() {
        return "AuthorManuscriptFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_author_manuscript;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化稿件列表
        initManuscriptList();
    }

    @Override
    protected void initListener() {
        /**
         * 稿件点击
         */
        mPtrListManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Intent intent = new Intent(mContext, ManuscriptDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", mAdapter.getItem(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_MANUSCRIPT);
            }
        });

        /**
         * 我要投稿
         */
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, "我要投稿！！！");
                Intent intent = new Intent(mContext, ContributeManuscriptActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CONTRIBUTE_MANUSCRIPT);
            }
        });

        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListManuscript.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器发出请求作者稿件
                requestServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mCurrentPage == mTotalPage) {
                    // 没有更多数据
                    new NoMoreDataTask().execute();
                } else {
                    // 向服务器发出请求下一页作者稿件
                    mCurrentPage++;
                    requestServer();
                }
            }
        });
    }

    /**
     * 初始化稿件列表
     */
    private void initManuscriptList() {
        mCurrentPage = 1;
        // 向服务器发出第一次请求作者稿件
        requestServer();
    }

    /**
     * 向服务器发出请求作者稿件
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "findArticleAll")
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
                            JSONArray array = data.getJSONArray("pageList");
                            Log.e(getTagName(), array.toString());
                            // 将返回的json数组解析成List<ManuscriptVersion>
                            List<ManuscriptVersion> list = new Gson().fromJson(array.toString(), new TypeToken<List<ManuscriptVersion>>() {
                            }.getType());
                            if (mCurrentPage == 1) {// 第一次请求（或下拉刷新）
                                mAdapter = new ManuscriptAdapter(mContext, list);
                                mPtrListManuscript.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListManuscript.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListManuscript.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListManuscript.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListManuscript.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListManuscript.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListManuscript.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListManuscript.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListManuscript.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListManuscript.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
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
            mPtrListManuscript.onRefreshComplete();
            super.onPostExecute(s);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CONTRIBUTE_MANUSCRIPT) {
                if (data.getExtras().getBoolean("isContributeManuscript")) {
                    // 更新稿件列表
                    mCurrentPage = 1;
                    // 向服务器发出第一次请求作者稿件
                    requestServer();
                }
            } else if (requestCode == REQUEST_CODE_UPDATE_MANUSCRIPT) {
                if (data.getExtras().getBoolean("isCancelManuscript")
                        || data.getExtras().getBoolean("isContributeNewManuscript")) {
                    // 更新稿件列表
                    mCurrentPage = 1;
                    // 向服务器发出第一次请求作者稿件
                    requestServer();
                }
            }
        }
    }
}
