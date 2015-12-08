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
import cn.edu.whut.tgsg.bean.Manuscript;
import me.next.tagview.TagCloudView;

/**
 * 消息adapter
 * <p/>
 * Created by xwh on 2015/11/30.
 */
public class ManuscriptAdapter extends BaseAdapter {

    private Context mContext;
    private List<Manuscript> mManuscriptList;

    public ManuscriptAdapter(Context context, List<Manuscript> list) {
        mContext = context;
        mManuscriptList = list;
    }

    @Override
    public int getCount() {
        return mManuscriptList.size();
    }

    @Override
    public Object getItem(int position) {
        return mManuscriptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_manuscript, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_manuscript_title = (TextView) convertView.findViewById(R.id.tv_manuscript_title);
            viewHolder.tcv_manuscript_keywords = (TagCloudView) convertView.findViewById(R.id.tcv_manuscript_keywords);
            viewHolder.tv_manuscript_summary = (TextView) convertView.findViewById(R.id.tv_manuscript_summary);
            viewHolder.img_progress_1 = (ImageView) convertView.findViewById(R.id.img_progress_1);
            viewHolder.img_progress_2 = (ImageView) convertView.findViewById(R.id.img_progress_2);
            viewHolder.img_progress_3 = (ImageView) convertView.findViewById(R.id.img_progress_3);
            viewHolder.img_progress_4 = (ImageView) convertView.findViewById(R.id.img_progress_4);
            viewHolder.img_progress_5 = (ImageView) convertView.findViewById(R.id.img_progress_5);
            viewHolder.tv_progress_1 = (TextView) convertView.findViewById(R.id.tv_progress_1);
            viewHolder.tv_progress_2 = (TextView) convertView.findViewById(R.id.tv_progress_2);
            viewHolder.tv_progress_3 = (TextView) convertView.findViewById(R.id.tv_progress_3);
            viewHolder.tv_progress_4 = (TextView) convertView.findViewById(R.id.tv_progress_4);
            viewHolder.tv_progress_5 = (TextView) convertView.findViewById(R.id.tv_progress_5);
            viewHolder.img_finished_flag = (ImageView) convertView.findViewById(R.id.img_finished_flag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Manuscript manuscript = mManuscriptList.get(position);
        viewHolder.tv_manuscript_title.setText(manuscript.getTitle());
        viewHolder.tcv_manuscript_keywords.setTags(manuscript.getKeywords());
        viewHolder.tv_manuscript_summary.setText(manuscript.getSummary());
        // 根据稿件状态设置进度显示
        int state = manuscript.getState();
        switch (state) {
            case 1:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_doing);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_undo);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.img_finished_flag.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_doing);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_undo);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.img_finished_flag.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_doing);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_undo);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_undo);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.img_finished_flag.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_doing);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_undo);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.img_finished_flag.setVisibility(View.INVISIBLE);
                break;
            case 5:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_doing);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.img_finished_flag.setVisibility(View.INVISIBLE);
                break;
            case 6:
                viewHolder.img_progress_1.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_2.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_3.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_4.setImageResource(R.mipmap.icon_done);
                viewHolder.img_progress_5.setImageResource(R.mipmap.icon_done);
                viewHolder.tv_progress_1.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_2.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_3.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_4.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.tv_progress_5.setTextColor(mContext.getResources().getColor(R.color.third_text));
                viewHolder.img_finished_flag.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }

    private final class ViewHolder {
        public TextView tv_manuscript_title;
        public TagCloudView tcv_manuscript_keywords;
        public TextView tv_manuscript_summary;

        public ImageView img_progress_1;
        public ImageView img_progress_2;
        public ImageView img_progress_3;
        public ImageView img_progress_4;
        public ImageView img_progress_5;
        public TextView tv_progress_1;
        public TextView tv_progress_2;
        public TextView tv_progress_3;
        public TextView tv_progress_4;
        public TextView tv_progress_5;
        public ImageView img_finished_flag;
    }
}
