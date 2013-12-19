/**************************************************************************
 * ProduitListAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.produit;

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
import com.gesture.entity.Produit;

/**
 * List adapter for Produit entity.
 */
public class ProduitListAdapter
		extends HeaderAdapter<Produit>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ProduitListAdapter(Context ctx) {
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
			super(context, attrs, R.layout.row_produit);
		}

		/** Populate row with a Produit.
		 *
		 * @param model Produit data
		 */
		public void populate(final Produit model) {
			View convertView = this.getInnerLayout();
			TextView typeView =
				(TextView) convertView.findViewById(
						R.id.row_produit_type);
			TextView etatView =
				(TextView) convertView.findViewById(
						R.id.row_produit_etat);
			TextView materielView =
				(TextView) convertView.findViewById(
						R.id.row_produit_materiel);
			TextView commandeView =
				(TextView) convertView.findViewById(
						R.id.row_produit_commande);
			TextView quantiteView =
				(TextView) convertView.findViewById(
						R.id.row_produit_quantite);
			TextView avancementView =
				(TextView) convertView.findViewById(
						R.id.row_produit_avancement);


			if (model.getType() != null) {
				typeView.setText(model.getType().name());
			}
			if (model.getEtat() != null) {
				etatView.setText(model.getEtat().name());
			}
			if (model.getMateriel() != null) {
				materielView.setText(model.getMateriel().name());
			}
			commandeView.setText(
					String.valueOf(model.getCommande().getId_cmd()));
			quantiteView.setText(String.valueOf(model.getQuantite()));
			avancementView.setText(String.valueOf(model.getAvancement()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class ProduitSectionIndexer
					extends HeaderSectionIndexer<Produit>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public ProduitSectionIndexer(List<Produit> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(Produit item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				Produit item,
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
	public int getPosition(Produit item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId_produit() == this.getItem(i).getId_produit()) {
					result = i;
				}
			}
		}
		return result;
	}
}
