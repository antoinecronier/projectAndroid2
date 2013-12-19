/**************************************************************************
 * LogTracaListLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.logtraca;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.gesture.criterias.LogTracaCriterias;

/**
 * LogTraca Loader.
 */
public class LogTracaListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit LogTracaCriterias
	 */
	public LogTracaListLoader(
			final Context ctx,
			final LogTracaCriterias crit) {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param selection The selection
	 * @param selectionArgs The selection Args
	 * @param sortOrder The sort order
	 */
	public LogTracaListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					String selection,
					String[] selectionArgs,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				selection,
				selectionArgs,
				sortOrder);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param criterias LogTracaCriterias
	 * @param sortOrder The sort order
	 */
	public LogTracaListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					LogTracaCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
