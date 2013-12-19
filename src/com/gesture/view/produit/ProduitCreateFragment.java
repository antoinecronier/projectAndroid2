/**************************************************************************
 * ProduitCreateFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.produit;

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
import com.gesture.entity.Produit;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.entity.Produit.ProduitEtat;
import com.gesture.entity.Produit.ProduitMateriel;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyFragment;

import com.gesture.harmony.widget.SingleEntityWidget;
import com.gesture.harmony.widget.EnumSpinner;
import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;
import com.gesture.provider.utils.ProduitProviderUtils;
import com.gesture.provider.utils.CommandeProviderUtils;

/**
 * Produit create fragment.
 *
 * This fragment gives you an interface to create a Produit.
 */
public class ProduitCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Produit model = new Produit();

	/** Fields View. */
	/** type View. */
	protected EnumSpinner typeView;
	/** etat View. */
	protected EnumSpinner etatView;
	/** materiel View. */
	protected EnumSpinner materielView;
	/** The commande chooser component. */
	protected SingleEntityWidget commandeWidget;
	/** The commande Adapter. */
	protected SingleEntityWidget.EntityAdapter<Commande> 
				commandeAdapter;
	/** quantite View. */
	protected EditText quantiteView;
	/** avancement View. */
	protected EditText avancementView;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.typeView =
			(EnumSpinner) view.findViewById(R.id.produit_type);
		this.typeView.setEnum(ProduitType.class);
		this.etatView =
			(EnumSpinner) view.findViewById(R.id.produit_etat);
		this.etatView.setEnum(ProduitEtat.class);
		this.materielView =
			(EnumSpinner) view.findViewById(R.id.produit_materiel);
		this.materielView.setEnum(ProduitMateriel.class);
		this.commandeAdapter = 
				new SingleEntityWidget.EntityAdapter<Commande>() {
			@Override
			public String entityToString(Commande item) {
				return String.valueOf(item.getId_cmd());
			}
		};
		this.commandeWidget =
			(SingleEntityWidget) view.findViewById(R.id.produit_commande_button);
		this.commandeWidget.setAdapter(this.commandeAdapter);
		this.quantiteView =
			(EditText) view.findViewById(R.id.produit_quantite);
		this.avancementView =
			(EditText) view.findViewById(R.id.produit_avancement);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getType() != null) {
			this.typeView.setSelectedItem(this.model.getType());
		}
		if (this.model.getEtat() != null) {
			this.etatView.setSelectedItem(this.model.getEtat());
		}
		if (this.model.getMateriel() != null) {
			this.materielView.setSelectedItem(this.model.getMateriel());
		}
		this.quantiteView.setText(String.valueOf(this.model.getQuantite()));
		this.avancementView.setText(String.valueOf(this.model.getAvancement()));

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setType((ProduitType) this.typeView.getSelectedItem());

		this.model.setEtat((ProduitEtat) this.etatView.getSelectedItem());

		this.model.setMateriel((ProduitMateriel) this.materielView.getSelectedItem());

		this.model.setCommande(this.commandeAdapter.getSelectedItem());

		this.model.setQuantite(Integer.parseInt(
					this.quantiteView.getEditableText().toString()));

		this.model.setAvancement(Integer.parseInt(
					this.avancementView.getEditableText().toString()));

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.commandeAdapter.getSelectedItem() == null) {
			error = R.string.produit_commande_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.quantiteView.getText().toString().trim())) {
			error = R.string.produit_quantite_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.avancementView.getText().toString().trim())) {
			error = R.string.produit_avancement_invalid_field_error;
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
		final View view = inflater.inflate(
				R.layout.fragment_produit_create,
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
		private final Produit entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final ProduitCreateFragment fragment,
				final Produit entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.produit_progress_save_title),
					this.ctx.getString(
							R.string.produit_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new ProduitProviderUtils(this.ctx).insert(
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
								R.string.produit_error_create));
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
		private ProduitCreateFragment fragment;
		/** commande list. */
		private ArrayList<Commande> commandeList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final ProduitCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.produit_progress_load_relations_title),
					this.ctx.getString(
							R.string.produit_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.commandeList = 
				new CommandeProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.commandeAdapter.loadData(this.commandeList);
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
