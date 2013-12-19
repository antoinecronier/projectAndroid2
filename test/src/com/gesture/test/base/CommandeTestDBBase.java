/**************************************************************************
 * CommandeTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.entity.Commande;

import com.gesture.fixture.CommandeDataLoader;

import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** Commande database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit CommandeTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class CommandeTestDBBase extends TestDBBase {
	protected Context ctx;

	protected CommandeSQLiteAdapter adapter;

	protected Commande entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new CommandeSQLiteAdapter(this.ctx);
		this.adapter.open();

		ArrayList<Commande> entities = new ArrayList<Commande>(CommandeDataLoader.getInstance(this.ctx).getMap().values());
		if (entities.size()>0){
			this.entity = entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
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
			Commande commande = CommandeUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(commande);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Commande result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_cmd()); // TODO Generate by @Id annotation

			CommandeUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Commande commande = CommandeUtils.generateRandom(this.ctx);
			commande.setId_cmd(this.entity.getId_cmd());

			result = (int)this.adapter.update(commande);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_cmd());
			Assert.assertTrue(result >= 0);
		}
	}
}
