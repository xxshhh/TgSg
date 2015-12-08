package cn.edu.whut.tgsg.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Activity基类
 * <p/>
 * Created by xwh on 2015/11/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected abstract int getContentLayoutId();

    protected abstract Context getContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentLayoutId());
        mContext = getContext();
        // 注解绑定
        ButterKnife.bind(this);
    }

}
