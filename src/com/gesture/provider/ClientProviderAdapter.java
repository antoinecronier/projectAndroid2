/**************************************************************************
 * ClientProviderAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gesture.provider.base.ClientProviderAdapterBase;

/**
 * ClientProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ClientProviderAdapter
					extends ClientProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ClientProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ClientProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

