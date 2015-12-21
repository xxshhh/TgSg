package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * 个人信息界面
 * <p/>
 * Created by ylj on 2015-12-14.
 */
public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edt_username)
    TextView mEdtUsername;
    @Bind(R.id.edt_email)
    TextView mEdtEmail;
    @Bind(R.id.edt_psw)
    TextView mEdtPsw;
    @Bind(R.id.edt_age)
    TextView mEdtAge;
    @Bind(R.id.edt_tel)
    TextView mEdtTel;
    @Bind(R.id.edt_degree)
    TextView mEdtDegree;
    @Bind(R.id.edt_major)
    TextView mEdtMajor;
    @Bind(R.id.edt_research_direction)
    TextView mEdtResearchDirection;
    @Bind(R.id.edt_work_place)
    TextView mEdtWorkPlace;
    @Bind(R.id.edt_desc)
    TextView mEdtDesc;

    private Handler mHandler;
    private String jsonString = "{\"authorId\":1,\"username\":\"小杨\"," +
            "\"email\":\"110@163.com\",\"tel\":\"5511952\"," +
            "\"edubkg\":\"大学\",\"desc\":\"我有一头小毛驴，从来也不骑\"}";
    private User user = new User();//初始化

    @Override
    protected String getTagName() {
        return "PersonInfoActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personinfo;
    }

    @Override
    protected Context getContext() {
        return PersonInfoActivity.this;
    }

    @Override
    protected void initData() {
        Gson gson = new Gson();
        user = gson.fromJson(jsonString, User.class);
        //初始化个人信息界面
        mEdtUsername.setText(user.getName());
        mEdtEmail.setText(user.getEmail());
        mEdtPsw.setText(user.getPassword());
        mEdtAge.setText(user.getAge() + "");
        mEdtTel.setText(user.getPhone());
        mEdtDegree.setText(user.getEducation());
        mEdtMajor.setText(user.getProfessional());
        mEdtResearchDirection.setText(user.getResearch());
        mEdtWorkPlace.setText(user.getWork());
        mEdtDesc.setText(user.getPersonal());
        /**
         * 处理消息
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.HTTP_ACCESS_ERROR:
                        T.show(mContext, "网络访问错误");
                        break;
                    case Constant.GETJSON_SUCCEED:

                    case Constant.FAILED:
                        T.show(mContext, "修改失败");
                        break;
                    case Constant.SUCCEED:
                        T.show(mContext, "修改成功。");
                        break;
                }
            }
        };
    }

    @Override
    protected void initListener() {
        mEdtUsername.setOnClickListener(this);
        mEdtEmail.setOnClickListener(this);
        mEdtPsw.setOnClickListener(this);
        mEdtAge.setOnClickListener(this);
        mEdtTel.setOnClickListener(this);
        mEdtDegree.setOnClickListener(this);
        mEdtMajor.setOnClickListener(this);
        mEdtResearchDirection.setOnClickListener(this);
        mEdtWorkPlace.setOnClickListener(this);
        mEdtDesc.setOnClickListener(this);
    }


    /**
     * 将用户修改的信息发送到服务器，保存修改
     *
     * @param tagName
     * @param chgedText
     */
    private void requestServer(String tagName, String chgedText) {
        RequestBody ChgedPersonInfoFormBody = new FormEncodingBuilder()
                .add(tagName, chgedText)
                .build();
        Request request = new Request.Builder().url(Constant.SAVE_PERSONINFO_URL).post(ChgedPersonInfoFormBody).build();
//        OkHttpUtil.enqueue(request, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
////                mHandler.sendEmptyMessage(Constant.SUCCEED);
//                mHandler.sendEmptyMessage(Constant.HTTP_ACCESS_ERROR);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                mHandler.sendEmptyMessage(Constant.SUCCEED);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//10个字段
            case R.id.edt_psw:
                showMyDialog(v.getId(), R.layout.dialog_chg_psw);//以该自定义布局显示对话框
                break;
            case R.id.edt_degree:
                break;
            default:
                showMyDialog(v.getId(), R.layout.dialog_chg_personinfo);//以该自定义布局显示对话框
                break;
        }
    }

    /**
     * 显示自定义的对话框
     *
     * @param viewId
     * @param layoutId
     */
    private void showMyDialog(final int viewId, int layoutId) {
        String tagName = "";
        String title = "";
        switch (viewId) {//10个字段
            case R.id.edt_email:
                tagName = "email";
                title = "邮箱";
                break;
            case R.id.edt_username:
                tagName = "username";
                title = "姓名";
                break;
            case R.id.edt_age:
                tagName = "age";
                title = "年龄";
                break;
            case R.id.edt_tel:
                tagName = "tel";
                title = "电话";
                break;
            case R.id.edt_major:
                tagName = "major";
                title = "专业";
                break;
            case R.id.edt_research_direction:
                tagName = "researchDirection";
                title = "研究方向";
                break;
            case R.id.edt_work_place:
                tagName = "workPlace";
                title = "工作单位";
                break;
            case R.id.edt_desc:
                tagName = "desc";
                title = "个人简介";
                break;
            case R.id.edt_psw:
                tagName = "password";
                title = "密码";
                break;
            case R.id.edt_degree:
                tagName = "degree";
                title = "学历";
                break;
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final RelativeLayout layout = (RelativeLayout) inflater.inflate(layoutId, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

        dialog.setTitle(title);
        dialog.setView(layout);
        dialog.setCancelable(false);
        dialog.show();
        Button btnOk = (Button) layout.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) layout.findViewById(R.id.btn_cancel);
        final EditText editText = (EditText) layout.findViewById(R.id.edt_input);
        final String finalTagName = tagName;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                requestServer(finalTagName, editText.getText().toString());
                T.show(mContext, editText.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                T.show(mContext, "Cancel");
            }
        });

    }


}
