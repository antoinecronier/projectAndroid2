/**************************************************************************
 * ProduitDataLoader.java, WindowsGesture2 Android
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
import java.util.Map;

import android.content.Context;

import com.gesture.entity.Produit;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.entity.Produit.ProduitEtat;
import com.gesture.entity.Produit.ProduitMateriel;



/**
 * ProduitDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ProduitDataLoader
						extends FixtureBase<Produit> {
	/** ProduitDataLoader name. */
	private static final String FILE_NAME = "Produit";

	/** Constant field for id_produit. */
	private static final String ID_PRODUIT = "id_produit";
	/** Constant field for type. */
	private static final String TYPE = "type";
	/** Constant field for etat. */
	private static final String ETAT = "etat";
	/** Constant field for materiel. */
	private static final String MATERIEL = "materiel";
	/** Constant field for commande. */
	private static final String COMMANDE = "commande";
	/** Constant field for quantite. */
	private static final String QUANTITE = "quantite";
	/** Constant field for avancement. */
	private static final String AVANCEMENT = "avancement";


	/** ProduitDataLoader instance (Singleton). */
	private static ProduitDataLoader instance;

	/**
	 * Get the ProduitDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static ProduitDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ProduitDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ProduitDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Produit extractItem(final Map<?, ?> columns) {
		final Produit produit =
				new Produit();

		return this.extractItem(columns, produit);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param produit Entity to extract
	 * @return A Produit entity
	 */
	protected Produit extractItem(final Map<?, ?> columns,
				Produit produit) {

		if (columns.get(ID_PRODUIT) != null) {
			produit.setId_produit(
				(Integer) columns.get(ID_PRODUIT));
		}

		if (columns.get(TYPE) != null) {
			produit.setType(ProduitType.fromValue(
				(String) columns.get(TYPE)));
		}

		if (columns.get(ETAT) != null) {
			produit.setEtat(ProduitEtat.fromValue(
				(String) columns.get(ETAT)));
		}

		if (columns.get(MATERIEL) != null) {
			produit.setMateriel(ProduitMateriel.fromValue(
				(String) columns.get(MATERIEL)));
		}

		if (columns.get(COMMANDE) != null) {
			final Commande commande =
				CommandeDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(COMMANDE));
			if (commande != null) {
				produit.setCommande(commande);
			}
		}

		if (columns.get(QUANTITE) != null) {
			produit.setQuantite(
				(Integer) columns.get(QUANTITE));
		}

		if (columns.get(AVANCEMENT) != null) {
			produit.setAvancement(
				(Integer) columns.get(AVANCEMENT));
		}


		return produit;
	}
	/**
	 * Loads Produits into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Produit produit : this.items.values()) {
			produit.setId_produit(
					manager.persist(produit));
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
