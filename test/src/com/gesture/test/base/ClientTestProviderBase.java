/**************************************************************************
 * ClientTestProviderBase.java, WindowsGesture2 Android
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

import com.gesture.provider.ClientProviderAdapter;

import com.gesture.data.ClientSQLiteAdapter;

import com.gesture.entity.Client;

import com.gesture.fixture.ClientDataLoader;
import com.gesture.fixture.ClientDataLoader;

import java.util.ArrayList;
import com.gesture.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Client database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ClientTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ClientTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected ClientSQLiteAdapter adapter;

	protected Client entity;
	protected ContentResolver provider;

	protected ArrayList<Client> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ClientSQLiteAdapter(this.ctx);

		this.entities = new ArrayList<Client>(ClientDataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		this.nbEntities += ClientDataLoader.getInstance(this.ctx).getMap().size();
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
			Client client = ClientUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(client);
				values.remove(ClientSQLiteAdapter.COL_ID_CLIENT);
				result = this.provider.insert(ClientProviderAdapter.CLIENT_URI, values);

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
		Client result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(ClientProviderAdapter.CLIENT_URI + "/" + this.entity.getId_client()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ClientUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Client> result = null;
		try {
			Cursor c = this.provider.query(ClientProviderAdapter.CLIENT_URI, this.adapter.getCols(), null, null, null);
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
			Client client = ClientUtils.generateRandom(this.ctx);

			try {
				client.setId_client(this.entity.getId_client());

				ContentValues values = this.adapter.itemToContentValues(client);
				result = this.provider.update(
					Uri.parse(ClientProviderAdapter.CLIENT_URI
						+ "/"
						+ client.getId_client()),
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
			Client client = ClientUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(client);
				values.remove(ClientSQLiteAdapter.COL_ID_CLIENT);
				values.remove(ClientSQLiteAdapter.COL_NOM);

				result = this.provider.update(ClientProviderAdapter.CLIENT_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(ClientProviderAdapter.CLIENT_URI + "/" + this.entity.getId_client()), null, null);

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
				result = this.provider.delete(ClientProviderAdapter.CLIENT_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
