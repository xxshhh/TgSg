package cn.edu.whut.tgsg.base;

import android.content.Context;
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

    protected Context mContext;

    protected abstract int getContentLayoutId();

    protected abstract void initData();

    protected abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayoutId(), container, false);
        // 注解绑定
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getContext();
        initData();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 绑定重置
        ButterKnife.unbind(this);
    }
}
