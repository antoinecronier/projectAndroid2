/**************************************************************************
 * CommandeListAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

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
import com.gesture.entity.Commande;

/**
 * List adapter for Commande entity.
 */
public class CommandeListAdapter
		extends HeaderAdapter<Commande>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public CommandeListAdapter(Context ctx) {
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
			super(context, attrs, R.layout.row_commande);
		}

		/** Populate row with a Commande.
		 *
		 * @param model Commande data
		 */
		public void populate(final Commande model) {
			View convertView = this.getInnerLayout();
			TextView clientView =
				(TextView) convertView.findViewById(
						R.id.row_commande_client);
			TextView dateCreationView =
				(TextView) convertView.findViewById(
						R.id.row_commande_datecreation);
			TextView dateFinView =
				(TextView) convertView.findViewById(
						R.id.row_commande_datefin);
			TextView dateLivraisonView =
				(TextView) convertView.findViewById(
						R.id.row_commande_datelivraison);
			TextView avancementView =
				(TextView) convertView.findViewById(
						R.id.row_commande_avancement);


			clientView.setText(
					String.valueOf(model.getClient().getId_client()));
			if (model.getDateCreation() != null) {
				dateCreationView.setText(model.getDateCreation());
			}
			if (model.getDateFin() != null) {
				dateFinView.setText(model.getDateFin());
			}
			if (model.getDateLivraison() != null) {
				dateLivraisonView.setText(model.getDateLivraison());
			}
			avancementView.setText(String.valueOf(model.getAvancement()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class CommandeSectionIndexer
					extends HeaderSectionIndexer<Commande>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public CommandeSectionIndexer(List<Commande> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(Commande item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				Commande item,
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
	public int getPosition(Commande item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId_cmd() == this.getItem(i).getId_cmd()) {
					result = i;
				}
			}
		}
		return result;
	}
}
