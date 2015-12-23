package cn.edu.whut.tgsg.fragment.editor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 编辑未受理稿件界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class EditorUnhandleFragment extends BaseFragment {

    @Bind(R.id.list_unhandle_manuscript)
    ListView mListUnhandleManuscript;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    UnhandleManuscriptAdapter mAdapter;

    @Override
    protected String getTagName() {
        return "EditorUnhandleFragment";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_unhandle;
    }

    @Override
    protected void initData() {
        // 初始化未处理稿件列表
        initUnhandleManuscriptList();
        // 初始化下拉刷新控件
        initPtrFrame();
    }

    @Override
    protected void initListener() {
        /**
         * 稿件点击
         */
        mListUnhandleManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "未受理稿件" + position);
            }
        });

        /**
         * 下拉刷新
         */
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        ManuscriptVersion manuscriptVersion = new ManuscriptVersion(1, "测试", "测试", "测试1,测试2", "", "2015-12-11 10:45:21");
//                        mAdapter.getDataList().add(0, new Manuscript(1, "随笔", MyApplication.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 6, manuscriptVersion));
                        frame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }

    /**
     * 初始化未受理稿件列表
     */
    private void initUnhandleManuscriptList() {
        List<Manuscript> list = new ArrayList<>();
//        ManuscriptVersion manuscriptVersion = new ManuscriptVersion(1, "乖，摸摸头", "真实的故事自有万钧之力，本书讲述了12个真实的故事。或许会让你看到那些你永远无法去体会的生活，见识那些可能你永远都无法结交的人。", "大冰,旅行,治愈,散文随笔", "", "2015-12-11 10:45:21");
//        list.add(new Manuscript(1, "随笔", MyApplication.GLOBAL_USER, "2015-12-11 10:35:10", 6, manuscriptVersion));
//        manuscriptVersion = new ManuscriptVersion(1, "红楼梦", "《红楼梦》是一部百科全书式的长篇小说。以宝黛爱情悲剧为主线，以四大家族的荣辱兴衰为背景，描绘出18世纪中国封建社会的方方面面，以及封建专制下新兴资本主义民主思想的萌动。", "古典文学,曹雪芹,经典,小说,中国,名著", "", "2015-12-11 10:45:21");
//        list.add(new Manuscript(2, "名著", MyApplication.GLOBAL_USER, "2015-12-10 11:35:10", 4, manuscriptVersion));
//        manuscriptVersion = new ManuscriptVersion(1, "芈月传(1-6)", "她是历史上真实存在的传奇女性。“太后”一词由她而来。太后专权，也自她始。她是千古一帝秦始皇的高祖母。她沿着商鞅变法之路，奠定了日后秦国一统天下的基础。 到现在都还有学者坚信，兵马俑的主人其实是她。", "芈月传,中国文学,女性,蔣胜男,小说,古代", "", "2015-12-11 10:45:21");
//        list.add(new Manuscript(3, "文学", MyApplication.GLOBAL_USER, "2015-12-08 14:47:23", 1, manuscriptVersion));
        mAdapter = new UnhandleManuscriptAdapter(mContext, list);
        mListUnhandleManuscript.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPtrFrame() {
        final StoreHouseHeader header = new StoreHouseHeader(mContext);
        header.setPadding(0, 15, 0, 0);
        header.initWithString("loading...");
        header.setTextColor(getResources().getColor(R.color.primary));
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
    }

    /**
     * 编辑受理稿件操作
     *
     * @param manuscript
     */
    private void handleManuscript(Manuscript manuscript) {
//        T.show(mContext, "受理稿件:" + manuscript.getManuscriptVersion().getTitle());
        mAdapter.getDataList().remove(manuscript);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 未受理稿件adapter
     */
    public class UnhandleManuscriptAdapter extends CommonAdapter<Manuscript> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public UnhandleManuscriptAdapter(Context context, List<Manuscript> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_editor_unhandle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Manuscript manuscript = mDataList.get(position);
//            ManuscriptVersion manuscriptVersion = manuscript.getManuscriptVersion();
//            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
//            viewHolder.mTvManuscriptUser.setText(manuscript.getContributor().getName());
//            viewHolder.mTvManuscriptDate.setText(manuscript.getContributeTime());
            viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用编辑受理稿件操作
                    handleManuscript(manuscript);
                }
            });
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_unhandle_manuscript.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_manuscript_title)
            TextView mTvManuscriptTitle;
            @Bind(R.id.tv_manuscript_user)
            TextView mTvManuscriptUser;
            @Bind(R.id.tv_manuscript_date)
            TextView mTvManuscriptDate;
            @Bind(R.id.btn_handle)
            Button mBtnHandle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
