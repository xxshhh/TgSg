package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.bean.Message;

/**
 * 消息adapter
 * <p/>
 * Created by xwh on 2015/11/30.
 */
public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Message> mMessageList;

    public MessageAdapter(Context context, List<Message> list) {
        mContext = context;
        mMessageList = list;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, null);
            viewHolder.tv_message_title = (TextView) convertView.findViewById(R.id.tv_message_title);
            viewHolder.tv_message_content = (TextView) convertView.findViewById(R.id.tv_message_content);
            viewHolder.tv_message_date = (TextView) convertView.findViewById(R.id.tv_message_date);
            viewHolder.img_check = (ImageView) convertView.findViewById(R.id.img_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Message message = mMessageList.get(position);
        viewHolder.tv_message_title.setText(message.getTitle());
        viewHolder.tv_message_content.setText(message.getContent());
        viewHolder.tv_message_date.setText(message.getDate());
        viewHolder.img_check.setVisibility((message.isCheck()) ? View.INVISIBLE : View.VISIBLE);
        return convertView;
    }

    private final class ViewHolder {
        public TextView tv_message_title;
        public TextView tv_message_content;
        public TextView tv_message_date;
        public ImageView img_check;
    }
}
