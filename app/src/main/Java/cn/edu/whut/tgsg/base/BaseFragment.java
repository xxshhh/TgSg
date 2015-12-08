package cn.edu.whut.tgsg.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * <p/>
 * Created by xwh on 2015/11/19.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getContentLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayoutId(), container, false);
        // 注解绑定
        ButterKnife.bind(this, view);
        return view;
    }
}
