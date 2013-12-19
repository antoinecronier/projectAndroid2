/**************************************************************************
 * LogTracaSQLiteAdapterBase.java, WindowsGesture2 Android
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
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;
import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;
import com.gesture.entity.Machine;
import com.gesture.entity.User;

import com.gesture.harmony.util.DateUtils;
import com.gesture.WindowsGesture2Application;


/** LogTraca adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit LogTracaAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class LogTracaSQLiteAdapterBase
						extends SQLiteAdapterBase<LogTraca> {

	/** TAG for debug purpose. */
	protected static final String TAG = "LogTracaDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "LogTraca";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_log. */
	public static final String COL_ID_LOG =
			"id_log";
	/** Alias. */
	public static final String ALIASED_COL_ID_LOG =
			TABLE_NAME + "." + COL_ID_LOG;
	/** produit. */
	public static final String COL_PRODUIT =
			"produit";
	/** Alias. */
	public static final String ALIASED_COL_PRODUIT =
			TABLE_NAME + "." + COL_PRODUIT;
	/** machine. */
	public static final String COL_MACHINE =
			"machine";
	/** Alias. */
	public static final String ALIASED_COL_MACHINE =
			TABLE_NAME + "." + COL_MACHINE;
	/** user. */
	public static final String COL_USER =
			"user";
	/** Alias. */
	public static final String ALIASED_COL_USER =
			TABLE_NAME + "." + COL_USER;
	/** duree. */
	public static final String COL_DUREE =
			"duree";
	/** Alias. */
	public static final String ALIASED_COL_DUREE =
			TABLE_NAME + "." + COL_DUREE;
	/** dateEntre. */
	public static final String COL_DATEENTRE =
			"dateEntre";
	/** Alias. */
	public static final String ALIASED_COL_DATEENTRE =
			TABLE_NAME + "." + COL_DATEENTRE;
	/** dateSortie. */
	public static final String COL_DATESORTIE =
			"dateSortie";
	/** Alias. */
	public static final String ALIASED_COL_DATESORTIE =
			TABLE_NAME + "." + COL_DATESORTIE;
	/** Machine_LogTracas_internal. */
	public static final String COL_MACHINELOGTRACASINTERNAL =
			"Machine_LogTracas_internal";
	/** Alias. */
	public static final String ALIASED_COL_MACHINELOGTRACASINTERNAL =
			TABLE_NAME + "." + COL_MACHINELOGTRACASINTERNAL;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		LogTracaSQLiteAdapter.COL_ID_LOG,
		LogTracaSQLiteAdapter.COL_PRODUIT,
		LogTracaSQLiteAdapter.COL_MACHINE,
		LogTracaSQLiteAdapter.COL_USER,
		LogTracaSQLiteAdapter.COL_DUREE,
		LogTracaSQLiteAdapter.COL_DATEENTRE,
		LogTracaSQLiteAdapter.COL_DATESORTIE,
		LogTracaSQLiteAdapter.COL_MACHINELOGTRACASINTERNAL
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		LogTracaSQLiteAdapter.ALIASED_COL_ID_LOG,
		LogTracaSQLiteAdapter.ALIASED_COL_PRODUIT,
		LogTracaSQLiteAdapter.ALIASED_COL_MACHINE,
		LogTracaSQLiteAdapter.ALIASED_COL_USER,
		LogTracaSQLiteAdapter.ALIASED_COL_DUREE,
		LogTracaSQLiteAdapter.ALIASED_COL_DATEENTRE,
		LogTracaSQLiteAdapter.ALIASED_COL_DATESORTIE,
		LogTracaSQLiteAdapter.ALIASED_COL_MACHINELOGTRACASINTERNAL
	};

	/**
	 * Get the table name used in DB for your LogTraca entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your LogTraca entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the LogTraca entity table.
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
		
		 + COL_ID_LOG	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_PRODUIT	+ " INTEGER NOT NULL,"
		 + COL_MACHINE	+ " INTEGER NOT NULL,"
		 + COL_USER	+ " INTEGER NOT NULL,"
		 + COL_DUREE	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_DATEENTRE	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_DATESORTIE	+ " DATETIME(2147483647) NOT NULL,"
		 + COL_MACHINELOGTRACASINTERNAL	+ " INTEGER,"
		
		
		 + "FOREIGN KEY(" + COL_PRODUIT + ") REFERENCES " 
			 + ProduitSQLiteAdapter.TABLE_NAME 
				+ " (" + ProduitSQLiteAdapter.COL_ID_PRODUIT + "),"
		 + "FOREIGN KEY(" + COL_MACHINE + ") REFERENCES " 
			 + MachineSQLiteAdapter.TABLE_NAME 
				+ " (" + MachineSQLiteAdapter.COL_ID_MACHINE + "),"
		 + "FOREIGN KEY(" + COL_USER + ") REFERENCES " 
			 + UserSQLiteAdapter.TABLE_NAME 
				+ " (" + UserSQLiteAdapter.COL_ID_USER + "),"
		 + "FOREIGN KEY(" + COL_MACHINELOGTRACASINTERNAL + ") REFERENCES " 
			 + MachineSQLiteAdapter.TABLE_NAME 
				+ " (" + MachineSQLiteAdapter.COL_ID_MACHINE + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogTracaSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters
	/** Convert LogTraca entity to Content Values for database.
	 *
	 * @param item LogTraca entity object
	 * @param machineId machine id
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final LogTraca item,
				int machineId) {
		final ContentValues result = this.itemToContentValues(item);
		result.put(COL_MACHINELOGTRACASINTERNAL,
				String.valueOf(machineId));
		return result;
	}

	/**
	 * Convert LogTraca entity to Content Values for database.
	 * @param item LogTraca entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final LogTraca item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_LOG,
			String.valueOf(item.getId_log()));

		if (item.getProduit() != null) {
			result.put(COL_PRODUIT,
				item.getProduit().getId_produit());
		}

		if (item.getMachine() != null) {
			result.put(COL_MACHINE,
				item.getMachine().getId_machine());
		}

		if (item.getUser() != null) {
			result.put(COL_USER,
				item.getUser().getId_user());
		}

		if (item.getDuree() != null) {
			result.put(COL_DUREE,
				item.getDuree());
		}

		if (item.getDateEntre() != null) {
			result.put(COL_DATEENTRE,
				item.getDateEntre());
		}

		if (item.getDateSortie() != null) {
			result.put(COL_DATESORTIE,
				item.getDateSortie());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to LogTraca entity.
	 * @param cursor Cursor object
	 * @return LogTraca entity
	 */
	public LogTraca cursorToItem(final Cursor cursor) {
		LogTraca result = new LogTraca();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to LogTraca entity.
	 * @param cursor Cursor object
	 * @param result LogTraca entity
	 */
	public void cursorToItem(final Cursor cursor, final LogTraca result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_LOG);
			result.setId_log(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_PRODUIT);
			final Produit produit = new Produit();
			produit.setId_produit(cursor.getInt(index));
			result.setProduit(produit);

			index = cursor.getColumnIndexOrThrow(COL_MACHINE);
			final Machine machine = new Machine();
			machine.setId_machine(cursor.getInt(index));
			result.setMachine(machine);

			index = cursor.getColumnIndexOrThrow(COL_USER);
			final User user = new User();
			user.setId_user(cursor.getInt(index));
			result.setUser(user);

			index = cursor.getColumnIndexOrThrow(COL_DUREE);
			result.setDuree(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_DATEENTRE);
			result.setDateEntre(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_DATESORTIE);
			result.setDateSortie(
					cursor.getString(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read LogTraca by id in database.
	 *
	 * @param id Identify of LogTraca
	 * @return LogTraca entity
	 */
	public LogTraca getByID(final int id_log) {
		final Cursor cursor = this.getSingleCursor(id_log);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final LogTraca result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getProduit() != null) {
			final ProduitSQLiteAdapter produitAdapter =
					new ProduitSQLiteAdapter(this.ctx);
			produitAdapter.open(this.mDatabase);
			
			result.setProduit(produitAdapter.getByID(
							result.getProduit().getId_produit()));
		}
		if (result.getMachine() != null) {
			final MachineSQLiteAdapter machineAdapter =
					new MachineSQLiteAdapter(this.ctx);
			machineAdapter.open(this.mDatabase);
			
			result.setMachine(machineAdapter.getByID(
							result.getMachine().getId_machine()));
		}
		if (result.getUser() != null) {
			final UserSQLiteAdapter userAdapter =
					new UserSQLiteAdapter(this.ctx);
			userAdapter.open(this.mDatabase);
			
			result.setUser(userAdapter.getByID(
							result.getUser().getId_user()));
		}
		return result;
	}

	/**
	 * Find & read LogTraca by produit.
	 * @param produitId produitId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogTraca entities
	 */
	 public Cursor getByProduit(final int produitId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogTracaSQLiteAdapter.COL_PRODUIT + "=?";
		String idSelectionArgs = String.valueOf(produitId);
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
	 * Find & read LogTraca by machine.
	 * @param machineId machineId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogTraca entities
	 */
	 public Cursor getByMachine(final int machineId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogTracaSQLiteAdapter.COL_MACHINE + "=?";
		String idSelectionArgs = String.valueOf(machineId);
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
	 * Find & read LogTraca by user.
	 * @param userId userId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogTraca entities
	 */
	 public Cursor getByUser(final int userId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogTracaSQLiteAdapter.COL_USER + "=?";
		String idSelectionArgs = String.valueOf(userId);
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
	 * Find & read LogTraca by MachineLogTracasInternal.
	 * @param machinelogtracasinternalId machinelogtracasinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogTraca entities
	 */
	 public Cursor getByMachineLogTracasInternal(final int machinelogtracasinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogTracaSQLiteAdapter.COL_MACHINELOGTRACASINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(machinelogtracasinternalId);
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
	 * Read All LogTracas entities.
	 *
	 * @return List of LogTraca entities
	 */
	public ArrayList<LogTraca> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<LogTraca> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a LogTraca entity into database.
	 *
	 * @param item The LogTraca entity to persist
	 * @return Id of the LogTraca entity
	 */
	public long insert(final LogTraca item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		values.remove(LogTracaSQLiteAdapter.COL_ID_LOG);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					LogTracaSQLiteAdapter.COL_ID_LOG,
					values);
		}
		item.setId_log((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a LogTraca entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The LogTraca entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final LogTraca item) {
		int result = 0;
		if (this.getByID(item.getId_log()) != null) {
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
	 * Update a LogTraca entity into database.
	 *
	 * @param item The LogTraca entity to persist
	 * @return count of updated entities
	 */
	public int update(final LogTraca item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		final String whereClause =
				 LogTracaSQLiteAdapter.COL_ID_LOG
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_log()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a LogTraca entity into database.
	 *
	 * @param item The LogTraca entity to persist
	 * @param machineId The machine id
	 * @return count of updated entities
	 */
	public int updateWithMachineLogTracas(
					LogTraca item, int machineId) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		ContentValues values =
				this.itemToContentValues(item,
							machineId);
		String whereClause =
				 LogTracaSQLiteAdapter.COL_ID_LOG
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId_log()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a LogTraca entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The LogTraca entity to persist
	 * @param machineId The machine id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithMachineLogTracas(
			LogTraca item, int machineId) {
		int result = 0;
		if (this.getByID(item.getId_log()) != null) {
			// Item already exists => update it
			result = this.updateWithMachineLogTracas(item,
					machineId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithMachineLogTracas(item,
					machineId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a LogTraca entity into database.
	 *
	 * @param item The LogTraca entity to persist
	 * @param machineId The machine id
	 * @return Id of the LogTraca entity
	 */
	public long insertWithMachineLogTracas(
			LogTraca item, int machineId) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		ContentValues values = this.itemToContentValues(item,
				machineId);
		values.remove(LogTracaSQLiteAdapter.COL_ID_LOG);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Delete a LogTraca entity of database.
	 *
	 * @param id_log id_log
	 * @return count of updated entities
	 */
	public int remove(final int id_log) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_log);
		}

		
		final String whereClause =  LogTracaSQLiteAdapter.COL_ID_LOG
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_log) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param logTraca The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final LogTraca logTraca) {
		return this.delete(logTraca.getId_log());
	}

	/**
	 *  Internal Cursor.
	 * @param id_log id_log
	 *  @return A Cursor pointing to the LogTraca corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_log) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_log);
		}

		final String whereClause =  LogTracaSQLiteAdapter.ALIASED_COL_ID_LOG
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_log) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a LogTraca entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_LOG + " = ?",
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
				ALIASED_COL_ID_LOG + " = ?",
				new String[]{String.valueOf(id)});
	}

}
