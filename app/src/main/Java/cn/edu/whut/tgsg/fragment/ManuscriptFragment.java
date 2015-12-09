package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.ManuscriptAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.util.T;

/**
 * 稿件界面
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class ManuscriptFragment extends BaseFragment {

    @Bind(R.id.list_manuscript)
    ListView mListManuscript;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_manuscript;
    }

    @Override
    protected void initListener() {
        /**
         * 稿件点击
         */
        mListManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "消息" + position);
            }
        });
    }

    @Override
    protected void initData() {
        // 初始化稿件列表
        initManuscriptList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化稿件列表
     */
    private void initManuscriptList() {
        List<Manuscript> list = new ArrayList<>();
        list.add(new Manuscript("乖，摸摸头", Arrays.asList("大冰", "旅行", "治愈", "散文随笔"), "讲述了12个真实的传奇故事,或许会让你看到那些你永远无法去体... （更多）", 6));
        list.add(new Manuscript("生命不息，折腾不止", Arrays.asList("励志", "人物", "理想主义", "罗永浩"), "“我不是为了输赢，我就是认真。”从牛博网，到老罗英语培训，再... （更多）", 2));
        list.add(new Manuscript("三体", Arrays.asList("科幻三体硬科幻", "刘慈欣", "科幻小说", "中国", "小说", "中国科幻"), "“一文化大革命如火如荼进行的同时。军方探寻外星文明的绝秘计划... （更多）", 5));
        list.add(new Manuscript("小王子", Arrays.asList("经典", "童话", "法国", "外国文学"), "“一个迫降在撒哈拉沙漠的飞行员，遇上了一个从自己的星球“离家... （更多）", 1));
        ManuscriptAdapter adapter = new ManuscriptAdapter(getContext(), list);
        mListManuscript.setAdapter(adapter);
    }
}
