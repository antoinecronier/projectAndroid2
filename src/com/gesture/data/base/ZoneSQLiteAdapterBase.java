/**************************************************************************
 * ZoneSQLiteAdapterBase.java, WindowsGesture2 Android
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
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gesture.data.ZoneSQLiteAdapter;
import com.gesture.entity.Zone;


import com.gesture.WindowsGesture2Application;


/** Zone adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ZoneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneSQLiteAdapterBase
						extends SQLiteAdapterBase<Zone> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ZoneDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Zone";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_zone. */
	public static final String COL_ID_ZONE =
			"id_zone";
	/** Alias. */
	public static final String ALIASED_COL_ID_ZONE =
			TABLE_NAME + "." + COL_ID_ZONE;
	/** nom. */
	public static final String COL_NOM =
			"nom";
	/** Alias. */
	public static final String ALIASED_COL_NOM =
			TABLE_NAME + "." + COL_NOM;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		ZoneSQLiteAdapter.COL_ID_ZONE,
		ZoneSQLiteAdapter.COL_NOM
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		ZoneSQLiteAdapter.ALIASED_COL_ID_ZONE,
		ZoneSQLiteAdapter.ALIASED_COL_NOM
	};

	/**
	 * Get the table name used in DB for your Zone entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Zone entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Zone entity table.
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
		
		 + COL_ID_ZONE	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_NOM	+ " VARCHAR NOT NULL"
		
		
		+ ", UNIQUE(" + COL_NOM + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ZoneSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Zone entity to Content Values for database.
	 * @param item Zone entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Zone item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_ZONE,
			String.valueOf(item.getId_zone()));

		if (item.getNom() != null) {
			result.put(COL_NOM,
				item.getNom());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to Zone entity.
	 * @param cursor Cursor object
	 * @return Zone entity
	 */
	public Zone cursorToItem(final Cursor cursor) {
		Zone result = new Zone();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Zone entity.
	 * @param cursor Cursor object
	 * @param result Zone entity
	 */
	public void cursorToItem(final Cursor cursor, final Zone result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_ZONE);
			result.setId_zone(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_NOM);
			result.setNom(
					cursor.getString(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Zone by id in database.
	 *
	 * @param id Identify of Zone
	 * @return Zone entity
	 */
	public Zone getByID(final int id_zone) {
		final Cursor cursor = this.getSingleCursor(id_zone);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Zone result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Zones entities.
	 *
	 * @return List of Zone entities
	 */
	public ArrayList<Zone> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Zone> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Zone entity into database.
	 *
	 * @param item The Zone entity to persist
	 * @return Id of the Zone entity
	 */
	public long insert(final Zone item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(ZoneSQLiteAdapter.COL_ID_ZONE);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					ZoneSQLiteAdapter.COL_ID_ZONE,
					values);
		}
		item.setId_zone((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a Zone entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Zone entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Zone item) {
		int result = 0;
		if (this.getByID(item.getId_zone()) != null) {
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
	 * Update a Zone entity into database.
	 *
	 * @param item The Zone entity to persist
	 * @return count of updated entities
	 */
	public int update(final Zone item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 ZoneSQLiteAdapter.COL_ID_ZONE
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_zone()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Zone entity of database.
	 *
	 * @param id_zone id_zone
	 * @return count of updated entities
	 */
	public int remove(final int id_zone) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_zone);
		}

		
		final String whereClause =  ZoneSQLiteAdapter.COL_ID_ZONE
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_zone) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param zone The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Zone zone) {
		return this.delete(zone.getId_zone());
	}

	/**
	 *  Internal Cursor.
	 * @param id_zone id_zone
	 *  @return A Cursor pointing to the Zone corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_zone) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_zone);
		}

		final String whereClause =  ZoneSQLiteAdapter.ALIASED_COL_ID_ZONE
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_zone) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Zone entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_ZONE + " = ?",
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
				ALIASED_COL_ID_ZONE + " = ?",
				new String[]{String.valueOf(id)});
	}

}
