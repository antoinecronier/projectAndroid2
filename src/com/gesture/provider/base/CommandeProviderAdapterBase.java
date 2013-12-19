/**************************************************************************
 * CommandeProviderAdapterBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.gesture.entity.Commande;
import com.gesture.provider.WindowsGesture2Provider;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;

/**
 * CommandeProviderAdapterBase.
 */
public abstract class CommandeProviderAdapterBase
				extends ProviderAdapterBase<Commande> {

	/** TAG for debug purpose. */
	protected static final String TAG = "CommandeProviderAdapter";

	/** COMMANDE_URI. */
	public	  static Uri COMMANDE_URI;

	/** commande type. */
	protected static final String commandeType =
			"commande";

	/** COMMANDE_ALL. */
	protected static final int COMMANDE_ALL =
			537891174;
	/** COMMANDE_ONE. */
	protected static final int COMMANDE_ONE =
			537891175;

	/** COMMANDE_CLIENT. */
	protected static final int COMMANDE_CLIENT =
			537891176;
	/** COMMANDE_PRODUITS. */
	protected static final int COMMANDE_PRODUITS =
			537891177;

	/**
	 * Static constructor.
	 */
	static {
		COMMANDE_URI =
				WindowsGesture2Provider.generateUri(
						commandeType);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				commandeType,
				COMMANDE_ALL);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				commandeType + "/#",
				COMMANDE_ONE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				commandeType + "/#/client",
				COMMANDE_CLIENT);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				commandeType + "/#/produits",
				COMMANDE_PRODUITS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public CommandeProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new CommandeSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(COMMANDE_ALL);
		this.uriIds.add(COMMANDE_ONE);
		this.uriIds.add(COMMANDE_CLIENT);
		this.uriIds.add(COMMANDE_PRODUITS);
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
			case COMMANDE_ALL:
				result = collection + "commande";
				break;
			case COMMANDE_ONE:
				result = single + "commande";
				break;
			case COMMANDE_CLIENT:
				result = single + "commande";
				break;
			case COMMANDE_PRODUITS:
				result = collection + "commande";
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
			case COMMANDE_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = CommandeSQLiteAdapter.COL_ID_CMD
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case COMMANDE_ALL:
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
			case COMMANDE_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(CommandeSQLiteAdapter.COL_ID_CMD, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							COMMANDE_URI,
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
		Cursor commandeCursor;
		int id = 0;

		switch (matchedUri) {

			case COMMANDE_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case COMMANDE_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case COMMANDE_CLIENT:
				commandeCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (commandeCursor.getCount() > 0) {
					commandeCursor.moveToFirst();
					int clientId = commandeCursor.getInt(commandeCursor.getColumnIndex(
									CommandeSQLiteAdapter.COL_CLIENT));
					
					ClientSQLiteAdapter clientAdapter = new ClientSQLiteAdapter(this.ctx);
					clientAdapter.open(this.getDb());
					result = clientAdapter.query(clientId);
				}
				break;

			case COMMANDE_PRODUITS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				ProduitSQLiteAdapter produitsAdapter = new ProduitSQLiteAdapter(this.ctx);
				produitsAdapter.open(this.getDb());
				result = produitsAdapter.getByCommandeproduitsInternal(id, ProduitSQLiteAdapter.ALIASED_COLS, selection, selectionArgs, null);
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
			case COMMANDE_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						CommandeSQLiteAdapter.COL_ID_CMD + " = "
						+ id,
						selectionArgs);
				break;
			case COMMANDE_ALL:
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
		return COMMANDE_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = CommandeSQLiteAdapter.ALIASED_COL_ID_CMD
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					CommandeSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

