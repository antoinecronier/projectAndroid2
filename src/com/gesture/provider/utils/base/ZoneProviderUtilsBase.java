/**************************************************************************
 * ZoneProviderUtilsBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.gesture.criterias.ZoneCriterias;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.ZoneSQLiteAdapter;

import com.gesture.entity.Zone;

import com.gesture.provider.ZoneProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * Zone Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ZoneProviderUtilsBase
			extends ProviderUtilsBase<Zone> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "ZoneProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public ZoneProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Zone item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ZoneSQLiteAdapter adapt =
				new ZoneSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(ZoneSQLiteAdapter.COL_ID_ZONE);

		operations.add(ContentProviderOperation.newInsert(
				ZoneProviderAdapter.ZONE_URI)
						.withValues(itemValues)
						.build());


		try {
			ContentProviderResult[] results = 
					prov.applyBatch(WindowsGesture2Provider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}


	/**
	 * Delete from DB.
	 * @param item Zone
	 * @return number of row affected
	 */
	public int delete(final Zone item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				ZoneProviderAdapter.ZONE_URI,
				String.valueOf(item.getId_zone()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Zone
	 */
	public Zone query(final int id) {
		Zone result = null;
		ZoneSQLiteAdapter adapt =
					new ZoneSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		ZoneCriterias crits =
				new ZoneCriterias(GroupType.AND);
		crits.add(ZoneSQLiteAdapter.ALIASED_COL_ID_ZONE,
					String.valueOf(id));

		Cursor cursor = prov.query(
			ZoneProviderAdapter.ZONE_URI,
			ZoneSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<Zone>
	 */
	public ArrayList<Zone> queryAll() {
		ArrayList<Zone> result =
					new ArrayList<Zone>();
		ZoneSQLiteAdapter adapt =
					new ZoneSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ZoneProviderAdapter.ZONE_URI,
				ZoneSQLiteAdapter.ALIASED_COLS,
				null,
				null,
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<Zone>
	 */
	public ArrayList<Zone> query(
				CriteriasBase<Zone> criteria) {
		ArrayList<Zone> result =
					new ArrayList<Zone>();
		ZoneSQLiteAdapter adapt =
					new ZoneSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ZoneProviderAdapter.ZONE_URI,
				ZoneSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Zone
	 
	 * @return number of rows updated
	 */
	public int update(final Zone item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ZoneSQLiteAdapter adapt =
				new ZoneSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				ZoneProviderAdapter.ZONE_URI,
				String.valueOf(item.getId_zone()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());



		try {
			ContentProviderResult[] results = prov.applyBatch(WindowsGesture2Provider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	
}
