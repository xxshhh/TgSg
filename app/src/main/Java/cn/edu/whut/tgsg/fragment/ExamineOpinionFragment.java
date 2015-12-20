package cn.edu.whut.tgsg.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.ExamineOpinionAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.ExamineManuscript;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * 审稿意见
 * <p/>
 * Created by xwh on 2015/12/20.
 */
public class ExamineOpinionFragment extends BaseFragment {

    @Bind(R.id.list_examineopinion)
    ListView mListExamineopinion;

    Manuscript mManuscript;

    ExamineOpinionAdapter mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_examineopinion;
    }

    @Override
    protected void initData() {
        // 获取Activity的稿件对象
        mManuscript = ((ManuscriptDetailActivity) getActivity()).getManuscript();
        // 初始化审稿意见列表
        initExamineOpinionList();
    }

    @Override
    protected void initListener() {
        /**
         * 审稿意见点击
         */
        mListExamineopinion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "position" + position);
            }
        });
    }

    /**
     * 初始化审稿意见列表
     */
    private void initExamineOpinionList() {
        // 根据不同的角色查找不同的审稿意见列表
        switch (Constant.GLOBAL_USER.getRole()) {
            case 2:
                break;
            case 3:
                break;
            default:
        }
        List<ExamineManuscript> list = new ArrayList<>();
        list.add(new ExamineManuscript(1, Constant.GLOBAL_USER, mManuscript, "这世界有另一种人，他们的生活模式与朝九晚五格格不入，却也活得有血有肉，有模有样。世界上还有另一种人，他们既可以朝九晚五，又可以浪荡天涯，比如大冰。", 1, "2015-12-20 11:53:48"));
        list.add(new ExamineManuscript(2, Constant.GLOBAL_USER, mManuscript, "读书，就是和作者交谈。我相信看完书的朋友，会和我当初一样，在和大冰对话，听他讲完那些故事后，把他当作自己的朋友。", 2, "2015-12-20 11:57:07"));
        mAdapter = new ExamineOpinionAdapter(mContext, list);
        mListExamineopinion.setAdapter(mAdapter);
    }
}
