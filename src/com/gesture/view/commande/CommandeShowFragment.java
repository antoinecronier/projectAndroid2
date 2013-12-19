/**************************************************************************
 * CommandeShowFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

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
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit;
import com.gesture.harmony.util.DateUtils;
import com.gesture.harmony.view.DeleteDialog;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.view.MultiLoader;
import com.gesture.harmony.view.MultiLoader.UriLoadedCallback;
import com.gesture.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.gesture.provider.utils.CommandeProviderUtils;
import com.gesture.provider.CommandeProviderAdapter;

/** Commande show fragment.
 *
 * This fragment gives you an interface to show a Commande.
 * 
 * @see android.app.Fragment
 */
public class CommandeShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected Commande model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** client View. */
	protected TextView clientView;
	/** dateCreation View. */
	protected TextView dateCreationView;
	/** dateFin View. */
	protected TextView dateFinView;
	/** dateLivraison View. */
	protected TextView dateLivraisonView;
	/** avancement View. */
	protected TextView avancementView;
	/** produits View. */
	protected TextView produitsView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no Commande. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.clientView =
			(TextView) view.findViewById(
					R.id.commande_client);
		this.dateCreationView =
			(TextView) view.findViewById(
					R.id.commande_datecreation);
		this.dateFinView =
			(TextView) view.findViewById(
					R.id.commande_datefin);
		this.dateLivraisonView =
			(TextView) view.findViewById(
					R.id.commande_datelivraison);
		this.avancementView =
			(TextView) view.findViewById(
					R.id.commande_avancement);
		this.produitsView =
			(TextView) view.findViewById(
					R.id.commande_produits);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.commande_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.commande_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getClient() != null) {
			this.clientView.setText(
					String.valueOf(this.model.getClient().getId_client()));
		}
		if (this.model.getDateCreation() != null) {
			this.dateCreationView.setText(this.model.getDateCreation());
		}
		if (this.model.getDateFin() != null) {
			this.dateFinView.setText(this.model.getDateFin());
		}
		if (this.model.getDateLivraison() != null) {
			this.dateLivraisonView.setText(this.model.getDateLivraison());
		}
		this.avancementView.setText(String.valueOf(this.model.getAvancement()));
		if (this.model.getProduits() != null) {
			String produitsValue = "";
			for (Produit item : this.model.getProduits()) {
				produitsValue += item.getId_produit() + ",";
			}
			this.produitsView.setText(produitsValue);
		}
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
        				R.layout.fragment_commande_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Commande) intent.getParcelableExtra(Commande.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The Commande to get the data from.
	 */
	public void update(Commande item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					CommandeProviderAdapter.COMMANDE_URI 
					+ "/" 
					+ this.model.getId_cmd();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					CommandeShowFragment.this.onCommandeLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/client"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					CommandeShowFragment.this.onClientLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/produits"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					CommandeShowFragment.this.onProduitsLoaded(c);
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
	public void onCommandeLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new CommandeSQLiteAdapter(getActivity()).cursorToItem(
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
	public void onClientLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setClient(
							new ClientSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setClient(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onProduitsLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				this.model.setProduits(
						new ProduitSQLiteAdapter(getActivity()).cursorToItems(c));
					this.loadData();
			} else {
				this.model.setProduits(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the CommandeEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									CommandeEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("Commande", this.model);
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
		private Commande item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build CommandeSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final Commande item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new CommandeProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				CommandeShowFragment.this.onPostDelete();
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

