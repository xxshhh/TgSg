package cn.edu.whut.tgsg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 分配专家
 * <p/>
 * Created by ylj on 2015-12-17.
 */
public class DistributeExpertActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_focus)
    TextView mTvFocus;
    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.btn_search)
    Button mBtnSearch;
    @Bind(R.id.edt_input)
    EditText mEdtInput;
    @Bind(R.id.ptr_list_distribute_expert)
    PullToRefreshListView mPtrListDistributeExpert;

    DistributeExpertAdapter mAdapter;

    ExamineManuscript mExamineManuscript;

    ManuscriptVersion mManuscriptVersion;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    String mType;
    String mTypeValue;

    @Override
    protected String getTagName() {
        return "DistributeExpertActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_distribute_expert;
    }

    @Override
    protected Context getContext() {
        return DistributeExpertActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的ExamineManuscript对象
        Bundle bundle = getIntent().getExtras();
        mExamineManuscript = (ExamineManuscript) bundle.getSerializable("examinemanuscript");
        mManuscriptVersion = mExamineManuscript.getArticleVersion();
        // 设置toolbar
        mToolbar.setTitle("分配专家");
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化状态下拉框
        initSpinnerState();
        // 初始化专家列表
        initExpertList();
        // 空布局获得焦点,防止自动弹出软键盘
        mTvFocus.requestFocus();
    }

    @Override
    protected void initListener() {
        /**
         * 查询按钮
         */
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinnerState.getSelectedItemPosition() == 0) {
                    T.show(mContext, "请选择查询条件");
                    return;
                }
                mType = StateTable.getDistributeExpertEngSpinner()[mSpinnerState.getSelectedItemPosition() - 1];
                String input = mEdtInput.getText().toString().trim();
                mTypeValue = TextUtils.isEmpty(input) ? "" : input;
                mCurrentPage = 1;
                // 向服务器第一次请求专家列表
                requestServer();
            }
        });

        /**
         * 下拉刷新 && 上拉加载
         */
        mPtrListDistributeExpert.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                mCurrentPage = 1;
                // 向服务器第一次请求专家列表
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
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getDistributeExpertSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
        mSpinnerState.setSelection(0);
    }

    /**
     * 初始化专家列表
     */
    private void initExpertList() {
        List<User> list = new ArrayList<>();
        mAdapter = new DistributeExpertAdapter(mContext, list);
        mPtrListDistributeExpert.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListDistributeExpert.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        mPtrListDistributeExpert.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListDistributeExpert.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListDistributeExpert.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
        // 上拉加载
        mPtrListDistributeExpert.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPtrListDistributeExpert.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPtrListDistributeExpert.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
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
            mPtrListDistributeExpert.onRefreshComplete();
            super.onPostExecute(s);
        }
    }

    /**
     * 向服务器请求专家列表
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "queryExpertByType")
                .addParams("currentPage", String.valueOf(mCurrentPage))
                .addParams("pageSize", String.valueOf(mPageSize))
                .addParams("type", mType)
                .addParams("typeValue", mTypeValue)
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
                            // 将返回的json数组解析成List<User>
                            List<User> list = new Gson().fromJson(array.toString(), new TypeToken<List<User>>() {
                            }.getType());
                            if (mCurrentPage == 1) {// 第一次请求（或下拉刷新）
                                mAdapter = new DistributeExpertAdapter(mContext, list);
                                mPtrListDistributeExpert.setAdapter(mAdapter);
                            } else {// 上拉加载
                                mAdapter.getDataList().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            // 完成数据加载
                            mPtrListDistributeExpert.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListDistributeExpert.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 调用分配专家操作
     *
     * @param expert
     */
    private void distributeExpert(final User expert) {
        OkHttpUtils
                .post()
                .url(Constant.URL + "distributeArticle")
                .addParams("articleId", String.valueOf(mManuscriptVersion.getArticle().getId()))
                .addParams("expertId", String.valueOf(expert.getId()))
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
                                T.show(mContext, "分配成功");
                                // 返回分配专家信息
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("isDistributeExpert", isSuccess);
                                DistributeExpertActivity.this.setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 分配专家adapter
     */
    public class DistributeExpertAdapter extends CommonAdapter<User> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public DistributeExpertAdapter(Context context, List<User> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_distribute_expert, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final User expert = mDataList.get(position);
            viewHolder.mTvExpertName.setText(expert.getName());
            viewHolder.mTvExpertMajor.setText(expert.getProfessional());
            viewHolder.mTvExpertResearchDirection.setText(expert.getResearch());
            viewHolder.mBtnDistribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用分配专家操作
                    distributeExpert(expert);
                }
            });
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_distribute_expert.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_expert_name)
            TextView mTvExpertName;
            @Bind(R.id.tv_expert_major)
            TextView mTvExpertMajor;
            @Bind(R.id.tv_expert_research_direction)
            TextView mTvExpertResearchDirection;
            @Bind(R.id.btn_distribute)
            Button mBtnDistribute;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
