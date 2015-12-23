package cn.edu.whut.tgsg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.NoticeDetailActivity;
import cn.edu.whut.tgsg.adapter.NoticeAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Notice;
import cn.edu.whut.tgsg.util.T;

/**
 * 首页
 * <p/>
 * Created by xwh on 2015/11/19.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.slider)
    SliderLayout mSlider;
    @Bind(R.id.list_notice)
    ListView mListNotice;

    NoticeAdapter mAdapter;

    @Override
    protected String getTagName() {
        return "HomeFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        // 初始化图片滑动展示栏
        initImageSlider();
        // 初始化公告列表
        initNoticeList();
    }

    @Override
    protected void initListener() {
        /**
         * 公告点击
         */
        mListNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "公告" + position);
                Intent intent = new Intent(mContext, NoticeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notice", mAdapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化公告列表
     */
    private void initNoticeList() {
        List<Notice> list = new ArrayList<>();
        list.add(new Notice(1, "关于余区管委会工人技师评审组拟推荐评聘万荣为技师的情况公示", "12-15", "<h2 style=\"font-style:italic;\">dsfsgs<span class=\"marker\">gfdgfdsdfsdf</span></h2>\n" +
                "\n" + "<h1>sfsdfadsafsafdsfykfsafsd散发的发放<span style=\"font-family:georgia,serif\">大风哥哥如果我</span></h1>\n" +
                "\n" + "<p><span style=\"font-family:georgia,serif\">撒<s>德国大使馆的发声</s></span></p>\n" +
                "\n" + "<p><span style=\"font-family:georgia,serif\"><s>阿飞大范德萨发沙发<sub>是非得失</sub></s></span></p>"));
        list.add(new Notice(2, "关于开展“十二五”规划全面总结及末期考核的通知", "12-14", ""));
        list.add(new Notice(3, "关于加强校园活动安全管理的通知", "12-13", ""));
        list.add(new Notice(4, "关于开展2015年目标责任制暨竞争性绩效津贴年度考核工作的通知", "12-12", ""));
        list.add(new Notice(5, "关于开展2015年目标责任制暨竞争性绩效津贴年度考核工作的通知", "12-11", ""));
        list.add(new Notice(6, "关于开展2015年目标责任制暨竞争性绩效津贴年度考核工作的通知", "12-10", ""));
        list.add(new Notice(7, "关于开展2015年目标责任制暨竞争性绩效津贴年度考核工作的通知", "12-04", ""));
        mAdapter = new NoticeAdapter(getContext(), list);
        mListNotice.setAdapter(mAdapter);
    }

    /**
     * 初始化图片滑动展示栏
     */
    private void initImageSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

//        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal", R.drawable.hannibal);
//        file_maps.put("Big Bang Theory", R.drawable.bigbang);
//        file_maps.put("House of Cards", R.drawable.house);
//        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add1 your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(6000);
        mSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        T.show(mContext, slider.getBundle().get("extra") + "");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
