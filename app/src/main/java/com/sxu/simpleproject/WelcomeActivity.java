package com.sxu.simpleproject;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.utils.PreferenceTag;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;

/**
 * 说明：新用户引导页通常都是几张图片，且一般都是大图，所以在显示时需要进行相应的优化，否则
 * 可能在低端机上出现卡顿甚至OOM。具体的图片优化可参考：
 * https://juejin.im/post/5af84f4b51882542714fdaa9
 *
 * 一般情况下，最后一页有一个进入的按钮，建议将此按钮直接作为图片的一部分进行显示，然后在相应
 * 的位置上放一个透明的Button, 在滑到最后一页时显示，其他页隐藏。
 */

/*******************************************************************************
 * Description: 新用户引导页
 *
 * Author: Freeman
 *
 * Date: 2018/8/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class WelcomeActivity extends BaseActivity {

	private Button entryButton;
	private ViewPager guidePager;

	private static int[] items = new int[] {
			R.drawable.guide_one_image,
			R.drawable.guide_two_image,
			R.drawable.guide_three_image,
			R.drawable.guide_four_image,
	};

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(TOOL_BAR_STYLE_NONE);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_new_user_guide_layout;
	}

	@Override
	protected void getViews() {
		entryButton = (Button) findViewById(R.id.entry_button);
		guidePager = (ViewPager) findViewById(R.id.guide_pager);
	}

	@Override
	protected void initActivity() {
		guidePager.setAdapter(new GuideAdapter());

		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferencesManager.putBoolean(PreferenceTag.KEY_IS_NEW_USER, false);
				ARouter.getInstance().build("/main/home").navigation();
				finish();
			}
		});
		guidePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (positionOffset > 0 && positionOffset < 1) {
					entryButton.setVisibility(View.GONE);
				} else {
					if (position == items.length - 1) {
						entryButton.setVisibility(View.VISIBLE);
					} else {
						entryButton.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void onPageSelected(int position) {
//				if (position == items.length - 1) {
//					entryButton.setVisibility(View.VISIBLE);
//				} else {
//					entryButton.setVisibility(View.GONE);
//				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private static class GuideAdapter extends PagerAdapter {

		@Override
		public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
			return view.equals(object);
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			ImageView imageView = new ImageView(container.getContext());
			imageView.setImageResource(items[position]);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
			container.removeView((View) object);
		}
	}
}
