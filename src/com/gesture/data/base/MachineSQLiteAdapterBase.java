/**************************************************************************
 * MachineSQLiteAdapterBase.java, WindowsGesture2 Android
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
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.ZoneSQLiteAdapter;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.entity.Machine;
import com.gesture.entity.Zone;
import com.gesture.entity.LogTraca;


import com.gesture.WindowsGesture2Application;


/** Machine adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit MachineAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class MachineSQLiteAdapterBase
						extends SQLiteAdapterBase<Machine> {

	/** TAG for debug purpose. */
	protected static final String TAG = "MachineDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Machine";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_machine. */
	public static final String COL_ID_MACHINE =
			"id_machine";
	/** Alias. */
	public static final String ALIASED_COL_ID_MACHINE =
			TABLE_NAME + "." + COL_ID_MACHINE;
	/** nom. */
	public static final String COL_NOM =
			"nom";
	/** Alias. */
	public static final String ALIASED_COL_NOM =
			TABLE_NAME + "." + COL_NOM;
	/** zone. */
	public static final String COL_ZONE =
			"zone";
	/** Alias. */
	public static final String ALIASED_COL_ZONE =
			TABLE_NAME + "." + COL_ZONE;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		MachineSQLiteAdapter.COL_ID_MACHINE,
		MachineSQLiteAdapter.COL_NOM,
		MachineSQLiteAdapter.COL_ZONE
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		MachineSQLiteAdapter.ALIASED_COL_ID_MACHINE,
		MachineSQLiteAdapter.ALIASED_COL_NOM,
		MachineSQLiteAdapter.ALIASED_COL_ZONE
	};

	/**
	 * Get the table name used in DB for your Machine entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Machine entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Machine entity table.
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
		
		 + COL_ID_MACHINE	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_NOM	+ " VARCHAR NOT NULL,"
		 + COL_ZONE	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + COL_ZONE + ") REFERENCES " 
			 + ZoneSQLiteAdapter.TABLE_NAME 
				+ " (" + ZoneSQLiteAdapter.COL_ID_ZONE + ")"
		+ ", UNIQUE(" + COL_NOM + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public MachineSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Machine entity to Content Values for database.
	 * @param item Machine entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Machine item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_MACHINE,
			String.valueOf(item.getId_machine()));

		if (item.getNom() != null) {
			result.put(COL_NOM,
				item.getNom());
		}

		if (item.getZone() != null) {
			result.put(COL_ZONE,
				item.getZone().getId_zone());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to Machine entity.
	 * @param cursor Cursor object
	 * @return Machine entity
	 */
	public Machine cursorToItem(final Cursor cursor) {
		Machine result = new Machine();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Machine entity.
	 * @param cursor Cursor object
	 * @param result Machine entity
	 */
	public void cursorToItem(final Cursor cursor, final Machine result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_MACHINE);
			result.setId_machine(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_NOM);
			result.setNom(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_ZONE);
			final Zone zone = new Zone();
			zone.setId_zone(cursor.getInt(index));
			result.setZone(zone);


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Machine by id in database.
	 *
	 * @param id Identify of Machine
	 * @return Machine entity
	 */
	public Machine getByID(final int id_machine) {
		final Cursor cursor = this.getSingleCursor(id_machine);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Machine result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getZone() != null) {
			final ZoneSQLiteAdapter zoneAdapter =
					new ZoneSQLiteAdapter(this.ctx);
			zoneAdapter.open(this.mDatabase);
			
			result.setZone(zoneAdapter.getByID(
							result.getZone().getId_zone()));
		}
		final LogTracaSQLiteAdapter logTracasAdapter =
				new LogTracaSQLiteAdapter(this.ctx);
		logTracasAdapter.open(this.mDatabase);
		Cursor logtracasCursor = logTracasAdapter
					.getByMachineLogTracasInternal(result.getId_machine(), LogTracaSQLiteAdapter.ALIASED_COLS, null, null, null);
		result.setLogTracas(logTracasAdapter.cursorToItems(logtracasCursor));
		return result;
	}

	/**
	 * Find & read Machine by zone.
	 * @param zoneId zoneId
	 * @param orderBy Order by string (can be null)
	 * @return List of Machine entities
	 */
	 public Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = MachineSQLiteAdapter.COL_ZONE + "=?";
		String idSelectionArgs = String.valueOf(zoneId);
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
	 * Read All Machines entities.
	 *
	 * @return List of Machine entities
	 */
	public ArrayList<Machine> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Machine> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Machine entity into database.
	 *
	 * @param item The Machine entity to persist
	 * @return Id of the Machine entity
	 */
	public long insert(final Machine item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(MachineSQLiteAdapter.COL_ID_MACHINE);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					MachineSQLiteAdapter.COL_ID_MACHINE,
					values);
		}
		item.setId_machine((int) newid);
		if (item.getLogTracas() != null) {
			LogTracaSQLiteAdapterBase logTracasAdapter =
					new LogTracaSQLiteAdapter(this.ctx);
			logTracasAdapter.open(this.mDatabase);
			for (LogTraca logtraca
						: item.getLogTracas()) {
				logTracasAdapter.insertOrUpdateWithMachineLogTracas(
									logtraca,
									newid);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a Machine entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Machine entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Machine item) {
		int result = 0;
		if (this.getByID(item.getId_machine()) != null) {
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
	 * Update a Machine entity into database.
	 *
	 * @param item The Machine entity to persist
	 * @return count of updated entities
	 */
	public int update(final Machine item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 MachineSQLiteAdapter.COL_ID_MACHINE
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_machine()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Machine entity of database.
	 *
	 * @param id_machine id_machine
	 * @return count of updated entities
	 */
	public int remove(final int id_machine) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_machine);
		}

		
		final String whereClause =  MachineSQLiteAdapter.COL_ID_MACHINE
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_machine) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param machine The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Machine machine) {
		return this.delete(machine.getId_machine());
	}

	/**
	 *  Internal Cursor.
	 * @param id_machine id_machine
	 *  @return A Cursor pointing to the Machine corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_machine) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_machine);
		}

		final String whereClause =  MachineSQLiteAdapter.ALIASED_COL_ID_MACHINE
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_machine) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Machine entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_MACHINE + " = ?",
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
				ALIASED_COL_ID_MACHINE + " = ?",
				new String[]{String.valueOf(id)});
	}

}
