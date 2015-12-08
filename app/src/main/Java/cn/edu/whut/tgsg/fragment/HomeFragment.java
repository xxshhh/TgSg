package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import cn.edu.whut.tgsg.adapter.MessageAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.util.T;

/**
 * 首页
 * <p/>
 * Created by xwh on 2015/11/19.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.slider)
    SliderLayout mSlider;
    @Bind(R.id.list_message)
    ListView mListMessage;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化图片滑动展示栏
        initImageSlider();
        // 初始化消息列表
        initMessageList();

    }

    /**
     * 初始化消息列表
     */
    private void initMessageList() {
        List<Message> list = new ArrayList<>();
        list.add(new Message("系统消息", "您的稿件“乖，摸摸头”状态已变更为“刊登”", "17:55", false));
        list.add(new Message("系统消息", "您的稿件“乖，摸摸头”状态已变更为“录用”", "12/03", false));
        list.add(new Message("系统消息", "您的稿件“三体”状态已变更为“专家审核”", "12/01", false));
        list.add(new Message("系统消息", "您的稿件“三体”状态已变更为“专家审核”", "11/30", false));
        list.add(new Message("系统消息", "您的稿件“小王子”状态已变更为“编辑初审”", "11/29", true));
        list.add(new Message("系统消息", "您的稿件“小王子”状态已变更为“投稿”", "12/27", true));
        MessageAdapter adapter = new MessageAdapter(getContext(), list);
        mListMessage.setAdapter(adapter);
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
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        T.show(getContext(), slider.getBundle().get("extra") + "");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
