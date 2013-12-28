/**************************************************************************
 * MachineCreateFragment.java, WindowsGesture2 Android
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.gesture.provider.utils.MachineProviderUtils;
import com.gesture.provider.utils.ZoneProviderUtils;
import com.gesture.provider.utils.LogTracaProviderUtils;

/**
 * Machine create fragment.
 *
 * This fragment gives you an interface to create a Machine.
 */
public class MachineCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Machine model = new Machine();

	/** Fields View. */
	/** nom View. */
	protected EditText nomView;
	/** The zone chooser component. */
	protected SingleEntityWidget zoneWidget;
	/** The zone Adapter. */
	protected SingleEntityWidget.EntityAdapter<Zone> 
				zoneAdapter;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.nomView =
			(EditText) view.findViewById(R.id.machine_nom);
		this.zoneAdapter = 
				new SingleEntityWidget.EntityAdapter<Zone>() {
			@Override
			public String entityToString(Zone item) {
				return item.getNom();
			}
		};
		this.zoneWidget =
			(SingleEntityWidget) view.findViewById(R.id.machine_zone_button);
		this.zoneWidget.setAdapter(this.zoneAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getNom() != null) {
			this.nomView.setText(this.model.getNom());
		}

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setNom(this.nomView.getEditableText().toString());

		this.model.setZone(this.zoneAdapter.getSelectedItem());

	}

	/** Check data is valid. MODIFIED
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
		//Suppression validation du log null
//		if (this.LogTracasAdapter.getCheckedItems().isEmpty()) {
//			error = R.string.machine_logtracas_invalid_field_error;
//		}
	
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
		final View view = inflater.inflate(
				R.layout.fragment_machine_create,
				container,
				false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class CreateTask extends AsyncTask<Void, Void, Uri> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to persist. */
		private final Machine entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final MachineCreateFragment fragment,
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
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new MachineProviderUtils(this.ctx).insert(
						this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				final HarmonyFragmentActivity activity =
										 (HarmonyFragmentActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
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
		private MachineCreateFragment fragment;
		/** zone list. */
		private ArrayList<Zone> zoneList;
		/** LogTracas list. */
		private ArrayList<LogTraca> LogTracasList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final MachineCreateFragment fragment) {
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
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.zoneAdapter.loadData(this.zoneList);
			this.progress.dismiss();
		}
	}

	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new CreateTask(this, this.model).execute();
		}
	}
}
