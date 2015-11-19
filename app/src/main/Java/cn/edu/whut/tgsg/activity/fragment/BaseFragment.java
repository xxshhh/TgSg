package cn.edu.whut.tgsg.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xwh on 2015/11/19.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getContentLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentLayoutId(), null);
    }
}
