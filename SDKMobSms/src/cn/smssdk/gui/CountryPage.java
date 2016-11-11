/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2014年 mob.com. All rights reserved.
 */
package cn.smssdk.gui;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.framework.FakeActivity;
import cn.smssdk.gui.GroupListView.OnItemClickListener;

import static cn.smssdk.framework.utils.R.getIdRes;
import static cn.smssdk.framework.utils.R.getLayoutRes;
import static cn.smssdk.framework.utils.R.getStringRes;

/**
 * 国家列表界面
 */
public class CountryPage extends FakeActivity implements OnClickListener, TextWatcher, OnItemClickListener {
    private String id;
    // 国家号码规则
    private HashMap<String, String> countryRules;
    private EventHandler handler;
    private CountryListView listView;
    private EditText etSearch;
    private Dialog pd;

    public void setCountryId(String id) {
        this.id = id;
    }

    public void setCountryRuls(HashMap<String, String> countryRules) {
        this.countryRules = countryRules;
    }

    public void onCreate() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        pd = CommonDialog.ProgressDialog(activity);
        if (pd != null) {
            pd.show();
        }
        // 初始化搜索引擎
        SearchEngine.prepare(activity, new Runnable() {
            public void run() {
                afterPrepare();
            }
        });
    }

    private void afterPrepare() {
        runOnUIThread(new Runnable() {
            public void run() {
                int resId = getLayoutRes(activity, "smssdk_country_list_page");
                if (resId > 0) {
                    activity.setContentView(resId);
                }

                if (countryRules == null || countryRules.size() <= 0) {
                    handler = new EventHandler() {
                        @SuppressWarnings("unchecked")
                        public void afterEvent(int event, final int result, final Object data) {
                            if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                                runOnUIThread(new Runnable() {
                                    public void run() {
                                        if (pd != null && pd.isShowing()) {
                                            pd.dismiss();
                                        }

                                        if (result == SMSSDK.RESULT_COMPLETE) {
                                            onCountryListGot((ArrayList<HashMap<String, Object>>) data);
                                        } else {
                                            ((Throwable) data).printStackTrace();
                                            int resId = getStringRes(activity, "smssdk_network_error");
                                            if (resId > 0) {
                                                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();

                                            }
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    };
                    // 注册回调接口
                    SMSSDK.registerEventHandler(handler);
                    // 获取国家列表
                    SMSSDK.getSupportedCountries();
                } else {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    initPage();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initPage() {
        int resId = getIdRes(activity, "ll_back");
        if (resId > 0) {
            activity.findViewById(resId).setOnClickListener(this);
        }
        resId = getIdRes(activity, "ivSearch");
        if (resId > 0) {
            activity.findViewById(resId).setOnClickListener(this);
        }
        resId = getIdRes(activity, "iv_clear");
        if (resId > 0) {
            activity.findViewById(resId).setOnClickListener(this);
        }
        resId = getIdRes(activity, "clCountry");
        if (resId > 0) {
            listView = (CountryListView) activity.findViewById(resId);
            listView.setOnItemClickListener(this);
        }
        resId = getIdRes(activity, "et_put_identify");
        if (resId > 0) {
            etSearch = (EditText) activity.findViewById(resId);
            etSearch.addTextChangedListener(this);
        }
    }

    private void onCountryListGot(ArrayList<HashMap<String, Object>> countries) {
        // 解析国家列表
        for (HashMap<String, Object> country : countries) {
            String code = (String) country.get("zone");
            String rule = (String) country.get("rule");
            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
                continue;
            }

            if (countryRules == null) {
                countryRules = new HashMap<String, String>();
            }
            countryRules.put(code, rule);
        }
        // 回归页面初始化操作
        initPage();
    }

    public void onItemClick(GroupListView parent, View view, int group, int position) {
        if (position >= 0) {
            String[] country = listView.getCountry(group, position);
            if (countryRules != null && countryRules.containsKey(country[1])) {
                id = country[2];
                finish();
            } else {
                int resId = getStringRes(activity, "smssdk_country_not_support_currently");
                if (resId > 0) {
                    Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        int id_ll_back = getIdRes(activity, "ll_back");
        int id_ivSearch = getIdRes(activity, "ivSearch");
        int id_iv_clear = getIdRes(activity, "iv_clear");
        if (id == id_ll_back) {
            finish();
        } else if (id == id_ivSearch) {
            // 搜索
            int id_llTitle = getIdRes(activity, "llTitle");
            activity.findViewById(id_llTitle).setVisibility(View.GONE);
            int id_llSearch = getIdRes(activity, "llSearch");
            activity.findViewById(id_llSearch).setVisibility(View.VISIBLE);
            etSearch.getText().clear();
            etSearch.requestFocus();
        } else if (id == id_iv_clear) {
            etSearch.getText().clear();
        }
    }

    public boolean onKeyEvent(int keyCode, KeyEvent event) {
        try {
            int resId = getIdRes(activity, "llSearch");
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN
                    && activity.findViewById(resId).getVisibility() == View.VISIBLE) {
                activity.findViewById(resId).setVisibility(View.GONE);
                resId = getIdRes(activity, "llTitle");
                activity.findViewById(resId).setVisibility(View.VISIBLE);
                etSearch.setText("");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onKeyEvent(keyCode, event);
    }

    public boolean onFinish() {
        // 销毁监听接口
        SMSSDK.unregisterEventHandler(handler);
        //start activity for result
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("id", id);
        res.put("rules", countryRules);
        res.put("page", 1);
        setResult(res);
        return super.onFinish();
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listView.onSearch(s.toString().toLowerCase());
    }

    public void afterTextChanged(Editable s) {

    }

}
