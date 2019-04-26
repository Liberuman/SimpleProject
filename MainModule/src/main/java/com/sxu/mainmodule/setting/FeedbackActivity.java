package com.sxu.mainmodule.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.commonutils.VerificationUtil;
import com.sxu.mainmodule.R;

/*******************************************************************************
 * Description: 意见反馈
 *
 * Author: Freeman
 *
 * Date: 2019/3/22
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class FeedbackActivity extends BaseActivity {

	private EditText contentEdit;
	private EditText phoneEdit;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_feedback_layout;
	}

	@Override
	protected void getViews() {
		contentEdit = (EditText) findViewById(R.id.content_edit);
		phoneEdit = (EditText) findViewById(R.id.phone_edit);
	}

	@Override
	protected void initActivity() {
		toolbar.setTitle("意见反馈");
		toolbar.setRightTextAndEvent("提交", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitFeedback();
			}
		});
	}

	private void submitFeedback() {
		String feedbackContent = contentEdit.getText().toString();
		if (TextUtils.isEmpty(feedbackContent)) {
			ToastUtil.show("请先输入您的宝贵意见~");
			return;
		}

		String phone = phoneEdit.getText().toString();
		if (!TextUtils.isEmpty(phone) &&
				!(VerificationUtil.isValidTelNumber(phone) || VerificationUtil.isValidEmailAddress(phone))) {
			ToastUtil.show("请输入正确的联系方式~");
			return;
		}

		realSubmitFeedback(feedbackContent, phone);
	}

	/**
	 * 提交意见内容到服务器
	 * @param content
	 * @param phone
	 */
	private void realSubmitFeedback(String content, String phone) {
		ToastUtil.show("意见提交成功");
		finish();
	}
}
