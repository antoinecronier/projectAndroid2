/**************************************************************************
 * CommandeProviderUtilsBase.java, WindowsGesture2 Android
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

import com.gesture.criterias.CommandeCriterias;
import com.gesture.criterias.ClientCriterias;
import com.gesture.criterias.ProduitCriterias;
import com.gesture.criterias.base.Criteria;
import com.gesture.criterias.base.Criteria.Type;
import com.gesture.criterias.base.value.ArrayValue;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;

import com.gesture.entity.Commande;
import com.gesture.entity.Client;
import com.gesture.entity.Produit;

import com.gesture.provider.CommandeProviderAdapter;
import com.gesture.provider.ClientProviderAdapter;
import com.gesture.provider.ProduitProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * Commande Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class CommandeProviderUtilsBase
			extends ProviderUtilsBase<Commande> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "CommandeProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public CommandeProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Commande item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		CommandeSQLiteAdapter adapt =
				new CommandeSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(CommandeSQLiteAdapter.COL_ID_CMD);

		operations.add(ContentProviderOperation.newInsert(
				CommandeProviderAdapter.COMMANDE_URI)
						.withValues(itemValues)
						.build());

		if (item.getProduits() != null && item.getProduits().size() > 0) {
			String produitsSelection = ProduitSQLiteAdapter.COL_ID_PRODUIT + " IN (";
			String[] produitsSelectionArgs = new String[item.getProduits().size()];
			for (int i = 0; i < item.getProduits().size(); i++) {
				produitsSelectionArgs[i] = String.valueOf(item.getProduits().get(i).getId_produit());
				produitsSelection += "? ";
				if (i != item.getProduits().size() - 1) {
					 produitsSelection += ", ";
				}
			}
			produitsSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(ProduitProviderAdapter.PRODUIT_URI)
					.withValueBackReference(
							ProduitSQLiteAdapter
									.COL_COMMANDEPRODUITSINTERNAL,
							0)
					.withSelection(produitsSelection, produitsSelectionArgs)
					.build());
		}

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
	 * @param item Commande
	 * @return number of row affected
	 */
	public int delete(final Commande item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				CommandeProviderAdapter.COMMANDE_URI,
				String.valueOf(item.getId_cmd()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Commande
	 */
	public Commande query(final int id) {
		Commande result = null;
		CommandeSQLiteAdapter adapt =
					new CommandeSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		CommandeCriterias crits =
				new CommandeCriterias(GroupType.AND);
		crits.add(CommandeSQLiteAdapter.ALIASED_COL_ID_CMD,
					String.valueOf(id));

		Cursor cursor = prov.query(
			CommandeProviderAdapter.COMMANDE_URI,
			CommandeSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

			if (result.getClient() != null) {
				result.setClient(
					this.getAssociateClient(result));
			}
			result.setProduits(
				this.getAssociateProduits(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<Commande>
	 */
	public ArrayList<Commande> queryAll() {
		ArrayList<Commande> result =
					new ArrayList<Commande>();
		CommandeSQLiteAdapter adapt =
					new CommandeSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				CommandeProviderAdapter.COMMANDE_URI,
				CommandeSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<Commande>
	 */
	public ArrayList<Commande> query(
				CriteriasBase<Commande> criteria) {
		ArrayList<Commande> result =
					new ArrayList<Commande>();
		CommandeSQLiteAdapter adapt =
					new CommandeSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				CommandeProviderAdapter.COMMANDE_URI,
				CommandeSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Commande
	 
	 * @return number of rows updated
	 */
	public int update(final Commande item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		CommandeSQLiteAdapter adapt =
				new CommandeSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				CommandeProviderAdapter.COMMANDE_URI,
				String.valueOf(item.getId_cmd()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getProduits() != null && item.getProduits().size() > 0) {
			// Set new produits for Commande
			ProduitCriterias produitsCrit =
						new ProduitCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(ProduitSQLiteAdapter.COL_ID_PRODUIT);
			crit.addValue(values);
			produitsCrit.add(crit);


			for (int i = 0; i < item.getProduits().size(); i++) {
				values.addValue(String.valueOf(
						item.getProduits().get(i).getId_produit()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					ProduitProviderAdapter.PRODUIT_URI)
						.withValue(
								ProduitSQLiteAdapter
										.COL_COMMANDEPRODUITSINTERNAL,
								item.getId_cmd())
					.withSelection(
							produitsCrit.toSQLiteSelection(),
							produitsCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated produits
			crit.setType(Type.NOT_IN);
			produitsCrit.add(ProduitSQLiteAdapter.COL_COMMANDEPRODUITSINTERNAL,
					String.valueOf(item.getId_cmd()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					ProduitProviderAdapter.PRODUIT_URI)
						.withValue(
								ProduitSQLiteAdapter
										.COL_COMMANDEPRODUITSINTERNAL,
								null)
					.withSelection(
							produitsCrit.toSQLiteSelection(),
							produitsCrit.toSQLiteSelectionArgs())
					.build());
		}


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
	 * Get associate Client.
	 * @param item Commande
	 * @return Client
	 */
	public Client getAssociateClient(
			final Commande item) {
		Client result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor clientCursor = prov.query(
				ClientProviderAdapter.CLIENT_URI,
				ClientSQLiteAdapter.ALIASED_COLS,
				ClientSQLiteAdapter.COL_ID_CLIENT + "= ?",
				new String[]{String.valueOf(item.getClient().getId_client())},
				null);

		if (clientCursor.getCount() > 0) {
			clientCursor.moveToFirst();
			ClientSQLiteAdapter clientAdapt =
					new ClientSQLiteAdapter(this.getContext());
			result = clientAdapt.cursorToItem(clientCursor);
		} else {
			result = null;
		}
		clientCursor.close();

		return result;
	}

	/**
	 * Get associate Produits.
	 * @param item Commande
	 * @return Produit
	 */
	public ArrayList<Produit> getAssociateProduits(
			final Commande item) {
		ArrayList<Produit> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor produitCursor = prov.query(
				ProduitProviderAdapter.PRODUIT_URI,
				ProduitSQLiteAdapter.ALIASED_COLS,
				ProduitSQLiteAdapter.COL_COMMANDEPRODUITSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId_cmd())},
				null);

		ProduitSQLiteAdapter produitAdapt =
				new ProduitSQLiteAdapter(this.getContext());
		result = produitAdapt.cursorToItems(
						produitCursor);
		produitCursor.close();

		return result;
	}

}
