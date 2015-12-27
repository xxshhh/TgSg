package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.Inform;

/**
 * 通知adapter
 * <p/>
 * Created by ylj on 2015/11/30.
 */
public class InformAdapter extends CommonAdapter<Inform> {

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public InformAdapter(Context context, List<Inform> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inform, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Inform inform = mDataList.get(position);
        viewHolder.mTvInformTitle.setText(inform.getTitle());
        viewHolder.mTvInformTime.setText(inform.getTime());
        if (inform.getRead() == 0) {
            viewHolder.mImgInform.setImageResource(R.mipmap.icon_inform_uncheck);
            viewHolder.mImgCheck.setVisibility(View.VISIBLE);
            viewHolder.mTvInformTitle.setTextColor(mContext.getResources().getColor(R.color.primary_text));
        } else {
            viewHolder.mImgInform.setImageResource(R.mipmap.icon_inform_checked);
            viewHolder.mImgCheck.setVisibility(View.INVISIBLE);
            viewHolder.mTvInformTitle.setTextColor(mContext.getResources().getColor(R.color.secondary_text));
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_inform.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.img_inform)
        ImageView mImgInform;
        @Bind(R.id.tv_inform_title)
        TextView mTvInformTitle;
        @Bind(R.id.tv_inform_time)
        TextView mTvInformTime;
        @Bind(R.id.img_check)
        ImageView mImgCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
