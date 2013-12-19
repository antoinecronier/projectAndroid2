/**************************************************************************
 * LogTracaListAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.logtraca;

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
import com.gesture.entity.LogTraca;

/**
 * List adapter for LogTraca entity.
 */
public class LogTracaListAdapter
		extends HeaderAdapter<LogTraca>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogTracaListAdapter(Context ctx) {
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
			super(context, attrs, R.layout.row_logtraca);
		}

		/** Populate row with a LogTraca.
		 *
		 * @param model LogTraca data
		 */
		public void populate(final LogTraca model) {
			View convertView = this.getInnerLayout();
			TextView produitView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_produit);
			TextView machineView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_machine);
			TextView userView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_user);
			TextView dureeView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_duree);
			TextView dateEntreView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_dateentre);
			TextView dateSortieView =
				(TextView) convertView.findViewById(
						R.id.row_logtraca_datesortie);


			produitView.setText(
					String.valueOf(model.getProduit().getId_produit()));
			machineView.setText(
					String.valueOf(model.getMachine().getId_machine()));
			userView.setText(
					String.valueOf(model.getUser().getId_user()));
			if (model.getDuree() != null) {
				dureeView.setText(model.getDuree());
			}
			if (model.getDateEntre() != null) {
				dateEntreView.setText(model.getDateEntre());
			}
			if (model.getDateSortie() != null) {
				dateSortieView.setText(model.getDateSortie());
			}
		}
	}

	/** Section indexer for this entity's list. */
	public static class LogTracaSectionIndexer
					extends HeaderSectionIndexer<LogTraca>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public LogTracaSectionIndexer(List<LogTraca> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(LogTraca item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				LogTraca item,
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
	public int getPosition(LogTraca item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId_log() == this.getItem(i).getId_log()) {
					result = i;
				}
			}
		}
		return result;
	}
}
