package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;


/*******************************************************************************
 * FileName: CommonDialog
 * <p>
 * Description:
 * <p>
 * Author: juhg
 * <p>
 * Version: v1.0
 * <p>
 * Date: 16/9/28
 * <p>
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class CommonListDialog extends AlertDialog {

    private ListView listView;
    private String[] items;

    private int textColor;
    private float textSize;

    public CommonListDialog(Context context, String[] items) {
        super(context, R.style.CommonDialog);
        this.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_list_layout);
        listView = (ListView) findViewById(R.id.listView);

        if (items != null && items.length > 0) {
            textSize = 16;
            textColor = getContext().getResources().getColor(R.color.b1);
            listView.setAdapter(new MenuAdapter());
        }
    }

    public void setOnItemListener(final AdapterView.OnItemClickListener listener) {
        if (listView != null && listener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listener.onItemClick(parent, view, position, id);
                    dismiss();
                }
            });
        }
    }

    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DisplayUtil.getScreenWidth() - DisplayUtil.dpToPx(80);
        getWindow().setAttributes(params);
    }

    private class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length+1;
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
                    DisplayUtil.dpToPx(45));
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
//            if (position != items.length) {
//                textView.setText(items[position]);
//                textView.setTextColor(textColor);
//                if (position == 0) {
//                    textView.setBackgroundResource(R.drawable.dialog_top_button_bg);
//                } else {
//                    textView.setBackgroundResource(R.drawable.home_white_button_bg);
//                }
//            } else {
//                textView.setText("取消");
//                textView.setTextColor(getContext().getResources().getColor(R.color.theme_color));
//                textView.setBackgroundResource(R.drawable.dialog_bottom_button_bg);
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dismiss();
//                    }
//                });
//            }

            return textView;
        }
    }
}
