package cn.edu.whut.tgsg.fragment.editor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.DistributeExpertActivity;
import cn.edu.whut.tgsg.activity.ExamineManuscriptActivity;
import cn.edu.whut.tgsg.activity.ManuscriptDetailActivity;
import cn.edu.whut.tgsg.base.BaseFragment;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.ManuscriptVersion;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 编辑已受理稿件界面
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class EditorHandleFragment extends BaseFragment {

    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.list_handle_manuscript)
    ListView mListhandleManuscript;

    HandleManuscriptAdapter mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_editor_handle;
    }

    @Override
    protected void initData() {
        // 初始化状态下拉框
        initSpinnerState();
        // 初始化已处理稿件列表
        initHandleManuscriptList();
    }

    @Override
    protected void initListener() {
        /**
         * 状态下拉框
         */
        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 查询不同稿件状态下对应的稿件列表
                queryForListByState(position);
                if (position != -1) {
                    T.show(mContext, "你点击的是:" + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 稿件点击
         */
        mListhandleManuscript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "已受理稿件" + position);
                Intent intent = new Intent(mContext, ManuscriptDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("manuscript", mAdapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getEditorStateSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    /**
     * 初始化已处理稿件列表
     */
    private void initHandleManuscriptList() {
        List<Manuscript> list = new ArrayList<>();
        mAdapter = new HandleManuscriptAdapter(mContext, list);
        mListhandleManuscript.setAdapter(mAdapter);
    }

    /**
     * 查询不同稿件状态下对应的稿件列表
     *
     * @param position
     */
    private void queryForListByState(int position) {
        ManuscriptVersion manuscriptVersion;
        mAdapter.getDataList().clear();
        switch (position) {
            case -1:
                mAdapter.notifyDataSetChanged();
                break;
            case 0:
                manuscriptVersion = new ManuscriptVersion(1, "编辑初审", "这是编辑初审状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 2, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                manuscriptVersion = new ManuscriptVersion(1, "待专家审核", "这是待专家审核状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 3, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
            case 2:
                manuscriptVersion = new ManuscriptVersion(1, "专家审核", "这是专家审核状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 4, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
            case 3:
                manuscriptVersion = new ManuscriptVersion(1, "编辑复审", "这是编辑复审状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 5, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
            case 4:
                manuscriptVersion = new ManuscriptVersion(1, "通过", "这是通过状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 6, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
            case 5:
                manuscriptVersion = new ManuscriptVersion(1, "录用", "这是录用状态的稿件", Arrays.asList("测试1", "测试2"), "", "2015-12-11 10:45:21");
                mAdapter.getDataList().add(0, new Manuscript(1, "随笔", Constant.GLOBAL_USER, DateHandleUtil.convertToStandard(new Date()), 7, manuscriptVersion));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 编辑操作稿件
     *
     * @param manuscript
     */
    private void handleManuscript(Manuscript manuscript) {
        T.show(mContext, "操作稿件" + manuscript.getManuscriptVersion().getTitle());
        Intent intent;
        Bundle bundle;
        switch (manuscript.getState()) {
            case 2:
            case 5:
                // 审稿
                intent = new Intent(mContext, ExamineManuscriptActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("manuscript", manuscript);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                // 分配专家
                intent = new Intent(mContext, DistributeExpertActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("manuscript", manuscript);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 6:
                // 录用
//                new MaterialDialog.Builder(mContext)
//                        .title("请选择稿件状态")
//                        .items(StateTable.getEditorStateSpinner())
//                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
//                            @Override
//                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                                T.show(mContext, which + ": " + text);
//                                return true;
//                            }
//                        })
//                        .positiveText(R.string.choose)
//                        .show();
                break;
            default:
        }
    }

    /**
     * 已受理稿件adapter
     */
    public class HandleManuscriptAdapter extends CommonAdapter<Manuscript> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public HandleManuscriptAdapter(Context context, List<Manuscript> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_editor_handle_manuscript, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Manuscript manuscript = mDataList.get(position);
            ManuscriptVersion manuscriptVersion = manuscript.getManuscriptVersion();
            viewHolder.mTvManuscriptTitle.setText(manuscriptVersion.getTitle());
            viewHolder.mTvManuscriptUser.setText(manuscript.getUser().getUsername());
            viewHolder.mTvManuscriptState.setText(StateTable.getString(manuscript.getState()));
            switch (manuscript.getState()) {
                case 2:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("审稿");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(manuscript);
                        }
                    });
                    break;
                case 3:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("分配专家");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(manuscript);
                        }
                    });
                    break;
                case 4:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
                    break;
                case 5:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("审稿");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(manuscript);
                        }
                    });
                    break;
                case 6:
                    viewHolder.mBtnHandle.setVisibility(View.VISIBLE);
                    viewHolder.mBtnHandle.setText("录用");
                    viewHolder.mBtnHandle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleManuscript(manuscript);
                        }
                    });
                    break;
                case 7:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
                    break;
                default:
                    viewHolder.mBtnHandle.setVisibility(View.GONE);
            }
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_handle_manuscript.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_manuscript_title)
            TextView mTvManuscriptTitle;
            @Bind(R.id.tv_manuscript_user)
            TextView mTvManuscriptUser;
            @Bind(R.id.tv_manuscript_state)
            TextView mTvManuscriptState;
            @Bind(R.id.btn_handle)
            Button mBtnHandle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
