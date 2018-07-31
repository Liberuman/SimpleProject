package com.sxu.module1;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;

public class BaseActivityStyleActivity extends BaseActivity {

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_activity_style_layout;
	}

	@Override
	protected void getViews() {
		TextView descText = (TextView) findViewById(R.id.desc_text);
		if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
			descText.setText("没有Toolbar的布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_NORMAL) {
			descText.setText("有Toolbar的线性布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_TRANSPARENT) {
			descText.setText("透明Toolbar的帧布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_TRANSLUCENT) {
			descText.setText("半透明Toolbar的帧布局");
		}
	}

	@Override
	protected void initActivity() {

	}

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(getIntent().getIntExtra("toolbarStyle", 0));
	}

	public static void enter(Context context, int toolbarStyle) {
		Intent intent = new Intent(context, BaseActivityStyleActivity.class);
		intent.putExtra("toolbarStyle", toolbarStyle);
		context.startActivity(intent);
	}
}
