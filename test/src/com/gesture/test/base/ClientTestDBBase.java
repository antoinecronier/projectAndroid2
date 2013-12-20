/**************************************************************************
 * ClientTestDBBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 20, 2013
 *
 **************************************************************************/
package com.gesture.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.gesture.data.ClientSQLiteAdapter;
import com.gesture.entity.Client;

import com.gesture.fixture.ClientDataLoader;

import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** Client database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ClientTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ClientTestDBBase extends TestDBBase {
	protected Context ctx;

	protected ClientSQLiteAdapter adapter;

	protected Client entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ClientSQLiteAdapter(this.ctx);
		this.adapter.open();

		ArrayList<Client> entities = new ArrayList<Client>(ClientDataLoader.getInstance(this.ctx).getMap().values());
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
			Client client = ClientUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(client);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Client result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_client()); // TODO Generate by @Id annotation

			ClientUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Client client = ClientUtils.generateRandom(this.ctx);
			client.setId_client(this.entity.getId_client());

			result = (int)this.adapter.update(client);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_client());
			Assert.assertTrue(result >= 0);
		}
	}
}
