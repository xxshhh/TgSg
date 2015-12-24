package cn.edu.whut.tgsg.fragment.editor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.DistributeExpertActivity;
import cn.edu.whut.tgsg.activity.ExamineManuscriptActivity;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 编辑已受理稿件界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class EditorHandleFragment extends BaseFragment {

    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.ptr_list_handle_manuscript)
    PullToRefreshListView mPtrListHandleManuscript;

    HandleManuscriptAdapter mAdapter;

    int mPageSize = 5;

    int mCurrentPage = 1;
    int mTotalPage = 1;

    // 稿件状态
    int mState = -1;

    private static final int REQUEST_CODE_EXAMINE_MANUSCRIPT = 1;//获取审稿信息
    private static final int REQUEST_CODE_DISTRIBUTE_EXPERT = 2;//获取分配专家信息

    @Override
    protected String getTagName() {
        return "EditorHandleFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_handle;
    }

    @Override
    protected void initData() {
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化状态下拉框
        initSpinnerState();
        // 初始化已处理稿件列表
        initHandleManuscriptList();
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
         * 状态下拉框
         */
        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // 编辑初审中
                        mState = 2;
                        break;
                    case 1:
                        // 待分配专家
                        mState = 3;
                        break;
                    case 2:
                        // 专家审核中
                        mState = 4;
                        break;
                    case 3:
                        // 编辑复审中
                        mState = 5;
                        break;
                    case 4:
                        // 已通过
                        mState = 6;
                        break;
                    case 5:
                        // 已录用
                        mState = 8;
                        break;
                    default:
                        mState = -1;
                }
                if (mState == -1) {
                    if (mAdapter != null) {
                        mAdapter.getDataList().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    mCurrentPage = 1;
                    // 向服务器发出第一次请求编辑所受理稿件（根据状态）
                    requestServer();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                if (mState != -1) {
                    mCurrentPage = 1;
                    // 向服务器发出第一次请求编辑所受理稿件（根据状态）
                    requestServer();
                }
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
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getEditorStateSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    /**
     * 初始化已处理稿件列表
     */
    private void initHandleManuscriptList() {
        List<ExamineManuscript> list = new ArrayList<>();
        mAdapter = new HandleManuscriptAdapter(mContext, list);
        mPtrListHandleManuscript.setAdapter(mAdapter);
    }

    /**
     * 向服务器发出请求编辑所受理稿件（根据状态）
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "queryAllArticleByEditor")
                .addParams("state", String.valueOf(mState))
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
                            // 将返回的json数组解析成List<ExamineManuscript>
                            List<ExamineManuscript> list = new Gson().fromJson(array.toString(), new TypeToken<List<ExamineManuscript>>() {
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
     * 编辑操作稿件
     *
     * @param examineManuscript
     */
    private void handleManuscript(final ExamineManuscript examineManuscript) {
        Intent intent;
        Bundle bundle;
        final ManuscriptVersion manuscriptVersion = examineManuscript.getArticleVersion();
        switch (manuscriptVersion.getArticle().getState()) {
            case 2:
                // 初审中
                intent = new Intent(mContext, ExamineManuscriptActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", manuscriptVersion);
                bundle.putBoolean("isFirst", true);
                intent.putExtras(bundle);
                startActivity(intent);
            case 3:
                // 待分配专家
                intent = new Intent(mContext, DistributeExpertActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", manuscriptVersion);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 5:
                // 复审中
                intent = new Intent(mContext, ExamineManuscriptActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("manuscriptversion", manuscriptVersion);
                bundle.putBoolean("isFirst", false);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 6:
                // 录用通过的稿件
                new MaterialDialog.Builder(mContext)
                        .title("录用稿件")
                        .content("真的要录用稿件吗？")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();
                                // 向服务器请求录用稿件
                                OkHttpUtils
                                        .post()
                                        .url(Constant.URL + "employeeArticle")
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
                                                        T.show(mContext, "稿件已录用");
                                                        mAdapter.getDataList().remove(examineManuscript);
                                                        mAdapter.notifyDataSetChanged();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                            }
                        }).show();
                break;
            default:
        }
    }

    /**
     * 已受理稿件adapter
     */
    public class HandleManuscriptAdapter extends CommonAdapter<ExamineManuscript> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public HandleManuscriptAdapter(Context context, List<ExamineManuscript> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_editor_handle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ExamineManuscript examineManuscript = mDataList.get(position);
            ManuscriptVersion manuscriptVersion = examineManuscript.getArticleVersion();
            Manuscript manuscript = manuscriptVersion.getArticle();
            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
            viewHolder.mTvManuscriptUser.setText(manuscript.getContributor().getName());
            viewHolder.mTvManuscriptState.setText(StateTable.getString(manuscript.getState()));
            switch (manuscript.getState()) {
                case 2:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("审稿");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(examineManuscript);
                        }
                    });
                    break;
                case 3:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("分配专家");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(examineManuscript);
                        }
                    });
                    break;
                case 4:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
                    break;
                case 5:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("审稿");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(examineManuscript);
                        }
                    });
                    break;
                case 6:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("录用");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(examineManuscript);
                        }
                    });
                    break;
                case 8:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
                    break;
                default:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
            }
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
            @Bind(R.id.tv_manuscript_user)
            TextView mTvManuscriptUser;
            @Bind(R.id.tv_manuscript_state)
            TextView mTvManuscriptState;
            @Bind(R.id.btn_handle)
            Button mBtnHandle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
