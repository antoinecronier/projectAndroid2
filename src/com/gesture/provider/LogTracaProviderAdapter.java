/**************************************************************************
 * LogTracaProviderAdapter.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider;

import android.app.DownloadManager.Query;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;
import com.gesture.entity.Commande;
import com.gesture.provider.base.LogTracaProviderAdapterBase;
import com.gesture.provider.base.WindowsGesture2ProviderBase;

/**
 * LogTracaProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class LogTracaProviderAdapter
					extends LogTracaProviderAdapterBase {
	
	/** LOGTRACA_USER. */
	protected static final int PRODUIT_COMMANDE =
			2068130946;
	
	/**
	 * Static constructor.
	 */
	static {
		LOGTRACA_URI =
				WindowsGesture2Provider.generateUri(
						logTracaType);
		
		WindowsGesture2Provider.getUriMatcher().addURI(
				WindowsGesture2Provider.authority,
				logTracaType + "/#/produitOnCommande",
				PRODUIT_COMMANDE);
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogTracaProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public LogTracaProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
		this.uriIds.add(PRODUIT_COMMANDE);
	}
	
	@Override
	public String getType(final Uri uri) {
		
		String result = super.getType(uri);
		final String collection =
				"vnc.android.cursor.collection/"
					+ WindowsGesture2Provider.authority + ".";

		int matchedUri = WindowsGesture2ProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			
			case PRODUIT_COMMANDE:
				result = collection + "logtraca";
				break;
			default:
				result = null;
				break;
		}

		return result;
	}
	
	@Override
	public Cursor query(final Uri uri,
						String[] projection,
						String selection,
						String[] selectionArgs,
						final String sortOrder) {

		int matchedUri = WindowsGesture2ProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		Cursor logTracaCursor;
		

//		switch (matchedUri) {
//			case PRODUIT_COMMANDE:
//				//uri.getPathSegments().get(1)
//				//TODO for modification
//				logTracaCursor = this.adapter.query(
//							LogTracaSQLiteAdapter.ALIASED_COLS,
//							selection,
//							selectionArgs,
//							null,
//							null,
//							null);
//				
//				if (logTracaCursor.getCount() > 0) {
//					logTracaCursor.moveToFirst();
//					
//					int produitId = logTracaCursor.getInt(logTracaCursor.getColumnIndex(
//									LogTracaSQLiteAdapter.COL_PRODUIT));
//					
//					ProduitSQLiteAdapter produitAdapter = new ProduitSQLiteAdapter(ctx);
//					produitAdapter.open(getDb());
//					result = produitAdapter.query(produitId);
//					
//				}
//				break;
//
//			
//			default:
//				result = null;
//				break;
//		}

		return result;
	}
}

