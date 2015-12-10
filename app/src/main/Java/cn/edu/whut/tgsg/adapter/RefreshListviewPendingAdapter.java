package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.bean.GjEntity;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.base.ViewHolder;

/**
 * Created by ylj on 2015-12-08.
 */
public class RefreshListviewPendingAdapter extends CommonAdapter<GjEntity> {

     /*
    子类从超类中继承非private的方法和变量
    因为CommonAdapter中有mDatas;//数据源
                        mContext;
                        mInflater;
        三个protected的变量，所以该类中继承了父类的变量和方法
     */

    /**
     * 构造方法
     * @param context
     * @param gjEntities
     */
    public RefreshListviewPendingAdapter(Context context, List<GjEntity> gjEntities) {
        super(context, gjEntities);
    }

    /**
     * 得到position处的item的布局
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1,得到holder对象
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                R.layout.item_expert_ckgj_pending, position);

        GjEntity entity = getItem(position);
        //2,得到holder对象中封装的控件
        holder.setText(R.id.tv_name, entity.getName());
        holder.setText(R.id.tv_time, entity.getReleaseTime());

        Button mCheckAgain=holder.getView(R.id.bt_checkAgain);
        mCheckAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "跳转到复审界面", Toast.LENGTH_SHORT).show();
            }
        });

        return holder.getConvertView();
    }


    public void onDateChange(ArrayList<GjEntity> gjEntities) {

        this.mDatas = gjEntities;
        this.notifyDataSetChanged();//-----------------------

    }

}
