package com.sxu.baselibrary.commonutils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

/*******************************************************************************
 * Description: 检查View的滑动位置
 *
 * Author: Freeman
 *
 * Date: 2017/12/26
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ScrollStateUtil {

	/**
	 * 是否滑动到顶部
	 * @param view
	 * @return
	 */
	public static boolean reachTop(View view) {
		if (view instanceof AbsListView) {
			return absListViewReachTop((AbsListView) view);
		} else if (view instanceof ScrollView) {
			return scrollViewReachTop((ScrollView) view);
		} else if (view instanceof RecyclerView) {
			return !view.canScrollVertically(-1);
		} else {
			return true;
		}
	}

	/**
	 * 是否滑动到底部
	 * @param view
	 * @return
	 */
	public static boolean reachBottom(View view) {
		if (view instanceof AbsListView) {
			return absListViewReachBottom((AbsListView) view);
		} else if (view instanceof RecyclerView) {
			return !view.canScrollVertically(1);
		} else {
			return false;
		}
	}

	/**
	 * AbsListView类型的View是否已滑动到顶部
	 * @param listView
	 * @return
	 */
	public static boolean absListViewReachTop(AbsListView listView) {
		int firstItemTop = 0;
		if (listView.getChildCount() > 0) {
			firstItemTop = listView.getChildAt(0).getTop() - listView.getPaddingTop();
		}

		return listView.getFirstVisiblePosition() == 0 && firstItemTop == 0;
	}

	/**
	 * ScrollView是否已滑动到顶部
	 * @param scrollView
	 * @return
	 */
	public static boolean scrollViewReachTop(ScrollView scrollView) {
		return scrollView.getScrollY() == 0;
	}

	/**
	 * RecycleView是否已滑动到顶部
	 * @param recyclerView
	 * @return
	 */
	public static boolean recycleViewReachTop(RecyclerView recyclerView) {
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		if (layoutManager == null || layoutManager.getItemCount() == 0) {
			return true;
		}

		View firstChild = recyclerView.getChildAt(0);
		int position = recyclerView.getChildAdapterPosition(firstChild);
		int firstItemTop = firstChild.getTop() - recyclerView.getPaddingTop();

		if (layoutManager instanceof LinearLayoutManager
				&& ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
			return position == 0 && firstItemTop == 0;
		} else if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
			return position < ((GridLayoutManager)layoutManager).getSpanCount() && firstItemTop == 0;
		} else {
			/**
			 * Nothing
			 */
		}

		return false;
	}

	/**
	 * AbsListView类型的View是否已滑动到底部
	 * @param listView
	 * @return
	 */
	public static boolean absListViewReachBottom(AbsListView listView) {
		if (listView.getChildCount() > 0) {
			int lastItemBottom = listView.getChildAt(listView.getChildCount() - 1).getBottom();
			int listHeight = listView.getBottom() - listView.getTop();
			if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1  && lastItemBottom <= listHeight) {
				return true;
			}
		}

		return false;
	}
}
