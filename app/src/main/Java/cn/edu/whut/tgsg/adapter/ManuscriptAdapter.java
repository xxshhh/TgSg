package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
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
public class ManuscriptAdapter extends CommonAdapter<ManuscriptVersion> {

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public ManuscriptAdapter(Context context, List<ManuscriptVersion> dataList) {
        super(context, dataList);
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
        ManuscriptVersion manuscriptVersion = mDataList.get(position);
        Manuscript manuscript = manuscriptVersion.getArticle();
        // 根据稿件对象获取最新稿件版本
        viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
        viewHolder.mTvManuscriptDate.setText(manuscript.getContributeTime());
        viewHolder.mTcvManuscriptKeywords.setTags(Arrays.asList(manuscriptVersion.getKeyword().split(",")));
        viewHolder.mTvManuscriptSummary.setText(manuscriptVersion.getSummary().length() > 30 ? manuscriptVersion.getSummary().substring(0, 30) + "...（更多）" : manuscriptVersion.getSummary());
        // 根据稿件状态设置进度显示
        int state = manuscript.getState();
        switch (state) {
            case 1:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 5:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_doing);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 6:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            case 8:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_done);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_done);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.mImgFinishedFlag.setVisibility(View.VISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.INVISIBLE);
                break;
            default:
                viewHolder.mImgProgress1.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress2.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress3.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress4.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress5.setImageResource(R.mipmap.icon_undo);
                viewHolder.mImgProgress6.setImageResource(R.mipmap.icon_undo);
                viewHolder.mTvProgress1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mTvProgress6.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.mImgFinishedFlag.setVisibility(View.INVISIBLE);
                viewHolder.mImgUnfinishedFlag.setVisibility(View.VISIBLE);
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
        @Bind(R.id.img_progress_6)
        ImageView mImgProgress6;
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
        @Bind(R.id.tv_progress_6)
        TextView mTvProgress6;
        @Bind(R.id.img_finished_flag)
        ImageView mImgFinishedFlag;
        @Bind(R.id.img_unfinished_flag)
        ImageView mImgUnfinishedFlag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
