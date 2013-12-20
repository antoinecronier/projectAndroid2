/**************************************************************************
 * UserSQLiteAdapterBase.java, WindowsGesture2 Android
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

import com.gesture.data.UserSQLiteAdapter;
import com.gesture.entity.User;


import com.gesture.WindowsGesture2Application;


/** User adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserSQLiteAdapterBase
						extends SQLiteAdapterBase<User> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "User";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id_user. */
	public static final String COL_ID_USER =
			"id_user";
	/** Alias. */
	public static final String ALIASED_COL_ID_USER =
			TABLE_NAME + "." + COL_ID_USER;
	/** login. */
	public static final String COL_LOGIN =
			"login";
	/** Alias. */
	public static final String ALIASED_COL_LOGIN =
			TABLE_NAME + "." + COL_LOGIN;
	/** password. */
	public static final String COL_PASSWORD =
			"password";
	/** Alias. */
	public static final String ALIASED_COL_PASSWORD =
			TABLE_NAME + "." + COL_PASSWORD;
	/** role. */
	public static final String COL_ROLE =
			"role";
	/** Alias. */
	public static final String ALIASED_COL_ROLE =
			TABLE_NAME + "." + COL_ROLE;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		UserSQLiteAdapter.COL_ID_USER,
		UserSQLiteAdapter.COL_LOGIN,
		UserSQLiteAdapter.COL_PASSWORD,
		UserSQLiteAdapter.COL_ROLE
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		UserSQLiteAdapter.ALIASED_COL_ID_USER,
		UserSQLiteAdapter.ALIASED_COL_LOGIN,
		UserSQLiteAdapter.ALIASED_COL_PASSWORD,
		UserSQLiteAdapter.ALIASED_COL_ROLE
	};

	/**
	 * Get the table name used in DB for your User entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your User entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the User entity table.
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
		
		 + COL_ID_USER	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_LOGIN	+ " VARCHAR NOT NULL,"
		 + COL_PASSWORD	+ " VARCHAR NOT NULL,"
		 + COL_ROLE	+ " INTEGER NOT NULL"
		
		
		+ ", UNIQUE(" + COL_LOGIN + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert User entity to Content Values for database.
	 * @param item User entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final User item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID_USER,
			String.valueOf(item.getId_user()));

		if (item.getLogin() != null) {
			result.put(COL_LOGIN,
				item.getLogin());
		}

		if (item.getPassword() != null) {
			result.put(COL_PASSWORD,
				item.getPassword());
		}

		result.put(COL_ROLE,
			String.valueOf(item.getRole()));


		return result;
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @return User entity
	 */
	public User cursorToItem(final Cursor cursor) {
		User result = new User();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @param result User entity
	 */
	public void cursorToItem(final Cursor cursor, final User result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID_USER);
			result.setId_user(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_LOGIN);
			result.setLogin(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_PASSWORD);
			result.setPassword(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_ROLE);
			result.setRole(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read User by id in database.
	 *
	 * @param id Identify of User
	 * @return User entity
	 */
	public User getByID(final int id_user) {
		final Cursor cursor = this.getSingleCursor(id_user);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final User result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Users entities.
	 *
	 * @return List of User entities
	 */
	public ArrayList<User> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<User> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return Id of the User entity
	 */
	public long insert(final User item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(UserSQLiteAdapter.COL_ID_USER);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					UserSQLiteAdapter.COL_ID_USER,
					values);
		}
		item.setId_user((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a User entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The User entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final User item) {
		int result = 0;
		if (this.getByID(item.getId_user()) != null) {
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
	 * Update a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return count of updated entities
	 */
	public int update(final User item) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 UserSQLiteAdapter.COL_ID_USER
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId_user()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a User entity of database.
	 *
	 * @param id_user id_user
	 * @return count of updated entities
	 */
	public int remove(final int id_user) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id_user);
		}

		
		final String whereClause =  UserSQLiteAdapter.COL_ID_USER
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_user) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param user The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final User user) {
		return this.delete(user.getId_user());
	}

	/**
	 *  Internal Cursor.
	 * @param id_user id_user
	 *  @return A Cursor pointing to the User corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id_user) {
		if (WindowsGesture2Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + id_user);
		}

		final String whereClause =  UserSQLiteAdapter.ALIASED_COL_ID_USER
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id_user) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a User entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID_USER + " = ?",
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
				ALIASED_COL_ID_USER + " = ?",
				new String[]{String.valueOf(id)});
	}

}
