/**************************************************************************
 * CommandeCreateFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

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
import com.gesture.entity.Commande;
import com.gesture.entity.Client;
import com.gesture.entity.Produit;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.widget.DateTimeWidget;
import com.gesture.harmony.widget.MultiEntityWidget;
import com.gesture.harmony.widget.SingleEntityWidget;
import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;
import com.gesture.provider.utils.CommandeProviderUtils;
import com.gesture.provider.utils.ClientProviderUtils;
import com.gesture.provider.utils.ProduitProviderUtils;

/**
 * Commande create fragment.
 *
 * This fragment gives you an interface to create a Commande.
 */
public class CommandeCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Commande model = new Commande();

	/** Fields View. */
	/** The client chooser component. */
	protected SingleEntityWidget clientWidget;
	/** The client Adapter. */
	protected SingleEntityWidget.EntityAdapter<Client> 
				clientAdapter;
	/** dateCreation View. */
	protected EditText dateCreationView;
	/** dateFin View. */
	protected EditText dateFinView;
	/** dateLivraison View. */
	protected EditText dateLivraisonView;
	/** avancement View. */
	protected EditText avancementView;
	/** The produits chooser component. */
	protected MultiEntityWidget produitsWidget;
	/** The produits Adapter. */
	protected MultiEntityWidget.EntityAdapter<Produit> 
				produitsAdapter;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.clientAdapter = 
				new SingleEntityWidget.EntityAdapter<Client>() {
			@Override
			public String entityToString(Client item) {
				return String.valueOf(item.getId_client());
			}
		};
		this.clientWidget =
			(SingleEntityWidget) view.findViewById(R.id.commande_client_button);
		this.clientWidget.setAdapter(this.clientAdapter);
		this.dateCreationView =
			(EditText) view.findViewById(R.id.commande_datecreation);
		this.dateFinView =
			(EditText) view.findViewById(R.id.commande_datefin);
		this.dateLivraisonView =
			(EditText) view.findViewById(R.id.commande_datelivraison);
		this.avancementView =
			(EditText) view.findViewById(R.id.commande_avancement);
		this.produitsAdapter = 
				new MultiEntityWidget.EntityAdapter<Produit>() {
			@Override
			public String entityToString(Produit item) {
				return String.valueOf(item.getId_produit());
			}
		};
		this.produitsWidget =
			(MultiEntityWidget) view.findViewById(R.id.commande_produits_button);
		this.produitsWidget.setAdapter(this.produitsAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {

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

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setClient(this.clientAdapter.getSelectedItem());

		this.model.setDateCreation(this.dateCreationView.getEditableText().toString());

		this.model.setDateFin(this.dateFinView.getEditableText().toString());

		this.model.setDateLivraison(this.dateLivraisonView.getEditableText().toString());

		this.model.setAvancement(Integer.parseInt(
					this.avancementView.getEditableText().toString()));

		this.model.setProduits(this.produitsAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.clientAdapter.getSelectedItem() == null) {
			error = R.string.commande_client_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dateCreationView.getText().toString().trim())) {
			error = R.string.commande_datecreation_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dateFinView.getText().toString().trim())) {
			error = R.string.commande_datefin_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.dateLivraisonView.getText().toString().trim())) {
			error = R.string.commande_datelivraison_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.avancementView.getText().toString().trim())) {
			error = R.string.commande_avancement_invalid_field_error;
		}
		if (this.produitsAdapter.getCheckedItems().isEmpty()) {
			error = R.string.commande_produits_invalid_field_error;
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
				R.layout.fragment_commande_create,
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
		private final Commande entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final CommandeCreateFragment fragment,
				final Commande entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.commande_progress_save_title),
					this.ctx.getString(
							R.string.commande_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new CommandeProviderUtils(this.ctx).insert(
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
								R.string.commande_error_create));
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
		private CommandeCreateFragment fragment;
		/** client list. */
		private ArrayList<Client> clientList;
		/** produits list. */
		private ArrayList<Produit> produitsList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final CommandeCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.commande_progress_load_relations_title),
					this.ctx.getString(
							R.string.commande_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.clientList = 
				new ClientProviderUtils(this.ctx).queryAll();
			this.produitsList = 
				new ProduitProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.clientAdapter.loadData(this.clientList);
			this.fragment.produitsAdapter.loadData(this.produitsList);
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
