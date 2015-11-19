package cn.edu.whut.tgsg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by xwh on 2015/11/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        // 注解绑定
        ButterKnife.bind(this);
    }
}
