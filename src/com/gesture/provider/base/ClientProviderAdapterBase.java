/**************************************************************************
 * ClientProviderAdapterBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Client;
import com.gesture.provider.WindowsGesture2Provider;
import com.gesture.data.ClientSQLiteAdapter;

/**
 * ClientProviderAdapterBase.
 */
public abstract class ClientProviderAdapterBase
				extends ProviderAdapterBase<Client> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ClientProviderAdapter";

	/** CLIENT_URI. */
	public	  static Uri CLIENT_URI;

	/** client type. */
	protected static final String clientType =
			"client";

	/** CLIENT_ALL. */
	protected static final int CLIENT_ALL =
			2021122027;
	/** CLIENT_ONE. */
	protected static final int CLIENT_ONE =
			2021122028;


	/**
	 * Static constructor.
	 */
	static {
		CLIENT_URI =
				WindowsGesture2Provider.generateUri(
						clientType);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				clientType,
				CLIENT_ALL);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				clientType + "/#",
				CLIENT_ONE);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ClientProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ClientSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(CLIENT_ALL);
		this.uriIds.add(CLIENT_ONE);
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
			case CLIENT_ALL:
				result = collection + "client";
				break;
			case CLIENT_ONE:
				result = single + "client";
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
			case CLIENT_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ClientSQLiteAdapter.COL_ID_CLIENT
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case CLIENT_ALL:
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
			case CLIENT_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(ClientSQLiteAdapter.COL_ID_CLIENT, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							CLIENT_URI,
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
		

		switch (matchedUri) {

			case CLIENT_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case CLIENT_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
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
			case CLIENT_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						ClientSQLiteAdapter.COL_ID_CLIENT + " = "
						+ id,
						selectionArgs);
				break;
			case CLIENT_ALL:
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
		return CLIENT_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ClientSQLiteAdapter.ALIASED_COL_ID_CLIENT
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					ClientSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

