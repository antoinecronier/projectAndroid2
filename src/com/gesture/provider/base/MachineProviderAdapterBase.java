/**************************************************************************
 * MachineProviderAdapterBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Machine;
import com.gesture.provider.WindowsGesture2Provider;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.ZoneSQLiteAdapter;
import com.gesture.data.LogTracaSQLiteAdapter;

/**
 * MachineProviderAdapterBase.
 */
public abstract class MachineProviderAdapterBase
				extends ProviderAdapterBase<Machine> {

	/** TAG for debug purpose. */
	protected static final String TAG = "MachineProviderAdapter";

	/** MACHINE_URI. */
	public	  static Uri MACHINE_URI;

	/** machine type. */
	protected static final String machineType =
			"machine";

	/** MACHINE_ALL. */
	protected static final int MACHINE_ALL =
			1805001689;
	/** MACHINE_ONE. */
	protected static final int MACHINE_ONE =
			1805001690;

	/** MACHINE_ZONE. */
	protected static final int MACHINE_ZONE =
			1805001691;
	/** MACHINE_LOGTRACAS. */
	protected static final int MACHINE_LOGTRACAS =
			1805001692;

	/**
	 * Static constructor.
	 */
	static {
		MACHINE_URI =
				WindowsGesture2Provider.generateUri(
						machineType);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				machineType,
				MACHINE_ALL);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				machineType + "/#",
				MACHINE_ONE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				machineType + "/#/zone",
				MACHINE_ZONE);
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				machineType + "/#/logtracas",
				MACHINE_LOGTRACAS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public MachineProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new MachineSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(MACHINE_ALL);
		this.uriIds.add(MACHINE_ONE);
		this.uriIds.add(MACHINE_ZONE);
		this.uriIds.add(MACHINE_LOGTRACAS);
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
			case MACHINE_ALL:
				result = collection + "machine";
				break;
			case MACHINE_ONE:
				result = single + "machine";
				break;
			case MACHINE_ZONE:
				result = single + "machine";
				break;
			case MACHINE_LOGTRACAS:
				result = collection + "machine";
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
			case MACHINE_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = MachineSQLiteAdapter.COL_ID_MACHINE
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case MACHINE_ALL:
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
			case MACHINE_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(MachineSQLiteAdapter.COL_ID_MACHINE, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							MACHINE_URI,
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
		Cursor machineCursor;
		int id = 0;

		switch (matchedUri) {

			case MACHINE_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case MACHINE_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case MACHINE_ZONE:
				machineCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (machineCursor.getCount() > 0) {
					machineCursor.moveToFirst();
					int zoneId = machineCursor.getInt(machineCursor.getColumnIndex(
									MachineSQLiteAdapter.COL_ZONE));
					
					ZoneSQLiteAdapter zoneAdapter = new ZoneSQLiteAdapter(this.ctx);
					zoneAdapter.open(this.getDb());
					result = zoneAdapter.query(zoneId);
				}
				break;

			case MACHINE_LOGTRACAS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				LogTracaSQLiteAdapter LogTracasAdapter = new LogTracaSQLiteAdapter(this.ctx);
				LogTracasAdapter.open(this.getDb());
				result = LogTracasAdapter.getByMachineLogTracasInternal(id, LogTracaSQLiteAdapter.ALIASED_COLS, selection, selectionArgs, null);
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
			case MACHINE_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						MachineSQLiteAdapter.COL_ID_MACHINE + " = "
						+ id,
						selectionArgs);
				break;
			case MACHINE_ALL:
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
		return MACHINE_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = MachineSQLiteAdapter.ALIASED_COL_ID_MACHINE
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					MachineSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

