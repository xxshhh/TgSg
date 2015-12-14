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
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import me.next.tagview.TagCloudView;

/**
 * 稿件adapter
 * <p/>
 * Created by xwh on 2015/11/30.
 */
public class ManuscriptAdapter extends CommonAdapter<Manuscript> {

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param data
     */
    public ManuscriptAdapter(Context context, List<Manuscript> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_manuscript, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Manuscript manuscript = mData.get(position);
        // 根据稿件对象获取最新稿件版本
        ManuscriptVersion manuscriptVersion = manuscript.getManuscriptVersion();
        viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
        viewHolder.mTvManuscriptDate.setText(manuscript.getDate());
        viewHolder.mTcvManuscriptKeywords.setTags(manuscriptVersion.getKeywords());
        viewHolder.mTvManuscriptSummary.setText(manuscriptVersion.getSummary().substring(0, 30) + "...（更多）");
        // 根据稿件状态设置进度显示
        int state = manuscript.getState();
        switch (state) {
            case 1:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 5:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_doing);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 6:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_done);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_manuscript.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_manuscript_title)
        TextView mTvManuscriptTitle;
        @Bind(R.id.tv_manuscript_date)
        TextView mTvManuscriptDate;
        @Bind(R.id.tcv_manuscript_keywords)
        TagCloudView mTcvManuscriptKeywords;
        @Bind(R.id.tv_manuscript_summary)
        TextView mTvManuscriptSummary;
        @Bind(R.id.img_progress_1)
        ImageView mImgProgress1;
        @Bind(R.id.img_progress_2)
        ImageView mImgProgress2;
        @Bind(R.id.img_progress_3)
        ImageView mImgProgress3;
        @Bind(R.id.img_progress_4)
        ImageView mImgProgress4;
        @Bind(R.id.img_progress_5)
        ImageView mImgProgress5;
        @Bind(R.id.tv_progress_1)
        TextView mTvProgress1;
        @Bind(R.id.tv_progress_2)
        TextView mTvProgress2;
        @Bind(R.id.tv_progress_3)
        TextView mTvProgress3;
        @Bind(R.id.tv_progress_4)
        TextView mTvProgress4;
        @Bind(R.id.tv_progress_5)
        TextView mTvProgress5;
        @Bind(R.id.img_finished_flag)
        ImageView mImgFinishedFlag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
