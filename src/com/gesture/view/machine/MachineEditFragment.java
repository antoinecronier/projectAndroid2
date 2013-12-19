/**************************************************************************
 * MachineEditFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.machine;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
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
import com.gesture.entity.Machine;
import com.gesture.entity.Zone;
import com.gesture.entity.LogTraca;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.widget.MultiEntityWidget;
import com.gesture.harmony.widget.SingleEntityWidget;
import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;
import com.gesture.provider.MachineProviderAdapter;
import com.gesture.provider.utils.MachineProviderUtils;
import com.gesture.provider.utils.ZoneProviderUtils;
import com.gesture.provider.utils.LogTracaProviderUtils;
import com.gesture.data.LogTracaSQLiteAdapter;

/** Machine create fragment.
 *
 * This fragment gives you an interface to edit a Machine.
 *
 * @see android.app.Fragment
 */
public class MachineEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Machine model = new Machine();

	/** curr.fields View. */
	/** nom View. */
	protected EditText nomView;
	/** The zone chooser component. */
	protected SingleEntityWidget zoneWidget;
	/** The zone Adapter. */
	protected SingleEntityWidget.EntityAdapter<Zone>
			zoneAdapter;
	/** The LogTracas chooser component. */
	protected MultiEntityWidget LogTracasWidget;
	/** The LogTracas Adapter. */
	protected MultiEntityWidget.EntityAdapter<LogTraca>
			LogTracasAdapter;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.nomView = (EditText) view.findViewById(
				R.id.machine_nom);
		this.zoneAdapter =
				new SingleEntityWidget.EntityAdapter<Zone>() {
			@Override
			public String entityToString(Zone item) {
				return String.valueOf(item.getId_zone());
			}
		};
		this.zoneWidget =
			(SingleEntityWidget) view.findViewById(R.id.machine_zone_button);
		this.zoneWidget.setAdapter(this.zoneAdapter);
		this.LogTracasAdapter =
				new MultiEntityWidget.EntityAdapter<LogTraca>() {
			@Override
			public String entityToString(LogTraca item) {
				return String.valueOf(item.getId_log());
			}
		};
		this.LogTracasWidget = (MultiEntityWidget) view.findViewById(
						R.id.machine_logtracas_button);
		this.LogTracasWidget.setAdapter(this.LogTracasAdapter);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getNom() != null) {
			this.nomView.setText(this.model.getNom());
		}

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setNom(this.nomView.getEditableText().toString());

		this.model.setZone(this.zoneAdapter.getSelectedItem());

		this.model.setLogTracas(this.LogTracasAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.nomView.getText().toString().trim())) {
			error = R.string.machine_nom_invalid_field_error;
		}
		if (this.zoneAdapter.getSelectedItem() == null) {
			error = R.string.machine_zone_invalid_field_error;
		}
		if (this.LogTracasAdapter.getCheckedItems().isEmpty()) {
			error = R.string.machine_logtracas_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_machine_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (Machine) intent.getParcelableExtra(
				Machine.PARCEL);

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
		private final Machine entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final MachineEditFragment fragment,
					final Machine entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.machine_progress_save_title),
					this.ctx.getString(
							R.string.machine_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new MachineProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("MachineEditFragment", e.getMessage());
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
						R.string.machine_error_create));
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
		private MachineEditFragment fragment;
		/** zone list. */
		private ArrayList<Zone> zoneList;
		/** LogTracas list. */
		private ArrayList<LogTraca> LogTracasList;
	/** LogTracas list. */
		private ArrayList<LogTraca> associatedLogTracasList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final MachineEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.machine_progress_load_relations_title),
				this.ctx.getString(
					R.string.machine_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.zoneList = 
				new ZoneProviderUtils(this.ctx).queryAll();
			this.LogTracasList = 
				new LogTracaProviderUtils(this.ctx).queryAll();
			Uri LogTracasUri = MachineProviderAdapter.MACHINE_URI;
			LogTracasUri = Uri.withAppendedPath(LogTracasUri, 
									String.valueOf(this.fragment.model.getId_machine()));
			LogTracasUri = Uri.withAppendedPath(LogTracasUri, "LogTracas");
			Cursor LogTracasCursor = 
					this.ctx.getContentResolver().query(
							LogTracasUri,
							new String[]{LogTracaSQLiteAdapter.ALIASED_COL_ID_LOG},
							null,
							null, 
							null);
			
			if (LogTracasCursor != null && LogTracasCursor.getCount() > 0) {
				this.associatedLogTracasList = new ArrayList<LogTraca>();
				while (LogTracasCursor.moveToNext()) {
					int LogTracasId = LogTracasCursor.getInt(
							LogTracasCursor.getColumnIndex(
									LogTracaSQLiteAdapter.COL_ID_LOG));
					for (LogTraca LogTracas : this.LogTracasList) {
						if (LogTracas.getId_log() == LogTracasId) {
							this.associatedLogTracasList.add(LogTracas);
						}
					}
				}
				LogTracasCursor.close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onZoneLoaded(this.zoneList);
			this.fragment.model.setLogTracas(this.associatedLogTracasList);
			this.fragment.onLogTracasLoaded(this.LogTracasList);

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
	 * Called when zone have been loaded.
	 * @param items The loaded items
	 */
	protected void onZoneLoaded(ArrayList<Zone> items) {
		this.zoneAdapter.loadData(items);
		
		for (Zone item : items) {
			if (item.getId_zone() == this.model.getZone().getId_zone()) {
				this.zoneAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when LogTracas have been loaded.
	 * @param items The loaded items
	 */
	protected void onLogTracasLoaded(ArrayList<LogTraca> items) {
		this.LogTracasAdapter.loadData(items);
		this.LogTracasAdapter.setCheckedItems(this.model.getLogTracas());
	}
}
