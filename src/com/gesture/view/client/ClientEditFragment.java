/**************************************************************************
 * ClientEditFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.client;



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
import com.gesture.entity.Client;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyFragment;

import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;

import com.gesture.provider.utils.ClientProviderUtils;

/** Client create fragment.
 *
 * This fragment gives you an interface to edit a Client.
 *
 * @see android.app.Fragment
 */
public class ClientEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Client model = new Client();

	/** curr.fields View. */
	/** nom View. */
	protected EditText nomView;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.nomView = (EditText) view.findViewById(
				R.id.client_nom);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getNom() != null) {
			this.nomView.setText(this.model.getNom());
		}


	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setNom(this.nomView.getEditableText().toString());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.nomView.getText().toString().trim())) {
			error = R.string.client_nom_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_client_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (Client) intent.getParcelableExtra(
				Client.PARCEL);

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
		private final Client entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final ClientEditFragment fragment,
					final Client entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.client_progress_save_title),
					this.ctx.getString(
							R.string.client_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new ClientProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("ClientEditFragment", e.getMessage());
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
						R.string.client_error_create));
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



	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

}
