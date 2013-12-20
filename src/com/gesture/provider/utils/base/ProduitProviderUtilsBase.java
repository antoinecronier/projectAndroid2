/**************************************************************************
 * ProduitProviderUtilsBase.java, WindowsGesture2 Android
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

import com.gesture.criterias.ProduitCriterias;
import com.gesture.criterias.CommandeCriterias;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.CommandeSQLiteAdapter;

import com.gesture.entity.Produit;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.entity.Produit.ProduitEtat;
import com.gesture.entity.Produit.ProduitMateriel;

import com.gesture.provider.ProduitProviderAdapter;
import com.gesture.provider.CommandeProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * Produit Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ProduitProviderUtilsBase
			extends ProviderUtilsBase<Produit> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "ProduitProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public ProduitProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Produit item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ProduitSQLiteAdapter adapt =
				new ProduitSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);

		operations.add(ContentProviderOperation.newInsert(
				ProduitProviderAdapter.PRODUIT_URI)
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
	 * @param item Produit to insert
	 * @param commandeproduitsInternalId commandeproduitsInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final Produit item,
							 final int commandeproduitsInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ProduitSQLiteAdapter adapt =
				new ProduitSQLiteAdapter(this.getContext());
		ContentValues itemValues = adapt.itemToContentValues(item,
					commandeproduitsInternalId);
		itemValues.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);

		operations.add(ContentProviderOperation.newInsert(
				ProduitProviderAdapter.PRODUIT_URI)
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
	 * @param item Produit
	 * @return number of row affected
	 */
	public int delete(final Produit item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				ProduitProviderAdapter.PRODUIT_URI,
				String.valueOf(item.getId_produit()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Produit
	 */
	public Produit query(final int id) {
		Produit result = null;
		ProduitSQLiteAdapter adapt =
					new ProduitSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		ProduitCriterias crits =
				new ProduitCriterias(GroupType.AND);
		crits.add(ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT,
					String.valueOf(id));

		Cursor cursor = prov.query(
			ProduitProviderAdapter.PRODUIT_URI,
			ProduitSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

			if (result.getCommande() != null) {
				result.setCommande(
					this.getAssociateCommande(result));
			}
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<Produit>
	 */
	public ArrayList<Produit> queryAll() {
		ArrayList<Produit> result =
					new ArrayList<Produit>();
		ProduitSQLiteAdapter adapt =
					new ProduitSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ProduitProviderAdapter.PRODUIT_URI,
				ProduitSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<Produit>
	 */
	public ArrayList<Produit> query(
				CriteriasBase<Produit> criteria) {
		ArrayList<Produit> result =
					new ArrayList<Produit>();
		ProduitSQLiteAdapter adapt =
					new ProduitSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ProduitProviderAdapter.PRODUIT_URI,
				ProduitSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Produit
	 * @return number of rows updated
	 */
	public int update(final Produit item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ProduitSQLiteAdapter adapt =
				new ProduitSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				ProduitProviderAdapter.PRODUIT_URI,
				String.valueOf(item.getId_produit()));


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
	 * @param item Produit
	 * @param commandeproduitsInternalId commandeproduitsInternal Id
	 * @return number of rows updated
	 */
	public int update(final Produit item,
							 final int commandeproduitsInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ProduitSQLiteAdapter adapt =
				new ProduitSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item,
				commandeproduitsInternalId);

		Uri uri = Uri.withAppendedPath(
				ProduitProviderAdapter.PRODUIT_URI,
				String.valueOf(item.getId_produit()));


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
	 * Get associate Commande.
	 * @param item Produit
	 * @return Commande
	 */
	public Commande getAssociateCommande(
			final Produit item) {
		Commande result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor commandeCursor = prov.query(
				CommandeProviderAdapter.COMMANDE_URI,
				CommandeSQLiteAdapter.ALIASED_COLS,
				CommandeSQLiteAdapter.COL_ID_CMD + "= ?",
				new String[]{String.valueOf(item.getCommande().getId_cmd())},
				null);

		if (commandeCursor.getCount() > 0) {
			commandeCursor.moveToFirst();
			CommandeSQLiteAdapter commandeAdapt =
					new CommandeSQLiteAdapter(this.getContext());
			result = commandeAdapt.cursorToItem(commandeCursor);
		} else {
			result = null;
		}
		commandeCursor.close();

		return result;
	}

}
