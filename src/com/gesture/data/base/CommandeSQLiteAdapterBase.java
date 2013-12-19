/**************************************************************************
 * CommandeSQLiteAdapterBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.entity.Commande;
import com.gesture.entity.Client;
import com.gesture.entity.Produit;

import com.gesture.harmony.util.DateUtils;
import com.gesture.WindowsGesture2Application;


/** Commande adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit CommandeAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class CommandeSQLiteAdapterBase
						extends SQLiteAdapterBase<Commande> {

	/** TAG for debug purpose. */
	protected static final String TAG = "CommandeDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Commande";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_cmd. */
	public static final String COL_ID_CMD =
			"id_cmd";
	/** Alias. */
	public static final String ALIASED_COL_ID_CMD =
			TABLE_NAME + "." + COL_ID_CMD;
	/** client. */
	public static final String COL_CLIENT =
			"client";
	/** Alias. */
	public static final String ALIASED_COL_CLIENT =
			TABLE_NAME + "." + COL_CLIENT;
	/** dateCreation. */
	public static final String COL_DATECREATION =
			"dateCreation";
	/** Alias. */
	public static final String ALIASED_COL_DATECREATION =
			TABLE_NAME + "." + COL_DATECREATION;
	/** dateFin. */
	public static final String COL_DATEFIN =
			"dateFin";
	/** Alias. */
	public static final String ALIASED_COL_DATEFIN =
			TABLE_NAME + "." + COL_DATEFIN;
	/** dateLivraison. */
	public static final String COL_DATELIVRAISON =
			"dateLivraison";
	/** Alias. */
	public static final String ALIASED_COL_DATELIVRAISON =
			TABLE_NAME + "." + COL_DATELIVRAISON;
	/** avancement. */
	public static final String COL_AVANCEMENT =
			"avancement";
	/** Alias. */
	public static final String ALIASED_COL_AVANCEMENT =
			TABLE_NAME + "." + COL_AVANCEMENT;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		CommandeSQLiteAdapter.COL_ID_CMD,
		CommandeSQLiteAdapter.COL_CLIENT,
		CommandeSQLiteAdapter.COL_DATECREATION,
		CommandeSQLiteAdapter.COL_DATEFIN,
		CommandeSQLiteAdapter.COL_DATELIVRAISON,
		CommandeSQLiteAdapter.COL_AVANCEMENT
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		CommandeSQLiteAdapter.ALIASED_COL_ID_CMD,
		CommandeSQLiteAdapter.ALIASED_COL_CLIENT,
		CommandeSQLiteAdapter.ALIASED_COL_DATECREATION,
		CommandeSQLiteAdapter.ALIASED_COL_DATEFIN,
		CommandeSQLiteAdapter.ALIASED_COL_DATELIVRAISON,
		CommandeSQLiteAdapter.ALIASED_COL_AVANCEMENT
	};

	/**
	 * Get the table name used in DB for your Commande entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Commande entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Commande entity table.
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
		
		 + COL_ID_CMD	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_CLIENT	+ " INTEGER NOT NULL,"
		 + COL_DATECREATION	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_DATEFIN	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_DATELIVRAISON	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_AVANCEMENT	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + COL_CLIENT + ") REFERENCES " 
			 + ClientSQLiteAdapter.TABLE_NAME 
				+ " (" + ClientSQLiteAdapter.COL_ID_CLIENT + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public CommandeSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Commande entity to Content Values for database.
	 * @param item Commande entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Commande item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_CMD,
			String.valueOf(item.getId_cmd()));

		if (item.getClient() != null) {
			result.put(COL_CLIENT,
				item.getClient().getId_client());
		}

		if (item.getDateCreation() != null) {
			result.put(COL_DATECREATION,
				item.getDateCreation());
		}

		if (item.getDateFin() != null) {
			result.put(COL_DATEFIN,
				item.getDateFin());
		}

		if (item.getDateLivraison() != null) {
			result.put(COL_DATELIVRAISON,
				item.getDateLivraison());
		}

		result.put(COL_AVANCEMENT,
			String.valueOf(item.getAvancement()));


		return result;
	}

	/**
	 * Convert Cursor of database to Commande entity.
	 * @param cursor Cursor object
	 * @return Commande entity
	 */
	public Commande cursorToItem(final Cursor cursor) {
		Commande result = new Commande();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Commande entity.
	 * @param cursor Cursor object
	 * @param result Commande entity
	 */
	public void cursorToItem(final Cursor cursor, final Commande result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_CMD);
			result.setId_cmd(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_CLIENT);
			final Client client = new Client();
			client.setId_client(cursor.getInt(index));
			result.setClient(client);

			index = cursor.getColumnIndexOrThrow(COL_DATECREATION);
			result.setDateCreation(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_DATEFIN);
			result.setDateFin(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_DATELIVRAISON);
			result.setDateLivraison(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_AVANCEMENT);
			result.setAvancement(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Commande by id in database.
	 *
	 * @param id Identify of Commande
	 * @return Commande entity
	 */
	public Commande getByID(final int id_cmd) {
		final Cursor cursor = this.getSingleCursor(id_cmd);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Commande result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getClient() != null) {
			final ClientSQLiteAdapter clientAdapter =
					new ClientSQLiteAdapter(this.ctx);
			clientAdapter.open(this.mDatabase);
			
			result.setClient(clientAdapter.getByID(
							result.getClient().getId_client()));
		}
		final ProduitSQLiteAdapter produitsAdapter =
				new ProduitSQLiteAdapter(this.ctx);
		produitsAdapter.open(this.mDatabase);
		Cursor produitsCursor = produitsAdapter
					.getByCommandeproduitsInternal(result.getId_cmd(), ProduitSQLiteAdapter.ALIASED_COLS, null, null, null);
		result.setProduits(produitsAdapter.cursorToItems(produitsCursor));
		return result;
	}

	/**
	 * Find & read Commande by client.
	 * @param clientId clientId
	 * @param orderBy Order by string (can be null)
	 * @return List of Commande entities
	 */
	 public Cursor getByClient(final int clientId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = CommandeSQLiteAdapter.COL_CLIENT + "=?";
		String idSelectionArgs = String.valueOf(clientId);
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
	 * Read All Commandes entities.
	 *
	 * @return List of Commande entities
	 */
	public ArrayList<Commande> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Commande> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Commande entity into database.
	 *
	 * @param item The Commande entity to persist
	 * @return Id of the Commande entity
	 */
	public long insert(final Commande item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(CommandeSQLiteAdapter.COL_ID_CMD);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					CommandeSQLiteAdapter.COL_ID_CMD,
					values);
		}
		item.setId_cmd((int) newid);
		if (item.getProduits() != null) {
			ProduitSQLiteAdapterBase produitsAdapter =
					new ProduitSQLiteAdapter(this.ctx);
			produitsAdapter.open(this.mDatabase);
			for (Produit produit
						: item.getProduits()) {
				produitsAdapter.insertOrUpdateWithCommandeProduits(
									produit,
									newid);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a Commande entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Commande entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Commande item) {
		int result = 0;
		if (this.getByID(item.getId_cmd()) != null) {
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
	 * Update a Commande entity into database.
	 *
	 * @param item The Commande entity to persist
	 * @return count of updated entities
	 */
	public int update(final Commande item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 CommandeSQLiteAdapter.COL_ID_CMD
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_cmd()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Commande entity of database.
	 *
	 * @param id_cmd id_cmd
	 * @return count of updated entities
	 */
	public int remove(final int id_cmd) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_cmd);
		}

		
		final String whereClause =  CommandeSQLiteAdapter.COL_ID_CMD
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_cmd) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param commande The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Commande commande) {
		return this.delete(commande.getId_cmd());
	}

	/**
	 *  Internal Cursor.
	 * @param id_cmd id_cmd
	 *  @return A Cursor pointing to the Commande corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_cmd) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_cmd);
		}

		final String whereClause =  CommandeSQLiteAdapter.ALIASED_COL_ID_CMD
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_cmd) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Commande entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_CMD + " = ?",
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
				ALIASED_COL_ID_CMD + " = ?",
				new String[]{String.valueOf(id)});
	}

}
