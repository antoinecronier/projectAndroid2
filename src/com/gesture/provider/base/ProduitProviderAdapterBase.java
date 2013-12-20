/**************************************************************************
 * ProduitProviderAdapterBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 20, 2013
 *
 **************************************************************************/
package com.gesture.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.gesture.entity.Produit;
import com.gesture.provider.WindowsGesture2Provider;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.CommandeSQLiteAdapter;

/**
 * ProduitProviderAdapterBase.
 */
public abstract class ProduitProviderAdapterBase
				extends ProviderAdapterBase<Produit> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ProduitProviderAdapter";

	/** PRODUIT_URI. */
	public	  static Uri PRODUIT_URI;

	/** produit type. */
	protected static final String produitType =
			"produit";

	/** PRODUIT_ALL. */
	protected static final int PRODUIT_ALL =
			1355179401;
	/** PRODUIT_ONE. */
	protected static final int PRODUIT_ONE =
			1355179402;

	/** PRODUIT_COMMANDE. */
	protected static final int PRODUIT_COMMANDE =
			1355179403;

	/**
	 * Static constructor.
	 */
	static {
		PRODUIT_URI =
				WindowsGesture2Provider.generateUri(
						produitType);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				produitType,
				PRODUIT_ALL);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				produitType + "/#",
				PRODUIT_ONE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				produitType + "/#/commande",
				PRODUIT_COMMANDE);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ProduitProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ProduitSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(PRODUIT_ALL);
		this.uriIds.add(PRODUIT_ONE);
		this.uriIds.add(PRODUIT_COMMANDE);
	}

	@Override
	public String getType(final Uri uri) {
		String result;
		final String single =
				"vnc.android.cursor.item/"
					+ WindowsGesture2Provider.authority + ".";
		final String collection =
				"vnc.android.cursor.collection/"
					+ WindowsGesture2Provider.authority + ".";

		int matchedUri = WindowsGesture2ProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			case PRODUIT_ALL:
				result = collection + "produit";
				break;
			case PRODUIT_ONE:
				result = single + "produit";
				break;
			case PRODUIT_COMMANDE:
				result = single + "produit";
				break;
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int delete(
			final Uri uri,
			String selection,
			String[] selectionArgs) {
		int matchedUri = WindowsGesture2ProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case PRODUIT_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ProduitSQLiteAdapter.COL_ID_PRODUIT
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case PRODUIT_ALL:
				result = this.adapter.delete(
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}
	
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = WindowsGesture2ProviderBase
				.getUriMatcher().match(uri);
		
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case PRODUIT_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(ProduitSQLiteAdapter.COL_ID_PRODUIT, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							PRODUIT_URI,
							id);
				}
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
		Cursor produitCursor;
		

		switch (matchedUri) {

			case PRODUIT_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case PRODUIT_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case PRODUIT_COMMANDE:
				produitCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (produitCursor.getCount() > 0) {
					produitCursor.moveToFirst();
					int commandeId = produitCursor.getInt(produitCursor.getColumnIndex(
									ProduitSQLiteAdapter.COL_COMMANDE));
					
					CommandeSQLiteAdapter commandeAdapter = new CommandeSQLiteAdapter(this.ctx);
					commandeAdapter.open(this.getDb());
					result = commandeAdapter.query(commandeId);
				}
				break;

			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int update(
			final Uri uri,
			final ContentValues values,
			String selection,
			String[] selectionArgs) {
		
		
		int matchedUri = WindowsGesture2ProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case PRODUIT_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						ProduitSQLiteAdapter.COL_ID_PRODUIT + " = "
						+ id,
						selectionArgs);
				break;
			case PRODUIT_ALL:
				result = this.adapter.update(
							values,
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}



	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	@Override
	public Uri getUri() {
		return PRODUIT_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					ProduitSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

