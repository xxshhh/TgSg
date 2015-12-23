package cn.edu.whut.tgsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.whut.tgsg.MyApplication;
import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.base.BaseActivity;
import cn.edu.whut.tgsg.base.CommonAdapter;
import cn.edu.whut.tgsg.bean.DistributeExpert;
import cn.edu.whut.tgsg.bean.Manuscript;
import cn.edu.whut.tgsg.bean.Role;
import cn.edu.whut.tgsg.bean.User;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.common.StateTable;
import cn.edu.whut.tgsg.util.DateHandleUtil;
import cn.edu.whut.tgsg.util.T;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 分配专家
 * <p/>
 * Created by ylj on 2015-12-17.
 */
public class DistributeExpertActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_focus)
    TextView mTvFocus;
    @Bind(R.id.spinner_state)
    MaterialSpinner mSpinnerState;
    @Bind(R.id.btn_search)
    Button mBtnSearch;
    @Bind(R.id.edt_input)
    EditText mEdtInput;
    @Bind(R.id.list_distribute_expert)
    ListView mListDistributeExpert;

    DistributeExpertAdapter mAdapter;

    Manuscript mManuscript;

    @Override
    protected String getTagName() {
        return "DistributeExpertActivity";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_distribute_expert;
    }

    @Override
    protected Context getContext() {
        return DistributeExpertActivity.this;
    }

    @Override
    protected void initData() {
        // 获取传来的Manuscript对象
        Bundle bundle = getIntent().getExtras();
        mManuscript = (Manuscript) bundle.getSerializable("manuscript");
        // 设置toolbar
        mToolbar.setTitle("分配专家");
        setSupportActionBar(mToolbar);
        // 设置返回键<-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 初始化状态下拉框
        initSpinnerState();
        // 初始化专家列表
        initExpertList();
        // 空布局获得焦点,防止自动弹出软键盘
        mTvFocus.requestFocus();
    }

    @Override
    protected void initListener() {
        /**
         * 专家点击
         */
        mListDistributeExpert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "专家" + position);
            }
        });

        /**
         * 状态下拉框
         */
        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                T.show(mContext, "你点击的是:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 查询
         */
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show(mContext, mSpinnerState.getSelectedItemPosition() + mEdtInput.getText().toString());
                mAdapter.getDataList().clear();
                mAdapter.getDataList().add(0, new User(0, null, null, "杨志", 0, null, null, null, null, "计算机", "信息安全", null, null));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 初始化状态下拉框
     */
    private void initSpinnerState() {
        String[] array = StateTable.getDistributeExpertSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(adapter);
    }

    /**
     * 初始化专家列表
     */
    private void initExpertList() {
        List<User> list = new ArrayList<>();
        mAdapter = new DistributeExpertAdapter(mContext, list);
        mListDistributeExpert.setAdapter(mAdapter);
    }

    /**
     * 调用分配专家操作
     *
     * @param expert
     */
    private void distributeExpert(User expert) {
//        DistributeExpert distributeExpert = new DistributeExpert(1, mManuscript.getManuscriptVersion(), MyApplication.GLOBAL_USER, expert, DateHandleUtil.convertToStandard(new Date()));
//        T.show(mContext, distributeExpert.toString());
    }

    /**
     * 分配专家adapter
     */
    public class DistributeExpertAdapter extends CommonAdapter<User> {

        /**
         * 构造方法：对成员变量进行初始化
         *
         * @param context
         * @param dataList
         */
        public DistributeExpertAdapter(Context context, List<User> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_distribute_expert, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final User expert = mDataList.get(position);
            viewHolder.mTvExpertName.setText(expert.getName());
            viewHolder.mTvExpertMajor.setText(expert.getProfessional());
            viewHolder.mTvExpertResearchDirection.setText(expert.getResearch());
            viewHolder.mBtnDistribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用分配专家操作
                    distributeExpert(expert);
                }
            });
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_distribute_expert.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        final class ViewHolder {
            @Bind(R.id.tv_expert_name)
            TextView mTvExpertName;
            @Bind(R.id.tv_expert_major)
            TextView mTvExpertMajor;
            @Bind(R.id.tv_expert_research_direction)
            TextView mTvExpertResearchDirection;
            @Bind(R.id.btn_distribute)
            Button mBtnDistribute;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
