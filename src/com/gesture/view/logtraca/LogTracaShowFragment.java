/**************************************************************************
 * LogTracaShowFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.logtraca;

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
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;
import com.gesture.entity.LogTraca;
import com.gesture.harmony.util.DateUtils;
import com.gesture.harmony.view.DeleteDialog;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.view.MultiLoader;
import com.gesture.harmony.view.MultiLoader.UriLoadedCallback;
import com.gesture.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.gesture.provider.utils.LogTracaProviderUtils;
import com.gesture.provider.LogTracaProviderAdapter;

/** LogTraca show fragment.
 *
 * This fragment gives you an interface to show a LogTraca.
 * 
 * @see android.app.Fragment
 */
public class LogTracaShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected LogTraca model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** produit View. */
	protected TextView produitView;
	/** machine View. */
	protected TextView machineView;
	/** user View. */
	protected TextView userView;
	/** duree View. */
	protected TextView dureeView;
	/** dateEntre View. */
	protected TextView dateEntreView;
	/** dateSortie View. */
	protected TextView dateSortieView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no LogTraca. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.produitView =
			(TextView) view.findViewById(
					R.id.logtraca_produit);
		this.machineView =
			(TextView) view.findViewById(
					R.id.logtraca_machine);
		this.userView =
			(TextView) view.findViewById(
					R.id.logtraca_user);
		this.dureeView =
			(TextView) view.findViewById(
					R.id.logtraca_duree);
		this.dateEntreView =
			(TextView) view.findViewById(
					R.id.logtraca_dateentre);
		this.dateSortieView =
			(TextView) view.findViewById(
					R.id.logtraca_datesortie);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.logtraca_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.logtraca_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getProduit() != null) {
			this.produitView.setText(
					String.valueOf(this.model.getProduit().getId_produit()));
		}
		if (this.model.getMachine() != null) {
			this.machineView.setText(
					String.valueOf(this.model.getMachine().getId_machine()));
		}
		if (this.model.getUser() != null) {
			this.userView.setText(
					String.valueOf(this.model.getUser().getId_user()));
		}
		if (this.model.getDuree() != null) {
			this.dureeView.setText(this.model.getDuree());
		}
		if (this.model.getDateEntre() != null) {
			this.dateEntreView.setText(this.model.getDateEntre());
		}
		if (this.model.getDateSortie() != null) {
			this.dateSortieView.setText(this.model.getDateSortie());
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
        				R.layout.fragment_logtraca_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((LogTraca) intent.getParcelableExtra(LogTraca.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The LogTraca to get the data from.
	 */
	public void update(LogTraca item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					LogTracaProviderAdapter.LOGTRACA_URI 
					+ "/" 
					+ this.model.getId_log();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogTracaShowFragment.this.onLogTracaLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/produit"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogTracaShowFragment.this.onProduitLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/machine"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogTracaShowFragment.this.onMachineLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/user"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogTracaShowFragment.this.onUserLoaded(c);
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
	public void onLogTracaLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new LogTracaSQLiteAdapter(getActivity()).cursorToItem(
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
	public void onProduitLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setProduit(
							new ProduitSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setProduit(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onMachineLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setMachine(
							new MachineSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setMachine(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onUserLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setUser(
							new UserSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setUser(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the LogTracaEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									LogTracaEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("LogTraca", this.model);
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
		private LogTraca item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build LogTracaSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final LogTraca item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new LogTracaProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				LogTracaShowFragment.this.onPostDelete();
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

