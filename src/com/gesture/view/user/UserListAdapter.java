/**************************************************************************
 * UserListAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.user;

import java.util.List;

import com.gesture.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.gesture.entity.User;

/**
 * List adapter for User entity.
 */
public class UserListAdapter
		extends HeaderAdapter<User>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserListAdapter(Context ctx) {
		super(ctx);
	}

	/** Holder row. */
	private static class ViewHolder extends SelectionItemView {

		/**
		 * Constructor.
		 *
		 * @param context The context
		 */
		public ViewHolder(Context context) {
			this(context, null);
		}
		
		/**
		 * Constructor.
		 *
		 * @param context The context
		 * @param attrs The attribute set
		 */
		public ViewHolder(Context context, AttributeSet attrs) {
			super(context, attrs, R.layout.row_user);
		}

		/** Populate row with a User.
		 *
		 * @param model User data
		 */
		public void populate(final User model) {
			View convertView = this.getInnerLayout();
			TextView loginView =
				(TextView) convertView.findViewById(
						R.id.row_user_login);
			TextView passwordView =
				(TextView) convertView.findViewById(
						R.id.row_user_password);


			if (model.getLogin() != null) {
				loginView.setText(model.getLogin());
			}
			if (model.getPassword() != null) {
				passwordView.setText(model.getPassword());
			}
		}
	}

	/** Section indexer for this entity's list. */
	public static class UserSectionIndexer
					extends HeaderSectionIndexer<User>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public UserSectionIndexer(List<User> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(User item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				User item,
				int position) {
    	final ViewHolder view;
    	
    	if (itemView != null) {
    		view = (ViewHolder) itemView;
    	} else {
    		view = new ViewHolder(this.getContext());
		}

    	if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
    		view.setActivatedStateSupported(false);
		}
    	
    	view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

	@Override
	public int getPosition(User item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId_user() == this.getItem(i).getId_user()) {
					result = i;
				}
			}
		}
		return result;
	}
}
