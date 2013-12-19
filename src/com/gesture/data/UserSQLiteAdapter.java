/**************************************************************************
 * UserSQLiteAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.data;

import com.gesture.data.base.UserSQLiteAdapterBase;
import com.gesture.entity.User;

import android.content.Context;
import android.database.Cursor;

/**
 * User adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class UserSQLiteAdapter extends UserSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
	
	/**
	 * Find & read User by name & pwd in database.
	 * @param name_user
	 * @param pwd_user
	 * @return
	 */
	public User getByNamePassword(String name_user, String pwd_user) {
		final Cursor cursor = this.getCursor(name_user, pwd_user);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final User result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}
	
	/**
	 *  Internal Cursor.
	 * @param id_user id_user
	 *  @return A Cursor pointing to the User corresponding
	 *		to the given id.
	 */
	protected Cursor getCursor(String name_user, String pwd_user) {

		final String whereClause =  UserSQLiteAdapter.ALIASED_COL_LOGIN
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(name_user), String.valueOf(pwd_user) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}
}
