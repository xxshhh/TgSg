package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;

/**
 * 稿件版本adapter
 * <p/>
 * Created by xwh on 2015/12/14.
 */
public class HistoryVersionAdapter extends CommonAdapter<ManuscriptVersion> {
    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param data
     */
    public HistoryVersionAdapter(Context context, List<ManuscriptVersion> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_historyversion, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ManuscriptVersion manuscriptVersion = mData.get(position);
        viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
        viewHolder.mTvManuscriptDate.setText(manuscriptVersion.getDate());
        viewHolder.mTvManuscriptIndex.setText(String.valueOf(mData.size() - position));
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_historyversion.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_manuscript_title)
        TextView mTvManuscriptTitle;
        @Bind(R.id.tv_manuscript_date)
        TextView mTvManuscriptDate;
        @Bind(R.id.tv_manuscript_index)
        TextView mTvManuscriptIndex;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
