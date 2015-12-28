package cn.edu.whut.tgsg.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import cn.edu.whut.tgsg.R;
import cn.edu.whut.tgsg.activity.LoginActivity;
import cn.edu.whut.tgsg.common.Constant;
import cn.edu.whut.tgsg.util.T;

/**
 * 设置界面
 * <p/>
 * Created by ylj on 2015-12-20.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        /**
         * 网页版
         */
        findPreference("web").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse(Constant.URL);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                return true;
            }
        });

        /**
         * 博客
         */
        findPreference("blog").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("http://xuwenhui.net");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                return true;
            }
        });

        /**
         * 注销
         */
        findPreference("logout").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title("注销账户")
                        .content("真的要注销账户吗？")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                T.show(getActivity(), "已经注销账户！！！");
                                // 存数据到SharedPreferences
                                SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("user_email", "");
                                editor.putString("user_password", "");
                                editor.commit();
                                materialDialog.dismiss();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }).show();
                return true;
            }
        });
    }
}
