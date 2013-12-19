/**************************************************************************
 * WindowsGesture2ProviderBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider.base;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.gesture.R;
import com.gesture.provider.UserProviderAdapter;
import com.gesture.provider.ProduitProviderAdapter;
import com.gesture.provider.MachineProviderAdapter;
import com.gesture.provider.ZoneProviderAdapter;
import com.gesture.provider.CommandeProviderAdapter;
import com.gesture.provider.LogTracaProviderAdapter;
import com.gesture.provider.ClientProviderAdapter;

/**
 * WindowsGesture2ProviderBase.
 */
public class WindowsGesture2ProviderBase extends ContentProvider {
	/** TAG for debug purpose. */
	protected static String TAG = "WindowsGesture2Provider";
	/** Uri not supported. */
	protected static String URI_NOT_SUPPORTED;

	/* Tools / Common. */
	/** com.gesture.provider authority. */
	public static String authority
				= "com.gesture.provider";
	/** URI Matcher. */
	protected static UriMatcher uriMatcher =
			new UriMatcher(UriMatcher.NO_MATCH);

	/** List of all the provider adapters. */
	protected ArrayList<ProviderAdapterBase<?>> providerAdapters;
	/**
	 * Database.
	 */
	protected SQLiteDatabase db;

	/**
	 * Context.
	 */
	protected Context mContext;

	/**
	 * Called when the contentProvider is first created.
	 * @return true if everything goes well, false otherwise
	 */
	@Override
	public boolean onCreate() {
		boolean result = true;

		this.mContext = getContext();
		URI_NOT_SUPPORTED = this.getContext().getString(
				R.string.uri_not_supported);

		try {
			this.providerAdapters = new ArrayList<ProviderAdapterBase<?>>();
			UserProviderAdapter userProviderAdapter =
				new UserProviderAdapter(this.mContext);
			this.db = userProviderAdapter.getDb();			
			this.providerAdapters.add(userProviderAdapter);
			this.providerAdapters.add(
					new ProduitProviderAdapter(
						this.mContext,
						this.db));
			this.providerAdapters.add(
					new MachineProviderAdapter(
						this.mContext,
						this.db));
			this.providerAdapters.add(
					new ZoneProviderAdapter(
						this.mContext,
						this.db));
			this.providerAdapters.add(
					new CommandeProviderAdapter(
						this.mContext,
						this.db));
			this.providerAdapters.add(
					new LogTracaProviderAdapter(
						this.mContext,
						this.db));
			this.providerAdapters.add(
					new ClientProviderAdapter(
						this.mContext,
						this.db));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			result = false;
		}

		return result;
	}

	/**
	 * Get the entity from the URI.
	 * @param uri URI
	 * @return A String representing the entity name
	 */
	@Override
	public String getType(final Uri uri) {
		String result = null;
		boolean matched = false;
		
		for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
			if (adapter.match(uri)) {
				result = adapter.getType(uri);
				matched = true;
				break;
			}
		}

		if (!matched) {
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		} else {
			return result;
		}
	}

	/**
	 * Delete the entities matching with uri from the DB.
	 * @param uri URI
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token deleted
	 */
	@Override
	public int delete(final Uri uri, final String selection,
			final String[] selectionArgs) {
		int result = 0;
		boolean matched = false;
		boolean alreadyInTransaction = this.db.inTransaction();
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
		
		for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
			if (adapter.match(uri)) {
				result = adapter.delete(uri, selection, selectionArgs);
				matched = true;
				break;
			}
		}
		
		if (!alreadyInTransaction) {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
		}
		
		if (!matched) {
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		} else {
			if (result > 0) {
				this.getContext().getContentResolver().notifyChange(uri, null);
			}
			return result;		
		}
	}

	/**
	 * Insert the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to insert
	 * @return how many token inserted
	 */
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		Uri result = null;
		boolean matched = false;
		boolean alreadyInTransaction = this.db.inTransaction();
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
		
		for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
			if (adapter.match(uri)) {
				result = adapter.insert(uri, values);
				matched = true;
				break;
			}
		}
		
		if (!alreadyInTransaction) {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
		}
		
		if (!matched) {
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		} else {
			if (result != null) {
				this.getContext().getContentResolver().notifyChange(uri, null);
			}
			return result;		
		}
	}

	/**
	 * Send a query to the DB.
	 * @param uri URI
	 * @param projection Columns to work with
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @param sortOrder ORDER BY clause
	 * @return A cursor pointing to the result of the query
	 */
	@Override
	public Cursor query(final Uri uri, final String[] projection,
			final String selection, final String[] selectionArgs,
			final String sortOrder) {
		Cursor result = null;
		boolean matched = false;
		boolean alreadyInTransaction = this.db.inTransaction();
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
		
		for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
			if (adapter.match(uri)) {
				result = adapter.query(uri,
							projection,
							selection,
							selectionArgs,
							sortOrder);

				matched = true;
				break;
			}
		}
		
		if (!alreadyInTransaction) {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
		}
		
		if (!matched) {
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		} else {
			return result;		
		}
	}

	/**
	 * Update the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to update
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token update
	 */
	@Override
	public int update(final Uri uri, final ContentValues values,
				      final String selection, final String[] selectionArgs) {
		int result = 0;
		boolean matched = false;
		boolean alreadyInTransaction = this.db.inTransaction();
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
		
		for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
			if (adapter.match(uri)) {
				result = adapter.update(uri,
							values,
							selection,
							selectionArgs);

				matched = true;
				break;
			}
		}
		
		if (!alreadyInTransaction) {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
		}
		
		if (!matched) {
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		} else {
			if (result > 0) {
				this.getContext().getContentResolver().notifyChange(uri, null);
			}
			return result;		
		}
	}

	//-------------------------------------------------------------------------

	/** Utils function.
	 * @param typePath Path to type
	 * @return generated URI
	 */
	public static final Uri generateUri(final String typePath) {
		return Uri.parse("content://" + authority + "/" + typePath);
	}

	/** Utils function.
	 * @return generated URI
	 */
	public static final Uri generateUri() {
		return Uri.parse("content://" + authority);
	}

	/**
	 * Get URI Matcher.
	 * @return the uriMatcher
	 */
	public static UriMatcher getUriMatcher() {
		return uriMatcher;
	}
}
