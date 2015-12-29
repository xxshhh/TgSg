package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.common.Constant;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 留言消息adapter
 * <p/>
 * Created by xwh on 2015/12/27.
 */
public class MessageAdapter extends CommonAdapter<Message> {

    Bitmap mMeBitmap = null;
    Bitmap mOtherBitmap = null;

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public MessageAdapter(Context context, List<Message> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断留言是自己发送还是别人发送的
        Message message = mDataList.get(position);
        boolean flag = false;
        if (message.getSender().getId() == MyApplication.GLOBAL_USER.getId()) {
            flag = true;//自己发送
        }

        ViewHolder viewHolder = null;
        if (flag) {//自己发送
            convertView = mInflater.inflate(R.layout.item_to_msg, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImage = (CircleImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_to_msg_date);
            viewHolder.mMsg = (TextView) convertView.findViewById(R.id.tv_to_msg_info);
            if (mMeBitmap == null) {
                // 请求sender
                final ViewHolder finalViewHolder = viewHolder;
                OkHttpUtils
                        .get()
                        .url(Constant.STATIC_URL + "img/" + message.getSender().getPhoto())
                        .addParams("source", "android")
                        .tag(this)
                        .build()
                        .connTimeOut(20000)
                        .readTimeOut(20000)
                        .writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                            }

                            @Override
                            public void onResponse(Bitmap bitmap) {
                                mMeBitmap = bitmap;
                                finalViewHolder.mImage.setImageBitmap(mMeBitmap);
                            }
                        });
            } else {
                viewHolder.mImage.setImageBitmap(mMeBitmap);
            }
        } else {//别人发送
            convertView = mInflater.inflate(R.layout.item_from_msg, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImage = (CircleImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_from_msg_date);
            viewHolder.mMsg = (TextView) convertView.findViewById(R.id.tv_from_msg_info);
            if (mOtherBitmap == null) {
                // 请求sender
                final ViewHolder finalViewHolder = viewHolder;
                OkHttpUtils
                        .get()
                        .url(Constant.STATIC_URL + "img/" + message.getSender().getPhoto())
                        .addParams("source", "android")
                        .tag(this)
                        .build()
                        .connTimeOut(20000)
                        .readTimeOut(20000)
                        .writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                            }

                            @Override
                            public void onResponse(Bitmap bitmap) {
                                mOtherBitmap = bitmap;
                                finalViewHolder.mImage.setImageBitmap(mOtherBitmap);
                            }
                        });
            } else {
                viewHolder.mImage.setImageBitmap(mOtherBitmap);
            }
        }
        convertView.setTag(viewHolder);

        // 填充数据
        viewHolder.mDate.setText(message.getMessageTime());
        viewHolder.mMsg.setText(message.getMessage());
        viewHolder.mName.setText(message.getSender().getName());

        return convertView;
    }

    static class ViewHolder {
        CircleImageView mImage;
        TextView mName;
        TextView mDate;
        TextView mMsg;
    }
}
