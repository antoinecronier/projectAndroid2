/**************************************************************************
 * ProduitSQLiteAdapterBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 20, 2013
 *
 **************************************************************************/
package com.gesture.data.base;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.entity.Produit;
import com.gesture.entity.Commande;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.entity.Produit.ProduitEtat;
import com.gesture.entity.Produit.ProduitMateriel;


import com.gesture.WindowsGesture2Application;


/** Produit adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ProduitAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ProduitSQLiteAdapterBase
						extends SQLiteAdapterBase<Produit> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ProduitDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Produit";

	/**
	 *  Columns constants fields mapping.
	 */
	/** Commande_produits_internal. */
	public static final String COL_COMMANDEPRODUITSINTERNAL =
			"Commande_produits_internal";
	/** Alias. */
	public static final String ALIASED_COL_COMMANDEPRODUITSINTERNAL =
			TABLE_NAME + "." + COL_COMMANDEPRODUITSINTERNAL;
	/** id_produit. */
	public static final String COL_ID_PRODUIT =
			"id_produit";
	/** Alias. */
	public static final String ALIASED_COL_ID_PRODUIT =
			TABLE_NAME + "." + COL_ID_PRODUIT;
	/** type. */
	public static final String COL_TYPE =
			"type";
	/** Alias. */
	public static final String ALIASED_COL_TYPE =
			TABLE_NAME + "." + COL_TYPE;
	/** etat. */
	public static final String COL_ETAT =
			"etat";
	/** Alias. */
	public static final String ALIASED_COL_ETAT =
			TABLE_NAME + "." + COL_ETAT;
	/** materiel. */
	public static final String COL_MATERIEL =
			"materiel";
	/** Alias. */
	public static final String ALIASED_COL_MATERIEL =
			TABLE_NAME + "." + COL_MATERIEL;
	/** commande. */
	public static final String COL_COMMANDE =
			"commande";
	/** Alias. */
	public static final String ALIASED_COL_COMMANDE =
			TABLE_NAME + "." + COL_COMMANDE;
	/** quantite. */
	public static final String COL_QUANTITE =
			"quantite";
	/** Alias. */
	public static final String ALIASED_COL_QUANTITE =
			TABLE_NAME + "." + COL_QUANTITE;
	/** avancement. */
	public static final String COL_AVANCEMENT =
			"avancement";
	/** Alias. */
	public static final String ALIASED_COL_AVANCEMENT =
			TABLE_NAME + "." + COL_AVANCEMENT;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		ProduitSQLiteAdapter.COL_COMMANDEPRODUITSINTERNAL,
		ProduitSQLiteAdapter.COL_ID_PRODUIT,
		ProduitSQLiteAdapter.COL_TYPE,
		ProduitSQLiteAdapter.COL_ETAT,
		ProduitSQLiteAdapter.COL_MATERIEL,
		ProduitSQLiteAdapter.COL_COMMANDE,
		ProduitSQLiteAdapter.COL_QUANTITE,
		ProduitSQLiteAdapter.COL_AVANCEMENT
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		ProduitSQLiteAdapter.ALIASED_COL_COMMANDEPRODUITSINTERNAL,
		ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT,
		ProduitSQLiteAdapter.ALIASED_COL_TYPE,
		ProduitSQLiteAdapter.ALIASED_COL_ETAT,
		ProduitSQLiteAdapter.ALIASED_COL_MATERIEL,
		ProduitSQLiteAdapter.ALIASED_COL_COMMANDE,
		ProduitSQLiteAdapter.ALIASED_COL_QUANTITE,
		ProduitSQLiteAdapter.ALIASED_COL_AVANCEMENT
	};

	/**
	 * Get the table name used in DB for your Produit entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Produit entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Produit entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
		
		 + COL_COMMANDEPRODUITSINTERNAL	+ " INTEGER,"
		 + COL_ID_PRODUIT	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_TYPE	+ " VARCHAR NOT NULL,"
		 + COL_ETAT	+ " VARCHAR NOT NULL,"
		 + COL_MATERIEL	+ " VARCHAR NOT NULL,"
		 + COL_COMMANDE	+ " INTEGER NOT NULL,"
		 + COL_QUANTITE	+ " INTEGER NOT NULL,"
		 + COL_AVANCEMENT	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + COL_COMMANDEPRODUITSINTERNAL + ") REFERENCES " 
			 + CommandeSQLiteAdapter.TABLE_NAME 
				+ " (" + CommandeSQLiteAdapter.COL_ID_CMD + "),"
		 + "FOREIGN KEY(" + COL_COMMANDE + ") REFERENCES " 
			 + CommandeSQLiteAdapter.TABLE_NAME 
				+ " (" + CommandeSQLiteAdapter.COL_ID_CMD + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ProduitSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters
	/** Convert Produit entity to Content Values for database.
	 *
	 * @param item Produit entity object
	 * @param commandeId commande id
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Produit item,
				int commandeId) {
		final ContentValues result = this.itemToContentValues(item);
		result.put(COL_COMMANDEPRODUITSINTERNAL,
				String.valueOf(commandeId));
		return result;
	}

	/**
	 * Convert Produit entity to Content Values for database.
	 * @param item Produit entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Produit item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_PRODUIT,
			String.valueOf(item.getId_produit()));

		if (item.getType() != null) {
			result.put(COL_TYPE,
				item.getType().getValue());
		}

		if (item.getEtat() != null) {
			result.put(COL_ETAT,
				item.getEtat().getValue());
		}

		if (item.getMateriel() != null) {
			result.put(COL_MATERIEL,
				item.getMateriel().getValue());
		}

		if (item.getCommande() != null) {
			result.put(COL_COMMANDE,
				item.getCommande().getId_cmd());
		}

		result.put(COL_QUANTITE,
			String.valueOf(item.getQuantite()));

		result.put(COL_AVANCEMENT,
			String.valueOf(item.getAvancement()));


		return result;
	}

	/**
	 * Convert Cursor of database to Produit entity.
	 * @param cursor Cursor object
	 * @return Produit entity
	 */
	public Produit cursorToItem(final Cursor cursor) {
		Produit result = new Produit();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Produit entity.
	 * @param cursor Cursor object
	 * @param result Produit entity
	 */
	public void cursorToItem(final Cursor cursor, final Produit result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_PRODUIT);
			result.setId_produit(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_TYPE);
			result.setType(
				ProduitType.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_ETAT);
			result.setEtat(
				ProduitEtat.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_MATERIEL);
			result.setMateriel(
				ProduitMateriel.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_COMMANDE);
			final Commande commande = new Commande();
			commande.setId_cmd(cursor.getInt(index));
			result.setCommande(commande);

			index = cursor.getColumnIndexOrThrow(COL_QUANTITE);
			result.setQuantite(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_AVANCEMENT);
			result.setAvancement(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Produit by id in database.
	 *
	 * @param id Identify of Produit
	 * @return Produit entity
	 */
	public Produit getByID(final int id_produit) {
		final Cursor cursor = this.getSingleCursor(id_produit);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Produit result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getCommande() != null) {
			final CommandeSQLiteAdapter commandeAdapter =
					new CommandeSQLiteAdapter(this.ctx);
			commandeAdapter.open(this.mDatabase);
			
			result.setCommande(commandeAdapter.getByID(
							result.getCommande().getId_cmd()));
		}
		return result;
	}

	/**
	 * Find & read Produit by CommandeproduitsInternal.
	 * @param commandeproduitsinternalId commandeproduitsinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of Produit entities
	 */
	 public Cursor getByCommandeproduitsInternal(final int commandeproduitsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = ProduitSQLiteAdapter.COL_COMMANDEPRODUITSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(commandeproduitsinternalId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }
	/**
	 * Find & read Produit by commande.
	 * @param commandeId commandeId
	 * @param orderBy Order by string (can be null)
	 * @return List of Produit entities
	 */
	 public Cursor getByCommande(final int commandeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = ProduitSQLiteAdapter.COL_COMMANDE + "=?";
		String idSelectionArgs = String.valueOf(commandeId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }

	/**
	 * Read All Produits entities.
	 *
	 * @return List of Produit entities
	 */
	public ArrayList<Produit> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Produit> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Produit entity into database.
	 *
	 * @param item The Produit entity to persist
	 * @return Id of the Produit entity
	 */
	public long insert(final Produit item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		values.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					ProduitSQLiteAdapter.COL_ID_PRODUIT,
					values);
		}
		item.setId_produit((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a Produit entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Produit entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Produit item) {
		int result = 0;
		if (this.getByID(item.getId_produit()) != null) {
			// Item already exists => update it
			result = this.update(item);
		} else {
			// Item doesn't exist => create it
			final long id = this.insert(item);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}

	/**
	 * Update a Produit entity into database.
	 *
	 * @param item The Produit entity to persist
	 * @return count of updated entities
	 */
	public int update(final Produit item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		final String whereClause =
				 ProduitSQLiteAdapter.COL_ID_PRODUIT
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_produit()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a Produit entity into database.
	 *
	 * @param item The Produit entity to persist
	 * @param commandeId The commande id
	 * @return count of updated entities
	 */
	public int updateWithCommandeProduits(
					Produit item, int commandeId) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		ContentValues values =
				this.itemToContentValues(item,
							commandeId);
		String whereClause =
				 ProduitSQLiteAdapter.COL_ID_PRODUIT
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId_produit()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a Produit entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Produit entity to persist
	 * @param commandeId The commande id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithCommandeProduits(
			Produit item, int commandeId) {
		int result = 0;
		if (this.getByID(item.getId_produit()) != null) {
			// Item already exists => update it
			result = this.updateWithCommandeProduits(item,
					commandeId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithCommandeProduits(item,
					commandeId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a Produit entity into database.
	 *
	 * @param item The Produit entity to persist
	 * @param commandeId The commande id
	 * @return Id of the Produit entity
	 */
	public long insertWithCommandeProduits(
			Produit item, int commandeId) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		ContentValues values = this.itemToContentValues(item,
				commandeId);
		values.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Delete a Produit entity of database.
	 *
	 * @param id_produit id_produit
	 * @return count of updated entities
	 */
	public int remove(final int id_produit) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_produit);
		}

		
		final String whereClause =  ProduitSQLiteAdapter.COL_ID_PRODUIT
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_produit) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param produit The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Produit produit) {
		return this.delete(produit.getId_produit());
	}

	/**
	 *  Internal Cursor.
	 * @param id_produit id_produit
	 *  @return A Cursor pointing to the Produit corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_produit) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_produit);
		}

		final String whereClause =  ProduitSQLiteAdapter.ALIASED_COL_ID_PRODUIT
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_produit) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Produit entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_PRODUIT + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
	}

	/**
	 * Deletes the given entity.
	 * @param id The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		return this.delete(
				ALIASED_COL_ID_PRODUIT + " = ?",
				new String[]{String.valueOf(id)});
	}

}
