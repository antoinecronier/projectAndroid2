/**************************************************************************
 * ZoneSQLiteAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.data;

import com.gesture.data.base.ZoneSQLiteAdapterBase;
import android.content.Context;

/**
 * Zone adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class ZoneSQLiteAdapter extends ZoneSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ZoneSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
