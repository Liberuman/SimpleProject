package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
public class CommonDialog extends AlertDialog {

    private TextView titleText;
    private TextView descText;
    private TextView cancelText;
    private TextView okText;
    private TextView gapLine;

    private String title;
    private String desc;

    public CommonDialog(Context context, String title, String desc) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.desc = desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_layout);
        titleText = (TextView) findViewById(R.id.title_text);
        descText = (TextView) findViewById(R.id.desc_text);
        cancelText = (TextView) findViewById(R.id.cancel_text);
        okText = (TextView) findViewById(R.id.ok_text);
        gapLine = (TextView) findViewById(R.id.gap_line);

        if (!TextUtils.isEmpty(title)) {
            titleText.setText(title);
        } else {
            titleText.setVisibility(View.GONE);
            // 没有标题时内容居中
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) descText.getLayoutParams();
            params.topMargin = DisplayUtil.dpToPx(20);
            descText.setLayoutParams(params);
        }
        descText.setText(desc);

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    // 设置富文本内容
    public void setSpanContent(SpannableString text) {
        descText.setText(text);
    }

    public void setCancelColor(int color) {
        cancelText.setTextColor(color);
    }

    public void setOkButtonText(String text) {
        okText.setText(text);
    }
    public TextView getOkButton(){
        return okText;
    }

    public void setCancelText(String text) {
        cancelText.setText(text);
    }

    public void setCancelTextHide() {
        cancelText.setVisibility(View.GONE);
        gapLine.setVisibility(View.GONE);
    }

    public void setOkButtonClickListener(final View.OnClickListener listener) {
        if (listener != null) {
            okText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dismiss();
                }
            });
        }
    }

    public void setCancelButtonClickListener(final View.OnClickListener listener) {
        if (listener != null) {
            cancelText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
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
}
