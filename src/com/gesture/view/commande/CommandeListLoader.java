/**************************************************************************
 * CommandeListLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.gesture.criterias.CommandeCriterias;

/**
 * Commande Loader.
 */
public class CommandeListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit CommandeCriterias
	 */
	public CommandeListLoader(
			final Context ctx,
			final CommandeCriterias crit) {
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
	public CommandeListLoader(
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
	 * @param criterias CommandeCriterias
	 * @param sortOrder The sort order
	 */
	public CommandeListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					CommandeCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
