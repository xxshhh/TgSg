package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.RefreshListviewSolvedAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.GjEntity;
import cn.edu.whut.tgsg.widget.ReFreshListView;

/**
 * Created by ylj on 2015-12-07.
 */
public class ExpertSolvedFragment extends BaseFragment implements ReFreshListView.IReFreshListener{

    //mContext
    //声明控件
    private ReFreshListView mListView;
    //数据源
    private ArrayList<GjEntity> mGjEntities;
    //适配器
    private RefreshListviewSolvedAdapter mAdapter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_expert_ckgj_solved;
    }

    @Override
    protected void initListener() {



    }


    private void showList(ArrayList<GjEntity> entities) {

        if (mAdapter == null) {
            mListView.setInterface(this);
//            mListView = (ReFreshListView) getActivity().findViewById(R.id.refresh_listview);
            mAdapter = new RefreshListviewSolvedAdapter(getActivity(),mGjEntities);
            mListView.setAdapter(mAdapter);


        } else {
            mAdapter.onDateChange(mGjEntities);
        }
    }

    @Override
    protected void initData() {

        mGjEntities = new ArrayList<GjEntity>();
        for (int i = 0; i < 10; i++) {
            GjEntity entity = new GjEntity();
            entity.setName("原始数据"+i);
            entity.setReleaseTime("2015-10-1");
            mGjEntities.add(entity);
        }



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = (ReFreshListView) getActivity().findViewById(R.id.refresh_listview_solved);

        showList(mGjEntities);
    }


    /**
     * 接口中的方法
     */
    @Override
    public void onRefresh() {

        Handler handler = new Handler();//刷新的太快了，增加一个延时
        handler.postDelayed(new Runnable() {//延时

            @Override
            public void run() {
                //获取最新数据
                setRefreshData();
                //通知界面显示
                showList(mGjEntities);
                //通知listview 刷新数据完毕；
                mListView.reflashComplete();
            }
        }, 2000);

    }

    /**
     * 准备好数据源
     */
    private void setRefreshData() {

        for (int i = 0; i < 2; i++) {
            GjEntity entity = new GjEntity();
            entity.setName("刷新数据 "+i);
            entity.setReleaseTime("2015-11-20");
            mGjEntities.add(0,entity);
        }
    }

}
