package cn.edu.whut.tgsg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.adapter.ManuscriptAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.util.T;

/**
 * 稿件界面
 * <p/>
 * Created by xwh on 2015/12/3.
 */
public class ManuscriptFragment extends BaseFragment {

    @Bind(R.id.list_manuscript)
    ListView mListManuscript;

    List<Manuscript> mManuscriptList;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_manuscript;
    }

    @Override
    protected void initData() {
        // 初始化稿件列表
        initManuscriptList();
    }

    @Override
    protected void initListener() {
        /**
         * 稿件点击
         */
        mListManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "稿件" + position);
                Intent intent = new Intent(mContext, ManuscriptDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("manuscript", mManuscriptList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化稿件列表
     */
    private void initManuscriptList() {
        mManuscriptList = new ArrayList<>();
        ManuscriptVersion manuscriptVersion = new ManuscriptVersion(1, "乖，摸摸头", "真实的故事自有万钧之力，本书讲述了12个真实的故事。或许会让你看到那些你永远无法去体会的生活，见识那些可能你永远都无法结交的人。", Arrays.asList("大冰", "旅行", "治愈", "散文随笔"), "", "2015-12-11 10:45:21");
        mManuscriptList.add(new Manuscript(1, "随笔", "2015-12-11 10:35:10", 6, manuscriptVersion));
        manuscriptVersion = new ManuscriptVersion(1, "红楼梦", "《红楼梦》是一部百科全书式的长篇小说。以宝黛爱情悲剧为主线，以四大家族的荣辱兴衰为背景，描绘出18世纪中国封建社会的方方面面，以及封建专制下新兴资本主义民主思想的萌动。", Arrays.asList("古典文学", "曹雪芹", "经典", "小说", "中国", "名著"), "", "2015-12-11 10:45:21");
        mManuscriptList.add(new Manuscript(2, "名著", "2015-12-10 11:35:10", 4, manuscriptVersion));
        manuscriptVersion = new ManuscriptVersion(1, "芈月传(1-6)", "她是历史上真实存在的传奇女性。“太后”一词由她而来。太后专权，也自她始。她是千古一帝秦始皇的高祖母。她沿着商鞅变法之路，奠定了日后秦国一统天下的基础。 到现在都还有学者坚信，兵马俑的主人其实是她。", Arrays.asList("芈月传", "中国文学", "女性", "蔣胜男", "小说", "古代"), "", "2015-12-11 10:45:21");
        mManuscriptList.add(new Manuscript(3, "文学", "2015-12-08 14:47:23", 2, manuscriptVersion));
        ManuscriptAdapter adapter = new ManuscriptAdapter(mContext, mManuscriptList);
        mListManuscript.setAdapter(adapter);
    }
}
