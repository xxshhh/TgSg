package cn.edu.whut.tgsg.fragment;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseFragment;

/**
 * Created by xwh on 2015/11/19.
 */
public class EmptyFragment extends BaseFragment {

    @Override
    protected String getTagName() {
        return "EmptyFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
