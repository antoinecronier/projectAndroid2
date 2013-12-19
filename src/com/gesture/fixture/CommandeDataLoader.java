/**************************************************************************
 * CommandeDataLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import android.content.Context;

import com.gesture.entity.Commande;
import com.gesture.entity.Client;
import com.gesture.entity.Produit;

import com.gesture.harmony.util.DateUtils;

/**
 * CommandeDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class CommandeDataLoader
						extends FixtureBase<Commande> {
	/** CommandeDataLoader name. */
	private static final String FILE_NAME = "Commande";

	/** Constant field for id_cmd. */
	private static final String ID_CMD = "id_cmd";
	/** Constant field for client. */
	private static final String CLIENT = "client";
	/** Constant field for dateCreation. */
	private static final String DATECREATION = "dateCreation";
	/** Constant field for dateFin. */
	private static final String DATEFIN = "dateFin";
	/** Constant field for dateLivraison. */
	private static final String DATELIVRAISON = "dateLivraison";
	/** Constant field for avancement. */
	private static final String AVANCEMENT = "avancement";
	/** Constant field for produits. */
	private static final String PRODUITS = "produits";


	/** CommandeDataLoader instance (Singleton). */
	private static CommandeDataLoader instance;

	/**
	 * Get the CommandeDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static CommandeDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new CommandeDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private CommandeDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Commande extractItem(final Map<?, ?> columns) {
		final Commande commande =
				new Commande();

		return this.extractItem(columns, commande);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param commande Entity to extract
	 * @return A Commande entity
	 */
	protected Commande extractItem(final Map<?, ?> columns,
				Commande commande) {

		if (columns.get(ID_CMD) != null) {
			commande.setId_cmd(
				(Integer) columns.get(ID_CMD));
		}

		if (columns.get(CLIENT) != null) {
			final Client client =
				ClientDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(CLIENT));
			if (client != null) {
				commande.setClient(client);
			}
		}

		if (columns.get(DATECREATION) != null) {
			commande.setDateCreation(
				(String) columns.get(DATECREATION));
		}

		if (columns.get(DATEFIN) != null) {
			commande.setDateFin(
				(String) columns.get(DATEFIN));
		}

		if (columns.get(DATELIVRAISON) != null) {
			commande.setDateLivraison(
				(String) columns.get(DATELIVRAISON));
		}

		if (columns.get(AVANCEMENT) != null) {
			commande.setAvancement(
				(Integer) columns.get(AVANCEMENT));
		}

		if (columns.get(PRODUITS) != null) {
			ArrayList<Produit> produits =
				new ArrayList<Produit>();
			final Map<?, ?> produitsMap =
				(Map<?, ?>) columns.get(PRODUITS);
			for (final Object produitName : produitsMap.values()) {
				if (ProduitDataLoader.getInstance(
					this.ctx).items.containsKey(
							(String) produitName)) {
				produits.add(
						ProduitDataLoader.getInstance(
								this.ctx).items.get((String) produitName));
				}
			}
			commande.setProduits(produits);
		}


		return commande;
	}
	/**
	 * Loads Commandes into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Commande commande : this.items.values()) {
			commande.setId_cmd(
					manager.persist(commande));
		}
		manager.flush();
	}

	/**
	 * Give priority for fixtures insertion in database.
	 * 0 is the first.
	 * @return The order
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * Get the fixture file name.
	 * @return A String representing the file name
	 */
	@Override
	public String getFixtureFileName() {
		return FILE_NAME;
	}
}
