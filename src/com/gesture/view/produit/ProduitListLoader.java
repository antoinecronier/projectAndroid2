/**************************************************************************
 * ProduitListLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.produit;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.gesture.criterias.ProduitCriterias;

/**
 * Produit Loader.
 */
public class ProduitListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit ProduitCriterias
	 */
	public ProduitListLoader(
			final Context ctx,
			final ProduitCriterias crit) {
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
	public ProduitListLoader(
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
	 * @param criterias ProduitCriterias
	 * @param sortOrder The sort order
	 */
	public ProduitListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					ProduitCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
