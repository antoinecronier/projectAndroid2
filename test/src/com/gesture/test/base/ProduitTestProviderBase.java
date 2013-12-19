/**************************************************************************
 * ProduitTestProviderBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.gesture.provider.ProduitProviderAdapter;

import com.gesture.data.ProduitSQLiteAdapter;

import com.gesture.entity.Produit;


import java.util.ArrayList;
import com.gesture.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Produit database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ProduitTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ProduitTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected ProduitSQLiteAdapter adapter;

	protected Produit entity;
	protected ContentResolver provider;

	protected ArrayList<Produit> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ProduitSQLiteAdapter(this.ctx);

		this.provider = this.getMockContext().getContentResolver();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			Produit produit = ProduitUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(produit, 0);
				values.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);
				result = this.provider.insert(ProduitProviderAdapter.PRODUIT_URI, values);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertNotNull(result);
			Assert.assertTrue(Integer.valueOf(result.getEncodedPath().substring(result.getEncodedPath().lastIndexOf("/")+1)) > 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Produit result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(ProduitProviderAdapter.PRODUIT_URI + "/" + this.entity.getId_produit()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ProduitUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Produit> result = null;
		try {
			Cursor c = this.provider.query(ProduitProviderAdapter.PRODUIT_URI, this.adapter.getCols(), null, null, null);
			result = this.adapter.cursorToItems(c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.nbEntities);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Produit produit = ProduitUtils.generateRandom(this.ctx);

			try {
				produit.setId_produit(this.entity.getId_produit());

				ContentValues values = this.adapter.itemToContentValues(produit, 0);
				result = this.provider.update(
					Uri.parse(ProduitProviderAdapter.PRODUIT_URI
						+ "/"
						+ produit.getId_produit()),
					values,
					null,
					null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertTrue(result > 0);
		}
	}

	/** Test case UpdateAll Entity */
	@SmallTest
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			Produit produit = ProduitUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(produit, 0);
				values.remove(ProduitSQLiteAdapter.COL_ID_PRODUIT);

				result = this.provider.update(ProduitProviderAdapter.PRODUIT_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}

	/** Test case Delete Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(Uri.parse(ProduitProviderAdapter.PRODUIT_URI + "/" + this.entity.getId_produit()), null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}

	/** Test case DeleteAll Entity */
	@SmallTest
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(ProduitProviderAdapter.PRODUIT_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
