package cn.edu.whut.tgsg.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.MessageAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * 留言消息
 * <p/>
 * Created by xwh on 2015/12/27.
 */
public class MessageFragment extends BaseFragment {

    @Bind(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @Bind(R.id.edt_input_msg)
    EditText mEdtInputMsg;
    @Bind(R.id.list_message)
    ListView mListMessage;

    MessageAdapter mAdapter;

    ManuscriptVersion mManuscriptVersion;

    User mReceiver;

    @Override
    protected String getTagName() {
        return "MessageFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件版本对象
        mManuscriptVersion = ((ManuscriptDetailActivity) getActivity()).getManuscriptVersion();
        // 初始化留言消息列表
        initMessageList();
    }

    @Override
    protected void initListener() {
        /**
         * 发送留言
         */
        mBtnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReceiver == null || mManuscriptVersion.getArticle().getState() == 0) {
                    T.show(mContext, "不能发送留言。");
                    mEdtInputMsg.setText("");
                    return;
                }
                String inputStr = mEdtInputMsg.getText().toString().trim();
                if (TextUtils.isEmpty(inputStr)) {
                    T.show(mContext, "输入不能为空");
                    return;
                }
                // 发送消息
                requestServer(inputStr);
            }
        });
    }

    /**
     * 初始化留言消息列表
     */
    private void initMessageList() {
        OkHttpUtils
                .post()
                .url(Constant.URL + "findArticleDes")
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
                            JSONArray array = serverInfo.getJSONArray("messageList");
                            Log.e(getTagName(), array.toString());
                            // 将返回的json数组解析成List<Message>
                            List<Message> list = new Gson().fromJson(array.toString(), new TypeToken<List<Message>>() {
                            }.getType());
                            mReceiver = list.get(0).getSender().getId() == MyApplication.GLOBAL_USER.getId() ?
                                    list.get(0).getReceiver() : list.get(0).getSender();
                            mAdapter = new MessageAdapter(mContext, list);
                            mListMessage.setAdapter(mAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 发送消息
     */
    private void requestServer(final String content) {
        String requestStr = "insertMessageToEditor";
        if (MyApplication.GLOBAL_USER.getRole().getId() == 2) {
            requestStr = "insertMessageToAuthor";
        }
        OkHttpUtils
                .post()
                .url(Constant.URL + requestStr)
                .addParams("articleId", String.valueOf(mManuscriptVersion.getArticle().getId()))
                .addParams("receiverId", String.valueOf(mReceiver.getId()))
                .addParams("content", content)
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
                            boolean isSuccess = serverInfo.getBoolean("result");
                            if (isSuccess) {
                                mEdtInputMsg.setText("");
                                // 初始化留言消息列表
                                initMessageList();
                                mListMessage.setSelection(mListMessage.getBottom());
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
