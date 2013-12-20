/**************************************************************************
 * ClientSQLiteAdapterBase.java, WindowsGesture2 Android
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

import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.entity.Client;


import com.gesture.WindowsGesture2Application;


/** Client adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ClientAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ClientSQLiteAdapterBase
						extends SQLiteAdapterBase<Client> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ClientDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Client";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_client. */
	public static final String COL_ID_CLIENT =
			"id_client";
	/** Alias. */
	public static final String ALIASED_COL_ID_CLIENT =
			TABLE_NAME + "." + COL_ID_CLIENT;
	/** nom. */
	public static final String COL_NOM =
			"nom";
	/** Alias. */
	public static final String ALIASED_COL_NOM =
			TABLE_NAME + "." + COL_NOM;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		ClientSQLiteAdapter.COL_ID_CLIENT,
		ClientSQLiteAdapter.COL_NOM
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		ClientSQLiteAdapter.ALIASED_COL_ID_CLIENT,
		ClientSQLiteAdapter.ALIASED_COL_NOM
	};

	/**
	 * Get the table name used in DB for your Client entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Client entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Client entity table.
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
		
		 + COL_ID_CLIENT	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_NOM	+ " VARCHAR NOT NULL"
		
		
		+ ", UNIQUE(" + COL_NOM + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ClientSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Client entity to Content Values for database.
	 * @param item Client entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Client item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_CLIENT,
			String.valueOf(item.getId_client()));

		if (item.getNom() != null) {
			result.put(COL_NOM,
				item.getNom());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to Client entity.
	 * @param cursor Cursor object
	 * @return Client entity
	 */
	public Client cursorToItem(final Cursor cursor) {
		Client result = new Client();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Client entity.
	 * @param cursor Cursor object
	 * @param result Client entity
	 */
	public void cursorToItem(final Cursor cursor, final Client result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_CLIENT);
			result.setId_client(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_NOM);
			result.setNom(
					cursor.getString(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Client by id in database.
	 *
	 * @param id Identify of Client
	 * @return Client entity
	 */
	public Client getByID(final int id_client) {
		final Cursor cursor = this.getSingleCursor(id_client);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Client result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Clients entities.
	 *
	 * @return List of Client entities
	 */
	public ArrayList<Client> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Client> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Client entity into database.
	 *
	 * @param item The Client entity to persist
	 * @return Id of the Client entity
	 */
	public long insert(final Client item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(ClientSQLiteAdapter.COL_ID_CLIENT);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					ClientSQLiteAdapter.COL_ID_CLIENT,
					values);
		}
		item.setId_client((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a Client entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Client entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Client item) {
		int result = 0;
		if (this.getByID(item.getId_client()) != null) {
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
	 * Update a Client entity into database.
	 *
	 * @param item The Client entity to persist
	 * @return count of updated entities
	 */
	public int update(final Client item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 ClientSQLiteAdapter.COL_ID_CLIENT
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_client()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Client entity of database.
	 *
	 * @param id_client id_client
	 * @return count of updated entities
	 */
	public int remove(final int id_client) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_client);
		}

		
		final String whereClause =  ClientSQLiteAdapter.COL_ID_CLIENT
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_client) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param client The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Client client) {
		return this.delete(client.getId_client());
	}

	/**
	 *  Internal Cursor.
	 * @param id_client id_client
	 *  @return A Cursor pointing to the Client corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_client) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_client);
		}

		final String whereClause =  ClientSQLiteAdapter.ALIASED_COL_ID_CLIENT
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_client) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Client entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_CLIENT + " = ?",
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
				ALIASED_COL_ID_CLIENT + " = ?",
				new String[]{String.valueOf(id)});
	}

}
