/**************************************************************************
 * LogTracaProviderAdapterBase.java, WindowsGesture2 Android
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
import com.gesture.entity.LogTraca;
import com.gesture.provider.WindowsGesture2Provider;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;

/**
 * LogTracaProviderAdapterBase.
 */
public abstract class LogTracaProviderAdapterBase
				extends ProviderAdapterBase<LogTraca> {

	/** TAG for debug purpose. */
	protected static final String TAG = "LogTracaProviderAdapter";

	/** LOGTRACA_URI. */
	public	  static Uri LOGTRACA_URI;

	/** logTraca type. */
	protected static final String logTracaType =
			"logtraca";

	/** LOGTRACA_ALL. */
	protected static final int LOGTRACA_ALL =
			2068130941;
	/** LOGTRACA_ONE. */
	protected static final int LOGTRACA_ONE =
			2068130942;

	/** LOGTRACA_PRODUIT. */
	protected static final int LOGTRACA_PRODUIT =
			2068130943;
	/** LOGTRACA_MACHINE. */
	protected static final int LOGTRACA_MACHINE =
			2068130944;
	/** LOGTRACA_USER. */
	protected static final int LOGTRACA_USER =
			2068130945;

	/**
	 * Static constructor.
	 */
	static {
		LOGTRACA_URI =
				WindowsGesture2Provider.generateUri(
						logTracaType);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType,
				LOGTRACA_ALL);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#",
				LOGTRACA_ONE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#/produit",
				LOGTRACA_PRODUIT);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#/machine",
				LOGTRACA_MACHINE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#/user",
				LOGTRACA_USER);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public LogTracaProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new LogTracaSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(LOGTRACA_ALL);
		this.uriIds.add(LOGTRACA_ONE);
		this.uriIds.add(LOGTRACA_PRODUIT);
		this.uriIds.add(LOGTRACA_MACHINE);
		this.uriIds.add(LOGTRACA_USER);
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
			case LOGTRACA_ALL:
				result = collection + "logtraca";
				break;
			case LOGTRACA_ONE:
				result = single + "logtraca";
				break;
			case LOGTRACA_PRODUIT:
				result = single + "logtraca";
				break;
			case LOGTRACA_MACHINE:
				result = single + "logtraca";
				break;
			case LOGTRACA_USER:
				result = single + "logtraca";
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
			case LOGTRACA_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = LogTracaSQLiteAdapter.COL_ID_LOG
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case LOGTRACA_ALL:
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
			case LOGTRACA_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(LogTracaSQLiteAdapter.COL_ID_LOG, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							LOGTRACA_URI,
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
		Cursor logTracaCursor;
		

		switch (matchedUri) {

			case LOGTRACA_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case LOGTRACA_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case LOGTRACA_PRODUIT:
				logTracaCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (logTracaCursor.getCount() > 0) {
					logTracaCursor.moveToFirst();
					int produitId = logTracaCursor.getInt(logTracaCursor.getColumnIndex(
									LogTracaSQLiteAdapter.COL_PRODUIT));
					
					ProduitSQLiteAdapter produitAdapter = new ProduitSQLiteAdapter(this.ctx);
					produitAdapter.open(this.getDb());
					result = produitAdapter.query(produitId);
				}
				break;

			case LOGTRACA_MACHINE:
				logTracaCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (logTracaCursor.getCount() > 0) {
					logTracaCursor.moveToFirst();
					int machineId = logTracaCursor.getInt(logTracaCursor.getColumnIndex(
									LogTracaSQLiteAdapter.COL_MACHINE));
					
					MachineSQLiteAdapter machineAdapter = new MachineSQLiteAdapter(this.ctx);
					machineAdapter.open(this.getDb());
					result = machineAdapter.query(machineId);
				}
				break;

			case LOGTRACA_USER:
				logTracaCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (logTracaCursor.getCount() > 0) {
					logTracaCursor.moveToFirst();
					int userId = logTracaCursor.getInt(logTracaCursor.getColumnIndex(
									LogTracaSQLiteAdapter.COL_USER));
					
					UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
					userAdapter.open(this.getDb());
					result = userAdapter.query(userId);
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
			case LOGTRACA_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						LogTracaSQLiteAdapter.COL_ID_LOG + " = "
						+ id,
						selectionArgs);
				break;
			case LOGTRACA_ALL:
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
		return LOGTRACA_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = LogTracaSQLiteAdapter.ALIASED_COL_ID_LOG
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					LogTracaSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

