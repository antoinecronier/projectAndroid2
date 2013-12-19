/**************************************************************************
 * LogTracaEditFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.logtraca;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.gesture.R;
import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;
import com.gesture.entity.Machine;
import com.gesture.entity.User;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.widget.DateTimeWidget;

import com.gesture.harmony.widget.SingleEntityWidget;
import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;

import com.gesture.provider.utils.LogTracaProviderUtils;
import com.gesture.provider.utils.ProduitProviderUtils;
import com.gesture.provider.utils.MachineProviderUtils;
import com.gesture.provider.utils.UserProviderUtils;

/** LogTraca create fragment.
 *
 * This fragment gives you an interface to edit a LogTraca.
 *
 * @see android.app.Fragment
 */
public class LogTracaEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected LogTraca model = new LogTraca();

	/** curr.fields View. */
	/** The produit chooser component. */
	protected SingleEntityWidget produitWidget;
	/** The produit Adapter. */
	protected SingleEntityWidget.EntityAdapter<Produit>
			produitAdapter;
	/** The machine chooser component. */
	protected SingleEntityWidget machineWidget;
	/** The machine Adapter. */
	protected SingleEntityWidget.EntityAdapter<Machine>
			machineAdapter;
	/** The user chooser component. */
	protected SingleEntityWidget userWidget;
	/** The user Adapter. */
	protected SingleEntityWidget.EntityAdapter<User>
			userAdapter;
	/** duree View. */
	protected EditText dureeView;
	/** dateEntre View. */
	protected EditText dateEntreView;
	/** dateSortie View. */
	protected EditText dateSortieView;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.produitAdapter =
				new SingleEntityWidget.EntityAdapter<Produit>() {
			@Override
			public String entityToString(Produit item) {
				return String.valueOf(item.getId_produit());
			}
		};
		this.produitWidget =
			(SingleEntityWidget) view.findViewById(R.id.logtraca_produit_button);
		this.produitWidget.setAdapter(this.produitAdapter);
		this.machineAdapter =
				new SingleEntityWidget.EntityAdapter<Machine>() {
			@Override
			public String entityToString(Machine item) {
				return String.valueOf(item.getId_machine());
			}
		};
		this.machineWidget =
			(SingleEntityWidget) view.findViewById(R.id.logtraca_machine_button);
		this.machineWidget.setAdapter(this.machineAdapter);
		this.userAdapter =
				new SingleEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId_user());
			}
		};
		this.userWidget =
			(SingleEntityWidget) view.findViewById(R.id.logtraca_user_button);
		this.userWidget.setAdapter(this.userAdapter);
		this.dureeView = (EditText) view.findViewById(
				R.id.logtraca_duree);
		this.dateEntreView = (EditText) view.findViewById(
				R.id.logtraca_dateentre);
		this.dateSortieView = (EditText) view.findViewById(
				R.id.logtraca_datesortie);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getDuree() != null) {
			this.dureeView.setText(this.model.getDuree());
		}
		if (this.model.getDateEntre() != null) {
			this.dateEntreView.setText(this.model.getDateEntre());
		}
		if (this.model.getDateSortie() != null) {
			this.dateSortieView.setText(this.model.getDateSortie());
		}

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setProduit(this.produitAdapter.getSelectedItem());

		this.model.setMachine(this.machineAdapter.getSelectedItem());

		this.model.setUser(this.userAdapter.getSelectedItem());

		this.model.setDuree(this.dureeView.getEditableText().toString());

		this.model.setDateEntre(this.dateEntreView.getEditableText().toString());

		this.model.setDateSortie(this.dateSortieView.getEditableText().toString());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.produitAdapter.getSelectedItem() == null) {
			error = R.string.logtraca_produit_invalid_field_error;
		}
		if (this.machineAdapter.getSelectedItem() == null) {
			error = R.string.logtraca_machine_invalid_field_error;
		}
		if (this.userAdapter.getSelectedItem() == null) {
			error = R.string.logtraca_user_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dureeView.getText().toString().trim())) {
			error = R.string.logtraca_duree_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dateEntreView.getText().toString().trim())) {
			error = R.string.logtraca_dateentre_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dateSortieView.getText().toString().trim())) {
			error = R.string.logtraca_datesortie_invalid_field_error;
		}
	
		if (error > 0) {
			Toast.makeText(this.getActivity(),
				this.getActivity().getString(error),
				Toast.LENGTH_SHORT).show();
		}
		return error == 0;
	}
	@Override
	public View onCreateView(
				LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		final View view =
				inflater.inflate(R.layout.fragment_logtraca_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (LogTraca) intent.getParcelableExtra(
				LogTraca.PARCEL);

		this.initializeComponent(view);
		this.loadData();

		return view;
	}

	/**
	 * This class will update the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to update. */
		private final LogTraca entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final LogTracaEditFragment fragment,
					final LogTraca entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.logtraca_progress_save_title),
					this.ctx.getString(
							R.string.logtraca_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new LogTracaProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("LogTracaEditFragment", e.getMessage());
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result > 0) {
				final HarmonyFragmentActivity activity =
						(HarmonyFragmentActivity) this.ctx;
				activity.setResult(HarmonyFragmentActivity.RESULT_OK);
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(this.ctx.getString(
						R.string.logtraca_error_create));
				builder.setPositiveButton(
						this.ctx.getString(android.R.string.yes),
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
																int which) {

							}
						});
				builder.show();
			}

			this.progress.dismiss();
		}
	}


	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class LoadTask extends AsyncTask<Void, Void, Void> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Progress Dialog. */
		private ProgressDialog progress;
		/** Fragment. */
		private LogTracaEditFragment fragment;
		/** produit list. */
		private ArrayList<Produit> produitList;
		/** machine list. */
		private ArrayList<Machine> machineList;
		/** user list. */
		private ArrayList<User> userList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final LogTracaEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.logtraca_progress_load_relations_title),
				this.ctx.getString(
					R.string.logtraca_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.produitList = 
				new ProduitProviderUtils(this.ctx).queryAll();
			this.machineList = 
				new MachineProviderUtils(this.ctx).queryAll();
			this.userList = 
				new UserProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onProduitLoaded(this.produitList);
			this.fragment.onMachineLoaded(this.machineList);
			this.fragment.onUserLoaded(this.userList);

			this.progress.dismiss();
		}
	}

	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

	/**
	 * Called when produit have been loaded.
	 * @param items The loaded items
	 */
	protected void onProduitLoaded(ArrayList<Produit> items) {
		this.produitAdapter.loadData(items);
		
		for (Produit item : items) {
			if (item.getId_produit() == this.model.getProduit().getId_produit()) {
				this.produitAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when machine have been loaded.
	 * @param items The loaded items
	 */
	protected void onMachineLoaded(ArrayList<Machine> items) {
		this.machineAdapter.loadData(items);
		
		for (Machine item : items) {
			if (item.getId_machine() == this.model.getMachine().getId_machine()) {
				this.machineAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when user have been loaded.
	 * @param items The loaded items
	 */
	protected void onUserLoaded(ArrayList<User> items) {
		this.userAdapter.loadData(items);
		
		for (User item : items) {
			if (item.getId_user() == this.model.getUser().getId_user()) {
				this.userAdapter.selectItem(item);
			}
		}
	}
}
