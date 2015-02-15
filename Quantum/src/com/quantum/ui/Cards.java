package com.quantum.ui;

import java.util.List;
import java.util.Map;

import com.quantum.R;
import com.quantum.bean.Content;
import com.quantum.db.DbManage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Cards {

	private LayoutInflater mLayoutInflater;
	private ViewGroup dismissableContainer;
	private LinearLayout linear_last;

	private Context context = null;
	private int card_num = 0;

	public Cards(Context context, ViewGroup dismissable, ScrollView scrollView,
			LinearLayout linear_last) {
		this.context = context;
		this.mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dismissableContainer = dismissable;
		this.linear_last = linear_last;

	}

	public void refresh() {
		int child_count = dismissableContainer.getChildCount();
		dismissableContainer.removeViews(1, child_count - 2);
	}

	public void refreshIndex() {

	}

	/**
	 * 
	 */
	public void getView() {

		int count = dismissableContainer.getChildCount();

		List<Content> list = (List<Content>) DbManage
				.findAllByBean(Content.class);

		if (list != null)
			for (Content con : list) {
				String key = con.getType();

				if (count > 1)
					for (int i = 0; i < count; i++) {
						String tag = dismissableContainer.getChildAt(i)
								.getTag().toString();
						if (tag.equals(key)) {
							dismissableContainer.removeViewAt(i);
						}
					}

				String data = con.getContent();
				int resID = context.getResources().getIdentifier("item_" + key,
						"layout", context.getPackageName());
				final View dismissableView = mLayoutInflater.inflate(resID,
						null);

				dismissableView.setTag(key);

				if (data == null)
					continue;
				viewHolder(dismissableView, key, data);

				dismissableView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
					}
				});
				// Create a generic swipe-to-dismiss touch listener.
				dismissableView
						.setOnTouchListener(new SwipeDismissTouchListener(
								dismissableView,
								null,
								new SwipeDismissTouchListener.DismissCallbacks() {
									@Override
									public boolean canDismiss(Object token) {
										return true;
									}

									@Override
									public void onDismiss(View view,
											Object token) {

									}
								}));
				dismissableContainer.addView(dismissableView);

				Animation animation = AnimationUtils.loadAnimation(context,
						R.anim.slide_up);
				dismissableView.startAnimation(animation);
			}

	}

	private void viewHolder(View view, String key, String data) {
		if ("weather".equals(key))
			new Weather(context, view, data);
		else if ("cook".equals(key))
			new Cooks(context, view, data);

	}
}
