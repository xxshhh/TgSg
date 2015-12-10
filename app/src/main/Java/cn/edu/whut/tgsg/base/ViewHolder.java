package cn.edu.whut.tgsg.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 一个通用的，封装对item中控件的引用的ViewHolder类，
 * <p/>
 * Created by ylj on 2015-12-09.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    //属于map的范畴 int(viewId)——Objects(View)
    //控件的id——控件本身是View对象，把item中的控件都存储在一个集合中，通过viewId进行索引
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        //layoutId:类似于，R.layout.activity_main


        /*对数据的初始化是非常重要的，
        构造函数的作用是对数据进行初始化，很多错误是忘记对数据进行初始化导致的*/
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        //加载布局文件，并将布局文件实例化
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);

    }


    /**
     * 入口方法
     * 作用：得到一个ViewHold对象
     * ViewHolder中存储了对item中各类控件的引用
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {//用于回收item的布局，每个item的布局都是一样的
            return new ViewHolder(context, parent, layoutId, position);

        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    /**
     * 返回convertView对象
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 根据传入的viewId得到view控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return (T) view;
    }


    /**
     * 辅助方法
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);//得到控件
        view.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int res) {
        ImageView view = getView(viewId);//得到控件
        view.setImageResource(res);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);//得到控件
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageURL(int viewId, String url) {
        ImageView view = getView(viewId);//得到控件
        //ImageLoader.getInstance().loadImg(view,url);
        // 网络图片的加载
//        view.setImageBitmap(bitmap);
        return this;
    }


}
