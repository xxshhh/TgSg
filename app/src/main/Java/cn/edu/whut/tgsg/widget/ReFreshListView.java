package cn.edu.whut.tgsg.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.whut.tgsg.R;

/**
 * 自定义的下拉刷新控件
 * <p/>
 * Created by ylj on 2015-12-08.
 */
public class ReFreshListView extends ListView implements AbsListView.OnScrollListener {

    View header;//顶部布局文件
    private int headerHeight;//顶部布局的高度
    int firstVisibleItem;// 当前第一个可见的item的位置；

    int scrollState;// listview 当前滚动状态；
    boolean isRemark;// 标记，当前是在listview最顶端摁下的；
    int startY;// 摁下时的Y值；

    int state;// 当前的状态；
    final int NONE = 0;// 正常状态；
    final int PULL = 1;// 提示下拉状态；
    final int RELESE = 2;// 提示释放可刷新状态；
    final int REFLASHING = 3;// 刷新状态；

    IReFreshListener iReFreshListener;//刷新数据的接口

    /**
     * 重写构造方法
     *
     * @param context
     */
    public ReFreshListView(Context context) {
        super(context);
        initView(context);
    }


    public ReFreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ReFreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }
    /**
     * 构造方法
     */


    /**
     * 初始化界面
     * 添加顶部布局文件到ListView
     *
     * @param context
     */
    private void initView(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);//加载布局文件
        header = inflater.inflate(R.layout.refreshlistview_header, null);
//        measure
        measureView(header);
        headerHeight = header.getMeasuredHeight();//得到header布局的高度
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }


    /**
     * 通知父布局，占用的宽高
     *
     * @param view
     */
    private void measureView(View view) {

        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            //新建布局
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }


    /**
     * 设置header布局上边距
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {

        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();//----------------
    }


    /*
    onCsrollListener
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        this.scrollState = scrollState;
    }

    /**
     * firstVisibleItem：第一个可见item的编号
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.firstVisibleItem = firstVisibleItem;
    }
    /*
    onCsrollListener
     */


    /**
     * 监听手势
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    // 加载最新数据；
                    reflashViewByState();
                    iReFreshListener.onRefresh();//加载最新数据
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }


    /**
     * 判断移动过程操作；
     *
     * @param ev
     */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();//当前的位子Y
        int space = tempY - startY;//移动的距离
        //space>0:向下移动
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {//正在滚动
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }


    /**
     * 根据当前状态，改变界面显示；
     */
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress_bar);
        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;

            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }
    }

    /**
     * 获取完数据；
     */
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView lastupdatetime = (TextView) header
                .findViewById(R.id.last_update_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastupdatetime.setText(time);
    }

    public void setInterface(IReFreshListener iReFreshListener) {
        this.iReFreshListener = iReFreshListener;
    }

    /**
     * 刷新数据接口
     *
     * @author Administrator
     */
    public interface IReFreshListener {
        public void onRefresh();
    }

}

