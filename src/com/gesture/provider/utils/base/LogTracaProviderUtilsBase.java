/**************************************************************************
 * LogTracaProviderUtilsBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 20, 2013
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

import com.gesture.criterias.LogTracaCriterias;
import com.gesture.criterias.ProduitCriterias;
import com.gesture.criterias.MachineCriterias;
import com.gesture.criterias.UserCriterias;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;

import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;
import com.gesture.entity.Machine;
import com.gesture.entity.User;

import com.gesture.provider.LogTracaProviderAdapter;
import com.gesture.provider.ProduitProviderAdapter;
import com.gesture.provider.MachineProviderAdapter;
import com.gesture.provider.UserProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * LogTraca Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class LogTracaProviderUtilsBase
			extends ProviderUtilsBase<LogTraca> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "LogTracaProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public LogTracaProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final LogTraca item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		LogTracaSQLiteAdapter adapt =
				new LogTracaSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(LogTracaSQLiteAdapter.COL_ID_LOG);

		operations.add(ContentProviderOperation.newInsert(
				LogTracaProviderAdapter.LOGTRACA_URI)
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
	 * Insert into DB.
	 * @param item LogTraca to insert
	 * @param machineLogTracasInternalId machineLogTracasInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final LogTraca item,
							 final int machineLogTracasInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		LogTracaSQLiteAdapter adapt =
				new LogTracaSQLiteAdapter(this.getContext());
		ContentValues itemValues = adapt.itemToContentValues(item,
					machineLogTracasInternalId);
		itemValues.remove(LogTracaSQLiteAdapter.COL_ID_LOG);

		operations.add(ContentProviderOperation.newInsert(
				LogTracaProviderAdapter.LOGTRACA_URI)
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
	 * @param item LogTraca
	 * @return number of row affected
	 */
	public int delete(final LogTraca item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				LogTracaProviderAdapter.LOGTRACA_URI,
				String.valueOf(item.getId_log()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return LogTraca
	 */
	public LogTraca query(final int id) {
		LogTraca result = null;
		LogTracaSQLiteAdapter adapt =
					new LogTracaSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		LogTracaCriterias crits =
				new LogTracaCriterias(GroupType.AND);
		crits.add(LogTracaSQLiteAdapter.ALIASED_COL_ID_LOG,
					String.valueOf(id));

		Cursor cursor = prov.query(
			LogTracaProviderAdapter.LOGTRACA_URI,
			LogTracaSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

			if (result.getProduit() != null) {
				result.setProduit(
					this.getAssociateProduit(result));
			}
			if (result.getMachine() != null) {
				result.setMachine(
					this.getAssociateMachine(result));
			}
			if (result.getUser() != null) {
				result.setUser(
					this.getAssociateUser(result));
			}
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<LogTraca>
	 */
	public ArrayList<LogTraca> queryAll() {
		ArrayList<LogTraca> result =
					new ArrayList<LogTraca>();
		LogTracaSQLiteAdapter adapt =
					new LogTracaSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				LogTracaProviderAdapter.LOGTRACA_URI,
				LogTracaSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<LogTraca>
	 */
	public ArrayList<LogTraca> query(
				CriteriasBase<LogTraca> criteria) {
		ArrayList<LogTraca> result =
					new ArrayList<LogTraca>();
		LogTracaSQLiteAdapter adapt =
					new LogTracaSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				LogTracaProviderAdapter.LOGTRACA_URI,
				LogTracaSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item LogTraca
	 * @return number of rows updated
	 */
	public int update(final LogTraca item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		LogTracaSQLiteAdapter adapt =
				new LogTracaSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				LogTracaProviderAdapter.LOGTRACA_URI,
				String.valueOf(item.getId_log()));


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

	/**
	 * Updates the DB.
	 * @param item LogTraca
	 * @param machineLogTracasInternalId machineLogTracasInternal Id
	 * @return number of rows updated
	 */
	public int update(final LogTraca item,
							 final int machineLogTracasInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		LogTracaSQLiteAdapter adapt =
				new LogTracaSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item,
				machineLogTracasInternalId);

		Uri uri = Uri.withAppendedPath(
				LogTracaProviderAdapter.LOGTRACA_URI,
				String.valueOf(item.getId_log()));


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

	/** Relations operations. */
	/**
	 * Get associate Produit.
	 * @param item LogTraca
	 * @return Produit
	 */
	public Produit getAssociateProduit(
			final LogTraca item) {
		Produit result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor produitCursor = prov.query(
				ProduitProviderAdapter.PRODUIT_URI,
				ProduitSQLiteAdapter.ALIASED_COLS,
				ProduitSQLiteAdapter.COL_ID_PRODUIT + "= ?",
				new String[]{String.valueOf(item.getProduit().getId_produit())},
				null);

		if (produitCursor.getCount() > 0) {
			produitCursor.moveToFirst();
			ProduitSQLiteAdapter produitAdapt =
					new ProduitSQLiteAdapter(this.getContext());
			result = produitAdapt.cursorToItem(produitCursor);
		} else {
			result = null;
		}
		produitCursor.close();

		return result;
	}

	/**
	 * Get associate Machine.
	 * @param item LogTraca
	 * @return Machine
	 */
	public Machine getAssociateMachine(
			final LogTraca item) {
		Machine result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor machineCursor = prov.query(
				MachineProviderAdapter.MACHINE_URI,
				MachineSQLiteAdapter.ALIASED_COLS,
				MachineSQLiteAdapter.COL_ID_MACHINE + "= ?",
				new String[]{String.valueOf(item.getMachine().getId_machine())},
				null);

		if (machineCursor.getCount() > 0) {
			machineCursor.moveToFirst();
			MachineSQLiteAdapter machineAdapt =
					new MachineSQLiteAdapter(this.getContext());
			result = machineAdapt.cursorToItem(machineCursor);
		} else {
			result = null;
		}
		machineCursor.close();

		return result;
	}

	/**
	 * Get associate User.
	 * @param item LogTraca
	 * @return User
	 */
	public User getAssociateUser(
			final LogTraca item) {
		User result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				UserSQLiteAdapter.ALIASED_COLS,
				UserSQLiteAdapter.COL_ID_USER + "= ?",
				new String[]{String.valueOf(item.getUser().getId_user())},
				null);

		if (userCursor.getCount() > 0) {
			userCursor.moveToFirst();
			UserSQLiteAdapter userAdapt =
					new UserSQLiteAdapter(this.getContext());
			result = userAdapt.cursorToItem(userCursor);
		} else {
			result = null;
		}
		userCursor.close();

		return result;
	}

}
