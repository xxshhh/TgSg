package cn.edu.whut.tgsg.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ContributeManuscriptActivity;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.HistoryVersionAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 历史版本界面
 * <p/>
 * Created by xwh on 2015/12/14.
 */
public class HistoryVersionFragment extends BaseFragment {

    @Bind(R.id.ptr_list_historyVersion)
    PullToRefreshListView mPtrListHistoryVersion;
    @Bind(R.id.btn_upload)
    Button mBtnUpload;

    Manuscript mManuscript;
    ManuscriptVersion mManuscriptVersion;

    HistoryVersionAdapter mAdapter;

    int mPageSize = 10;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    private static final int REQUEST_CODE_CONTRIBUTE_NEW_MANUSCRIPT = 1;//获取上传新版本信息

    @Override
    protected String getTagName() {
        return "HistoryVersionFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_historyversion;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件版本对象
        mManuscriptVersion = ((ManuscriptDetailActivity) getActivity()).getManuscriptVersion();
        mManuscript = mManuscriptVersion.getArticle();
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化历史版本列表
        initHistoryVersionList();
        // 设置底部按钮
        setupButton();
    }

    @Override
    protected void initListener() {
        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListHistoryVersion.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器发出第一次请求稿件版本
                requestServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mCurrentPage == mTotalPage) {
                    // 没有更多数据
                    new NoMoreDataTask().execute();
                } else {
                    // 向服务器发出请求下一页稿件版本
                    mCurrentPage++;
                    requestServer();
                }
            }
        });
    }

    /**
     * 初始化历史版本列表
     */
    private void initHistoryVersionList() {
        mCurrentPage = 1;
        // 向服务器发出第一次请求稿件版本
        requestServer();
    }

    /**
     * 向服务器发出请求稿件版本
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "findArticleVersionAll")
                .addParams("articleId", String.valueOf(mManuscript.getId()))
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
                                mAdapter = new HistoryVersionAdapter(mContext, list);
                                mPtrListHistoryVersion.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListHistoryVersion.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListHistoryVersion.onRefreshComplete();
                        }
                    }
                });
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
                            Intent intent = new Intent(mContext, ContributeManuscriptActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("articleId", mManuscript.getId());
                            intent.putExtras(bundle);
                            startActivityForResult(intent, REQUEST_CODE_CONTRIBUTE_NEW_MANUSCRIPT);
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

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListHistoryVersion.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListHistoryVersion.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListHistoryVersion.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListHistoryVersion.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListHistoryVersion.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListHistoryVersion.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListHistoryVersion.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
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
            mPtrListHistoryVersion.onRefreshComplete();
            super.onPostExecute(s);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CONTRIBUTE_NEW_MANUSCRIPT) {
                if (data.getExtras().getBoolean("isContributeNewManuscript")) {
                    // 更新历史版本列表
                    mCurrentPage = 1;
                    // 向服务器发出请求稿件版本
                    requestServer();
                    // 返回上传新版本信息
                    Intent intent = ((ManuscriptDetailActivity) getActivity()).getReturnIntent();
                    intent.putExtra("isContributeNewManuscript", true);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                }
            }
        }
    }
}
