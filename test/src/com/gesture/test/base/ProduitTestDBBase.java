/**************************************************************************
 * ProduitTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.entity.Produit;


import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** Produit database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ProduitTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ProduitTestDBBase extends TestDBBase {
	protected Context ctx;

	protected ProduitSQLiteAdapter adapter;

	protected Produit entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ProduitSQLiteAdapter(this.ctx);
		this.adapter.open();

	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		this.adapter.close();

		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			Produit produit = ProduitUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(produit);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Produit result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_produit()); // TODO Generate by @Id annotation

			ProduitUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Produit produit = ProduitUtils.generateRandom(this.ctx);
			produit.setId_produit(this.entity.getId_produit());

			result = (int)this.adapter.update(produit);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_produit());
			Assert.assertTrue(result >= 0);
		}
	}
}
