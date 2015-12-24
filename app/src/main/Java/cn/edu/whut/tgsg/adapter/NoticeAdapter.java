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
import cn.edu.whut.tgsg.bean.Notice;

/**
 * 公告adapter
 * <p/>
 * Created by xwh on 2015/12/16.
 */
public class NoticeAdapter extends CommonAdapter<Notice> {

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public NoticeAdapter(Context context, List<Notice> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_notice, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Notice notice = mDataList.get(position);
        viewHolder.mTvNoticeTitle.setText(notice.getTitle());
        int end = notice.getNoticeTime().trim().indexOf(" ");
        viewHolder.mTvNoticeDate.setText(notice.getNoticeTime().substring(0, end));
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_notice.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_notice_title)
        TextView mTvNoticeTitle;
        @Bind(R.id.tv_notice_date)
        TextView mTvNoticeDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
