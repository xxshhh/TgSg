package cn.edu.whut.tgsg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.adapter.MessageAdapter;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.bean.Message;
import cn.edu.whut.tgsg.util.T;

/**
 * 首页内容
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayoutId(), container, false);
        // 注解绑定
        ButterKnife.bind(this, view);

        initImageSlider();

        List<Message> list = new ArrayList<>();
        list.add(new Message(R.drawable.manuscript_blue, R.string.message_from_newsroom, "今天", "16:10"));
        list.add(new Message(R.drawable.manuscript_blue, R.string.message_from_newsroom, "昨天", "15:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        list.add(new Message(R.drawable.message_blue, R.string.message_from_system, "11/29", "22:10"));
        MessageAdapter adapter = new MessageAdapter(getContext(), list);
        mListMessage.setAdapter(adapter);
        return view;
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

            //add your extra information
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
