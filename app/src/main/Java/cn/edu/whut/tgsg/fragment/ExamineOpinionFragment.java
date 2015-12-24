package cn.edu.whut.tgsg.fragment;

import android.util.Log;
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
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.ExamineOpinionAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;

/**
 * 审稿意见
 * <p/>
 * Created by xwh on 2015/12/20.
 */
public class ExamineOpinionFragment extends BaseFragment {

    @Bind(R.id.ptr_list_examineOpinion)
    PullToRefreshListView mPtrListExamineopinion;

    ManuscriptVersion mManuscriptVersion;

    ExamineOpinionAdapter mAdapter;

    @Override
    protected String getTagName() {
        return "ExamineOpinionFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_examineopinion;
    }

    @Override
    protected void initData() {
        // 获取Activity的ManuscriptVersion对象
        mManuscriptVersion = ((ManuscriptDetailActivity) getActivity()).getManuscriptVersion();
        // 初始化下拉刷新控件
        initPtrFrame();
        // 初始化审稿意见列表
        initExamineOpinionList();
    }

    @Override
    protected void initListener() {
        /**
         * 下拉刷新
         */
        mPtrListExamineopinion.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // 设置上一次刷新的提示标签
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("最后更新时间：" + DateHandleUtil.convertToStandard(new Date()));
                // 向服务器发出请求审稿意见
                requestServer();
            }
        });
    }

    /**
     * 初始化审稿意见列表
     */
    private void initExamineOpinionList() {
        // 向服务器发出请求审稿意见
        requestServer();
    }

    /**
     * 向服务器发出请求审稿意见
     */
    private void requestServer() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "getAuditingDes")
                .addParams("articleId", String.valueOf(mManuscriptVersion.getArticle().getId()))
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
                            JSONArray array = serverInfo.getJSONArray("data");
                            Log.e(getTagName(), array.toString());
                            // 将返回的json数组解析成List<ExamineManuscript>
                            List<ExamineManuscript> list = new Gson().fromJson(array.toString(), new TypeToken<List<ExamineManuscript>>() {
                            }.getType());
                            // 根据不同的角色(编辑和专家)查找不同的审稿意见列表
                            switch (MyApplication.GLOBAL_USER.getRole().getId()) {
                                case 2://编辑
                                    break;
                                case 4://专家
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).getUserInfo().getId() != MyApplication.GLOBAL_USER.getId()) {
                                            list.remove(i);
                                            i--;
                                        }
                                    }
                                    break;
                                default:
                            }
                            // 下拉刷新
                            mAdapter = new ExamineOpinionAdapter(mContext, list);
                            mPtrListExamineopinion.setAdapter(mAdapter);
                            // 完成数据加载
                            mPtrListExamineopinion.onRefreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            T.show(mContext, "没有更多数据");
                            // 完成数据加载
                            mPtrListExamineopinion.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        // 模式
        mPtrListExamineopinion.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // 下拉刷新
        mPtrListExamineopinion.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPtrListExamineopinion.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPtrListExamineopinion.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
    }
}
