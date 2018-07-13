package com.sxu.basecomponent.uiwidget;

/*******************************************************************************
 * FileName: CommonChooseDialog
 * <p>
 * Description:
 * <p>
 * Author: juhg
 * <p>
 * Version: v1.0
 * <p>
 * Date: 16/10/19
 * <p>
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;

import java.util.List;

public class CommonChooseDialog extends AlertDialog {

    private TextView cancelText;
    private ListView listView;
    private String[] items;

    private int textColor;
    private float textSize;
    private String title;

    public CommonChooseDialog(Context context, String[] items) {
        this(context, items, null);
    }

    public CommonChooseDialog(Context context, List<String> itemList) {
        this(context, itemList.toArray(new String[itemList.size()]), null);
    }

    public CommonChooseDialog(Context context, List<String> itemList, String title) {
        super(context, R.style.CommonDialog);
        if (itemList != null) {
            this.items = itemList.toArray(new String[itemList.size()]);
        }
        this.title = title;
    }

    public CommonChooseDialog(Context context, String[] items, String title) {
        super(context, R.style.CommonDialog);
        this.items = items;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_choose_layout);
        listView = (ListView) findViewById(R.id.listView);
        cancelText = (TextView) findViewById(R.id.cancel_text);
        if (!TextUtils.isEmpty(title)) {
            listView.setHeaderDividersEnabled(true);
            TextView titleText = new TextView(getContext());
            titleText.setTextSize(13);
            titleText.setTextColor(getContext().getResources().getColor(R.color.b2));
            int padding = DisplayUtil.dpToPx(15);
            titleText.setPadding(padding, padding, padding, padding);
            titleText.setGravity(Gravity.CENTER);
            titleText.setText(title);
            titleText.setBackgroundColor(Color.WHITE);
            listView.addHeaderView(titleText);
        }

        if (items != null && items.length > 0) {
            textSize = 18;
            textColor = getContext().getResources().getColor(R.color.b1);
            listView.setAdapter(new MenuAdapter());
        }

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnItemListener(final AdapterView.OnItemClickListener listener) {
        if (listView != null && listener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (id >= 0) {
                        listener.onItemClick(parent, view, (int) id, id);
                        dismiss();
                    }
                }
            });
        }
    }

    public void show() {
        super.show();
        Window window = getWindow();
        window.setWindowAnimations(R.style.BottomDialogTheme);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DisplayUtil.getScreenWidth();
        params.gravity = Gravity.BOTTOM;
        //params.height = AndroidPlatformUtil.getDeviceScreenHeight(getContext()) - AndroidPlatformUtil.getStatusBarHeight(getContext());
        window.setAttributes(params);
    }

    private class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(textSize);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    DisplayUtil.dpToPx(50));
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items[position]);
            textView.setTextColor(textColor);

            return textView;
        }
    }
}

