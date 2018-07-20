package com.sxu.simpleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.sxu.basecomponent.activity.BaseActivity;

public class BaseHomeActivity extends BaseActivity {

	private int toolbarStyle;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_home_layout;
	}

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(TOOL_BAR_STYLE_NORMAL);
	}

	@Override
	protected void getViews() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		Button entryButton = (Button) findViewById(R.id.entry_button);
		Button entryButton2 = (Button) findViewById(R.id.entry_button2);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.no_toolbar_radio) {
					toolbarStyle = 0;
				} else if (checkedId == R.id.normal_radio) {
					toolbarStyle = 1;
				} else if (checkedId == R.id.transparent_radio) {
					toolbarStyle = 2;
				} else if (checkedId == R.id.translucent_radio) {
					toolbarStyle = 3;
				}
			}
		});

		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivityStyleActivity.enter(context, toolbarStyle);
			}
		});

		entryButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseProgressActivityStyleActivity.enter(context, toolbarStyle);
			}
		});
	}

	@Override
	protected void initActivity() {

	}
}
