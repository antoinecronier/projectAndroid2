/**************************************************************************
 * ProduitShowFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.produit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.entity.Produit;
import com.gesture.harmony.view.DeleteDialog;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.view.MultiLoader;
import com.gesture.harmony.view.MultiLoader.UriLoadedCallback;
import com.gesture.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.gesture.provider.utils.ProduitProviderUtils;
import com.gesture.provider.ProduitProviderAdapter;

/** Produit show fragment.
 *
 * This fragment gives you an interface to show a Produit.
 * 
 * @see android.app.Fragment
 */
public class ProduitShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected Produit model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** type View. */
	protected TextView typeView;
	/** etat View. */
	protected TextView etatView;
	/** materiel View. */
	protected TextView materielView;
	/** commande View. */
	protected TextView commandeView;
	/** quantite View. */
	protected TextView quantiteView;
	/** avancement View. */
	protected TextView avancementView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no Produit. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.typeView =
			(TextView) view.findViewById(
					R.id.produit_type);
		this.etatView =
			(TextView) view.findViewById(
					R.id.produit_etat);
		this.materielView =
			(TextView) view.findViewById(
					R.id.produit_materiel);
		this.commandeView =
			(TextView) view.findViewById(
					R.id.produit_commande);
		this.quantiteView =
			(TextView) view.findViewById(
					R.id.produit_quantite);
		this.avancementView =
			(TextView) view.findViewById(
					R.id.produit_avancement);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.produit_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.produit_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getType() != null) {
			this.typeView.setText(this.model.getType().toString());
		}
		if (this.model.getEtat() != null) {
			this.etatView.setText(this.model.getEtat().toString());
		}
		if (this.model.getMateriel() != null) {
			this.materielView.setText(this.model.getMateriel().toString());
		}
		if (this.model.getCommande() != null) {
			this.commandeView.setText(
					String.valueOf(this.model.getCommande().getId_cmd()));
		}
		this.quantiteView.setText(String.valueOf(this.model.getQuantite()));
		this.avancementView.setText(String.valueOf(this.model.getAvancement()));
		} else {
    		this.dataLayout.setVisibility(View.GONE);
    		this.emptyText.setVisibility(View.VISIBLE);
    	}
    }

    @Override
    public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

    	// Inflate the layout for this fragment
        final View view =
        		inflater.inflate(
        				R.layout.fragment_produit_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Produit) intent.getParcelableExtra(Produit.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The Produit to get the data from.
	 */
	public void update(Produit item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					ProduitProviderAdapter.PRODUIT_URI 
					+ "/" 
					+ this.model.getId_produit();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					ProduitShowFragment.this.onProduitLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/commande"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					ProduitShowFragment.this.onCommandeLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.init();
		}
    }

	/**
	 * Called when the entity has been loaded.
	 * 
	 * @param c The cursor of this entity
	 */
	public void onProduitLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new ProduitSQLiteAdapter(getActivity()).cursorToItem(
						c,
						this.model);
			this.loadData();
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onCommandeLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setCommande(
							new CommandeSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setCommande(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the ProduitEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									ProduitEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("Produit", this.model);
		intent.putExtras(extras);

		this.getActivity().startActivity(intent);
	}

	/**
	 * Shows a confirmation dialog.
	 */
	@Override
	public void onClickDelete() {
		new DeleteDialog(this.getActivity(), this).show();
	}

	@Override
	public void onDeleteDialogClose(boolean ok) {
		if (ok) {
			new DeleteTask(this.getActivity(), this.model).execute();
		}
	}
	
	/** 
	 * Called when delete task is done.
	 */	
	public void onPostDelete() {
		if (this.deleteCallback != null) {
			this.deleteCallback.onItemDeleted();
		}
	}

	/**
	 * This class will remove the entity into the DB.
	 * It runs asynchronously.
	 */
	private class DeleteTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private Context ctx;
		/** Entity to delete. */
		private Produit item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build ProduitSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final Produit item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new ProduitProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				ProduitShowFragment.this.onPostDelete();
			}
			super.onPostExecute(result);
		}
		
		

	}
	
	/**
	 * Callback for item deletion.
	 */ 
	public interface DeleteCallback {
		/** Called when current item has been deleted. */
		void onItemDeleted();
	}
}

