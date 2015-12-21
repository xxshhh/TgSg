package cn.edu.whut.tgsg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.common.StateTable;

/**
 * 审稿意见adapter
 * <p/>
 * Created by xwh on 2015/12/20.
 */
public class ExamineOpinionAdapter extends CommonAdapter<ExamineManuscript> {

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public ExamineOpinionAdapter(Context context, List<ExamineManuscript> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_examineopinion, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ExamineManuscript examineManuscript = mDataList.get(position);
        viewHolder.mTvManuscriptState.setText(StateTable.getString(1));
        viewHolder.mTvExamineUser.setText(examineManuscript.getUserInfo().getName());
        viewHolder.mTvExamineResult.setText(examineManuscript.getResult() == 1 ? "通过" : "不通过");
        viewHolder.mTvExamineDate.setText(examineManuscript.getAuditingTime());
        viewHolder.mBtnCheckOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("审稿意见")
                        .content(examineManuscript.getOpinion())
                        .positiveText(R.string.agree)
                        .show();
            }
        });
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_examineopinion.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_manuscript_state)
        TextView mTvManuscriptState;
        @Bind(R.id.tv_examine_user)
        TextView mTvExamineUser;
        @Bind(R.id.tv_examine_result)
        TextView mTvExamineResult;
        @Bind(R.id.tv_examine_date)
        TextView mTvExamineDate;
        @Bind(R.id.btn_check_opinion)
        Button mBtnCheckOpinion;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
