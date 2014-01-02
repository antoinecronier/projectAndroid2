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

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gesture.R;
import com.gesture.entity.Client;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit;
import com.gesture.entity.Produit.ProduitMateriel;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.harmony.view.HarmonyFragment;
import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.widget.MultiEntityWidget;
import com.gesture.harmony.widget.SingleEntityWidget;
import com.gesture.menu.SaveMenuWrapper.SaveMenuInterface;
import com.gesture.provider.utils.ClientProviderUtils;
import com.gesture.provider.utils.CommandeProviderUtils;
import com.gesture.provider.utils.ProduitProviderUtils;

/**
 * Commande create fragment.
 * 
 * This fragment gives you an interface to create a Commande.
 */
public class CommandeCreateFragment extends HarmonyFragment implements
		OnClickListener {
	/** Model data. */
	protected Commande model = new Commande();

	/** Fields View. */
	/** The client chooser component. */
	protected SingleEntityWidget clientWidget;
	/** The client Adapter. */
	protected SingleEntityWidget.EntityAdapter<Client> clientAdapter;
	/** spinner produit */
	protected Spinner spinProduit;
	/** spinner matière */
	protected Spinner spinMatiere;

	protected Button btnCreateCmd;
	protected EditText edtQuantite;
	
	/**
	 * Initialize view of fields.
	 * 
	 * @param view
	 *            The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.clientAdapter = new SingleEntityWidget.EntityAdapter<Client>() {
			@Override
			public String entityToString(Client item) {
				return item.getNom();
			}
		};
		this.clientWidget = (SingleEntityWidget) view
				.findViewById(R.id.commande_client_button);
		this.clientWidget.setAdapter(this.clientAdapter);

		this.spinProduit = (Spinner) view.findViewById(R.id.spinProduit);
		this.spinProduit.setAdapter(new ArrayAdapter<ProduitType>(
				getActivity(), android.R.layout.simple_spinner_item,
				ProduitType.values()));

		this.spinMatiere = (Spinner) view.findViewById(R.id.spinMatiere);
		this.spinMatiere.setAdapter(new ArrayAdapter<ProduitMateriel>(
				getActivity(), android.R.layout.simple_spinner_item,
				ProduitMateriel.values()));
		
		this.btnCreateCmd = (Button) view.findViewById(R.id.btn_val_cmd);
		this.btnCreateCmd.setOnClickListener(this);
		
		this.edtQuantite = (EditText) view.findViewById(R.id.quantite);

	}

	/** Load data from model to fields view. */
	public void loadData() {

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData(Context ctx) {

		Date currentDate = new Date();
		
		this.model.setClient(this.clientAdapter.getSelectedItem());
		this.model.setAvancement(0);
		this.model.setDateCreation(currentDate.toString());
		
		//création des produits
		String typeProduit = this.spinProduit.getSelectedItem().toString();
		String matierProduit = this.spinMatiere.getSelectedItem().toString();
		int quantite = Integer.parseInt(this.edtQuantite.getText().toString());
		int i = quantite;
		ArrayList<Produit> listPdt = new ArrayList<Produit>();
		
		do{
			//enregistrement pdt en bdd et récupération id
			Produit pdt = new Produit();
			pdt.setMateriel(ProduitMateriel.fromValue(matierProduit));
			pdt.setType(ProduitType.fromValue(typeProduit));
			pdt.setQuantite(quantite);
			
			Uri result = null;
			ProduitProviderUtils ppu = new ProduitProviderUtils(ctx);
			
			result = ppu.insert(pdt);
			
			pdt.setId_produit(Integer.parseInt(result.toString()));
			
			listPdt.add(pdt);
			i--;
		}while(i==1);
		
		this.model.setProduits(listPdt);
	}

	/**
	 * Check data is valid.
	 * 
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.clientAdapter.getSelectedItem() == null) {
			error = R.string.commande_client_invalid_field_error;
		}
		if (error > 0) {
			Toast.makeText(this.getActivity(),
					this.getActivity().getString(error), Toast.LENGTH_SHORT)
					.show();
		}
		return error == 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_commande_create,
				container, false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/**
	 * This class will save the entity into the DB. It runs asynchronously and
	 * shows a progressDialog
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
		 * 
		 * @param entity
		 *            The entity to insert in the DB
		 * @param fragment
		 *            The parent fragment from where the aSyncTask is called
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

			this.progress = ProgressDialog
					.show(this.ctx,
							this.ctx.getString(R.string.commande_progress_save_title),
							this.ctx.getString(R.string.commande_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;
			CommandeProviderUtils test = new CommandeProviderUtils(this.ctx);
			result = test.insert(this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				final HarmonyFragmentActivity activity = (HarmonyFragmentActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						this.ctx);
				builder.setIcon(0);
				builder.setMessage(this.ctx
						.getString(R.string.commande_error_create));
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
	 * This class will save the entity into the DB. It runs asynchronously and
	 * shows a progressDialog
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

		/**
		 * Constructor of the task.
		 * 
		 * @param entity
		 *            The entity to insert in the DB
		 * @param fragment
		 *            The parent fragment from where the aSyncTask is called
		 */
		public LoadTask(final CommandeCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog
					.show(this.ctx,
							this.ctx.getString(R.string.commande_progress_load_relations_title),
							this.ctx.getString(R.string.commande_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.clientList = new ClientProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.clientAdapter.loadData(this.clientList);
			this.progress.dismiss();
		}
	}

	//Gestion événements
	public void onClick(View view) {
		
		if (view == btnCreateCmd) {
			//TODO enregistrement de la commande
			this.saveData(this.getActivity());
			new CreateTask(this, this.model).execute();
			//TODO création des produits selon la quantité
			//Redirection écran de paramètrage tracabilité
		}

	}
}
