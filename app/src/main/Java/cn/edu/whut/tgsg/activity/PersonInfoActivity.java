package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.bean.Author;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.OkHttpUtil;
import cn.edu.whut.tgsg.util.T;
import cn.edu.whut.tgsg.widget.PullScrollView;

/**
 * Created by ylj on 2015-12-05.
 */
public class PersonInfoActivity extends BaseActivity implements PullScrollView.OnTurnListener {

    @Bind(R.id.background_img)
    ImageView mBackgroundImg;
    @Bind(R.id.table_layout)
    TableLayout mTableLayout;
    @Bind(R.id.scroll_view)
    PullScrollView mScrollView;
    @Bind(R.id.change_psw)
    TextView mChangePsw;


    private String mUsernameStr ="";//得到用户名
    private Handler mHandler;

    private Author author = new Author();//初始化
    //保存服务器返回的json字符串
    public String jsonString = "{\"authorId\":1,\"username\":\"小杨\"," +
            "\"email\":\"110@163.com\",\"tel\":\"5511952\"," +
            "\"edubkg\":\"大学\",\"desc\":\"我有一头小毛驴，从来也不骑\"}";


    /**
     * 继承的抽象方法
     *
     * @return
     */
//    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personinfo;
    }

    @Override
    protected Context getContext() {
        return PersonInfoActivity.this;
    }

    @Override
    protected void initListener() {

        mScrollView.setOnTurnListener(this);//实现接口
        mChangePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext,"修改密码");
            }
        });

    }

    @Override
    protected void initData() {

        mScrollView.setHeader(mBackgroundImg);

        //得到用户名，用于查询
        Intent intent = getIntent();
        mUsernameStr = intent.getStringExtra("username");


        //与服务器交互，获取服务器返回的json数据，存储在jsonString中
        getData(mUsernameStr);//发出消息

        /**
         * 处理消息
         */

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.SUCCEED:
                        //将json字符串解析成实体
                        dealJson(jsonString);

                        //初始化控件
//                        initView();
                        //初始化listView
//                        showListView();

                        showTable();
                        T.show(mContext,"个人信息加载成功");
                        break;
                    case Constant.NET_ACCESS_ERROR:
//                        initView();
                        T.show(mContext, "网络访问错误！！！");
                        break;
                    case Constant.FAILED:
                        break;
                }
            }
        };

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 用username请求服务器，得到服务器的响应的json字符串
     */
    private void getData(String username) {

        //---2，声明，初始化，实例化请求,http--post提交键值对
        RequestBody personCenterFormBody = new FormEncodingBuilder()
                .add("username", username)//通过账号查询用户（账号唯一）
                .build();
        Request request = new Request.Builder().url(Constant.GETPERSONINFO_URL)
                .post(personCenterFormBody)
                .build();//实例化请求
        OkHttpUtil.enqueue(request, new Callback() {//执行请求
            @Override
            public void onFailure(Request request, IOException e) {

//                mHandler.sendEmptyMessage(NET_ACCESS_ERROR);
                mHandler.sendEmptyMessage(Constant.SUCCEED);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                //请求成功，服务器返回用户的相关个人信息
                /*
                服务器的响应
                1.把响应转化成String字符串
                2，把响应转化成流Reader result类型的（数据量大时）
                 */
                jsonString = response.body().string();//响应流
                mHandler.sendEmptyMessage(Constant.SUCCEED);
            }
        });

    }


    /**
     * 将json字符串解析成实体
     *
     * @param str
     */
    public void dealJson(String str) {

        //1，创建一个实体类
        //2,将json解析成实体
        Gson gson = new Gson();//声明+实例化
        author = gson.fromJson(str, Author.class);
    }


    /**
     * 显示表格
     */
    private void showTable() {

        String[] itemNames = new String[]{"昵称", "邮箱", "电话", "学历", "个人简介"};
        String[] itemDescs = new String[]{author.getUsername(), author.getEmail(),
                author.getTel(), author.getEdubkg(), author.getDesc()};

        //new的时候指定宽高，LayoutParams相当于一个Layout信息包
        //表示子控件的父控件是一个TableRow
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < itemNames.length; i++) {

            TableRow tableRow1 = new TableRow(PersonInfoActivity.this);//新建TableRow
            TextView textView1 = new TextView(PersonInfoActivity.this);//新建TextView
            TableRow tableRow2 = new TableRow(PersonInfoActivity.this);//新建TableRow
            TextView textView2 = new TextView(PersonInfoActivity.this);//新建TextView

            textView1.setText(itemNames[i]);
            textView1.setTextSize(14);
            textView1.setPadding(10, 10, 10, 10);

            tableRow1.addView(textView1, layoutParams);
            tableRow1.setBackgroundColor(Color.WHITE);

            textView2.setText(itemDescs[i]);
            textView2.setTextSize(20);
            textView2.setPadding(15, 15, 15, 15);

            tableRow2.addView(textView2, layoutParams);
            tableRow2.setBackgroundColor(Color.LTGRAY);

            final int n = i;
            tableRow2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonInfoActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mTableLayout.addView(tableRow1);
            mTableLayout.addView(tableRow2);


        }

    }


    @Override
    public void onTurn() {

    }
}
