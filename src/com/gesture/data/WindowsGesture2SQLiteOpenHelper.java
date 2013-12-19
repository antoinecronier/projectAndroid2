/**************************************************************************
 * WindowsGesture2SQLiteOpenHelper.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.data;

import com.gesture.data.base.WindowsGesture2SQLiteOpenHelperBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class WindowsGesture2SQLiteOpenHelper
					extends WindowsGesture2SQLiteOpenHelperBase {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param name name
	 * @param factory factory
	 * @param version version
	 */
	public WindowsGesture2SQLiteOpenHelper(final Context ctx,
		   final String name, final CursorFactory factory, final int version) {
		super(ctx, name, factory, version);
	}

}
