package cn.edu.whut.tgsg.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 一个ListView通用的适配器
 * <p/>
 * Created by ylj on 2015-12-09.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;//数据源
    protected Context mContext;
    protected LayoutInflater mInflater;

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 构造方法：对成员变量进行初始化
     *
     * @param context
     * @param dataList
     */
    public CommonAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    /**
     * 返回第position处的列表项内容
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 方法作用：返回第position处的列表项组件
     * 因为每个Adapter的getView方法是不同的，所以公布出去，让具体的Adapter自己实现
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
