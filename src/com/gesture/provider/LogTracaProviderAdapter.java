/**************************************************************************
 * LogTracaProviderAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.gesture.criterias.LogTracaCriterias;
import com.gesture.criterias.ProduitCriterias;
import com.gesture.criterias.base.Criteria;
import com.gesture.criterias.base.Criteria.Type;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.criterias.base.value.ArrayValue;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit;
import com.gesture.provider.base.LogTracaProviderAdapterBase;
import com.gesture.provider.base.WindowsGesture2ProviderBase;
import com.gesture.provider.utils.CommandeProviderUtils;

/**
 * LogTracaProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class LogTracaProviderAdapter
					extends LogTracaProviderAdapterBase {
	
	/** LOGTRACA_COMMANDE. */
	protected static final int PRODUIT_COMMANDE =
			2068130946;
	
	/**
	 * Static constructor.
	 */
	static {
		LOGTRACA_URI =
				WindowsGesture2Provider.generateUri(
						logTracaType);
		
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#/produitOnCommande",
				PRODUIT_COMMANDE);
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogTracaProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public LogTracaProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
		this.uriIds.add(PRODUIT_COMMANDE);
	}
	
	@Override
	public String getType(final Uri uri) {
		
		String result = super.getType(uri);
		final String collection =
				"vnc.android.cursor.collection/"
					+ WindowsGesture2Provider.authority + ".";

		int matchedUri = WindowsGesture2ProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			
			case PRODUIT_COMMANDE:
				result = collection + "logtraca";
				break;
			default:
				result = null;
				break;
		}

		return result;
	}
	
	@Override
	public Cursor query(final Uri uri,
						String[] projection,
						String selection,
						String[] selectionArgs,
						final String sortOrder) {

		int matchedUri = WindowsGesture2ProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;

		switch (matchedUri) {
			case PRODUIT_COMMANDE:
				//result = this.method1(uri);
				result = this.method2(uri);
				//result = this.method3(uri);
				break;

			
			default:
				// Don't forget to call super in default case !
				result = super.query(
						uri,
						projection,
						selection,
						selectionArgs,
						sortOrder);
				break;
		}
		

		return result;
	}
	
	private Cursor method1(Uri uri) {
		Cursor result = null;
		// Methode "simple"
		// On créé une commande parcequ'il n'y a pas de signature de
		// getAssociateProduits qui prenne un int en entrée
		Commande commande = new Commande();
		commande.setId_cmd(
				Integer.parseInt(uri.getPathSegments().get(1)));
		
		// On récupère la liste des produits associés à la commande
		List<Produit> products = 
				new CommandeProviderUtils(this.ctx)
						.getAssociateProduits(commande);
		
		// On créé un criteria (LogTraca.produit IN (id1, id2, id3, ...)
		LogTracaCriterias crit = new LogTracaCriterias(GroupType.AND);
		Criteria logInCriteria = new Criteria();
		ArrayValue arrayVal = new ArrayValue();
		for (Produit product : products) {
			arrayVal.addValue(String.valueOf(product.getId_produit()));
		}
		logInCriteria.setKey(LogTracaSQLiteAdapter.ALIASED_COL_PRODUIT);
		logInCriteria.setType(Type.IN);
		logInCriteria.addValue(arrayVal);
		crit.add(logInCriteria);
		
		// On requête
		result = this.adapter.query(LogTracaSQLiteAdapter.ALIASED_COLS,
				crit.toSQLiteSelection(),
				crit.toSQLiteSelectionArgs(),
				null,
				null,
				null);
		return result;
	}
	
	private Cursor method2(Uri uri) {
		Cursor result = null;
		
		// Methode plus propre
		// On requête les ids produits associés à la commande
		Cursor productIds = this.ctx.getContentResolver().query(
				Uri.withAppendedPath(
						Uri.withAppendedPath(	
								CommandeProviderAdapter.COMMANDE_URI,
								uri.getPathSegments().get(1)),
						"produits"),
				new String[]{
					ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT},
				null,
				null,
				null);
		
		if (productIds != null && productIds.getCount() > 0) {
			LogTracaCriterias crit = new LogTracaCriterias(GroupType.AND);
			Criteria logInCriteria = new Criteria();
			ArrayValue arrayVal = new ArrayValue();
			productIds.moveToFirst();
			do {
				// Une seule column, donc l'id est forcément en position 0
				arrayVal.addValue(productIds.getString(0));
				
			} while (productIds.moveToNext());
			logInCriteria.setKey(LogTracaSQLiteAdapter.ALIASED_COL_PRODUIT);
			logInCriteria.setType(Type.IN);
			logInCriteria.addValue(arrayVal);
			crit.add(logInCriteria);
			
			// On requête
			result = this.adapter.query(LogTracaSQLiteAdapter.ALIASED_COLS,
					crit.toSQLiteSelection(),
					crit.toSQLiteSelectionArgs(),
					null,
					null,
					null);
		}
		return result;
	}
	
	private Cursor method3(Uri uri) {
		Cursor result = null;
		// On fait un simple INNER JOIN
		//		SELECT LogTraca.*
		//		FROM LogTraca
		//		INNER JOIN Produit ON Produit.IdProduit = LogTraca.produit
		//		WHERE Produit.commande = ?
		SQLiteQueryBuilder query = new SQLiteQueryBuilder();

		// Le SELECT
		String[] projection = LogTracaSQLiteAdapter.ALIASED_COLS;
		
		// La partie FROM / INNER JOIN de la requête
		StringBuilder tables = new StringBuilder();
		tables.append(LogTracaSQLiteAdapter.TABLE_NAME);
		tables.append(" INNER JOIN ");
		tables.append(ProduitSQLiteAdapter.TABLE_NAME);
		tables.append(" ON ");
		tables.append(ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT);
		tables.append(" = ");
		tables.append(LogTracaSQLiteAdapter.ALIASED_COL_PRODUIT);
		
		// Le where
		ProduitCriterias crit = new ProduitCriterias(GroupType.AND);
		crit.add(ProduitSQLiteAdapter.ALIASED_COL_COMMANDE,
				uri.getPathSegments().get(1),
				Type.EQUALS);
		

		query.setTables(tables.toString());
		result = query.query(this.db,
				projection,
				crit.toSQLiteSelection(),
				crit.toSQLiteSelectionArgs(),
				null,
				null,
				null);
		
		return result;
	}
}

