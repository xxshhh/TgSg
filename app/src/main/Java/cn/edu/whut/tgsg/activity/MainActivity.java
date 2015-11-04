package cn.edu.whut.tgsg.activity;

import android.content.Intent;
import android.os.Bundle;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;

/**
 * Created by xwh on 2015/11/4.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.icon_user_secret)
    AwesomeTextView iconRotate;
    @Bind(R.id.icon_heart)
    AwesomeTextView iconFlash;
    @Bind(R.id.tv_hello)
    BootstrapButton mButton;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconRotate.startRotate(true, AwesomeTextView.AnimationSpeed.SLOW);
        iconFlash.startFlashing(true, AwesomeTextView.AnimationSpeed.FAST);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String usernameStr = bundle.get("username").toString();
            mButton.setText("你好， " + usernameStr);
        }
    }
}
