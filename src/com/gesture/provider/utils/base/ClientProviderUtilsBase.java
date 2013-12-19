/**************************************************************************
 * ClientProviderUtilsBase.java, WindowsGesture2 Android
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

import com.gesture.criterias.ClientCriterias;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.ClientSQLiteAdapter;

import com.gesture.entity.Client;

import com.gesture.provider.ClientProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * Client Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ClientProviderUtilsBase
			extends ProviderUtilsBase<Client> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "ClientProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public ClientProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Client item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ClientSQLiteAdapter adapt =
				new ClientSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(ClientSQLiteAdapter.COL_ID_CLIENT);

		operations.add(ContentProviderOperation.newInsert(
				ClientProviderAdapter.CLIENT_URI)
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
	 * @param item Client
	 * @return number of row affected
	 */
	public int delete(final Client item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				ClientProviderAdapter.CLIENT_URI,
				String.valueOf(item.getId_client()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Client
	 */
	public Client query(final int id) {
		Client result = null;
		ClientSQLiteAdapter adapt =
					new ClientSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		ClientCriterias crits =
				new ClientCriterias(GroupType.AND);
		crits.add(ClientSQLiteAdapter.ALIASED_COL_ID_CLIENT,
					String.valueOf(id));

		Cursor cursor = prov.query(
			ClientProviderAdapter.CLIENT_URI,
			ClientSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<Client>
	 */
	public ArrayList<Client> queryAll() {
		ArrayList<Client> result =
					new ArrayList<Client>();
		ClientSQLiteAdapter adapt =
					new ClientSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ClientProviderAdapter.CLIENT_URI,
				ClientSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<Client>
	 */
	public ArrayList<Client> query(
				CriteriasBase<Client> criteria) {
		ArrayList<Client> result =
					new ArrayList<Client>();
		ClientSQLiteAdapter adapt =
					new ClientSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ClientProviderAdapter.CLIENT_URI,
				ClientSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Client
	 
	 * @return number of rows updated
	 */
	public int update(final Client item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ClientSQLiteAdapter adapt =
				new ClientSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				ClientProviderAdapter.CLIENT_URI,
				String.valueOf(item.getId_client()));


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
