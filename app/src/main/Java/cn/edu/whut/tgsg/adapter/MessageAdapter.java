package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Message message = mMessageList.get(position);
        viewHolder.mTvMessageTitle.setText(message.getTitle());
        viewHolder.mTvMessageContent.setText(message.getContent());
        viewHolder.mTvMessageDate.setText(message.getDate());
        viewHolder.mImgCheck.setVisibility((message.isCheck()) ? View.INVISIBLE : View.VISIBLE);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_message.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_message_title)
        TextView mTvMessageTitle;
        @Bind(R.id.tv_message_content)
        TextView mTvMessageContent;
        @Bind(R.id.tv_message_date)
        TextView mTvMessageDate;
        @Bind(R.id.img_check)
        ImageView mImgCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
