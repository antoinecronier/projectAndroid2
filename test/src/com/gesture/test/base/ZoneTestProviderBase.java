/**************************************************************************
 * ZoneTestProviderBase.java, WindowsGesture2 Android
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

import com.gesture.provider.ZoneProviderAdapter;

import com.gesture.data.ZoneSQLiteAdapter;

import com.gesture.entity.Zone;

import com.gesture.fixture.ZoneDataLoader;
import com.gesture.fixture.ZoneDataLoader;

import java.util.ArrayList;
import com.gesture.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Zone database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ZoneTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected ZoneSQLiteAdapter adapter;

	protected Zone entity;
	protected ContentResolver provider;

	protected ArrayList<Zone> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ZoneSQLiteAdapter(this.ctx);

		this.entities = new ArrayList<Zone>(ZoneDataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		this.nbEntities += ZoneDataLoader.getInstance(this.ctx).getMap().size();
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
			Zone zone = ZoneUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(zone);
				values.remove(ZoneSQLiteAdapter.COL_ID_ZONE);
				result = this.provider.insert(ZoneProviderAdapter.ZONE_URI, values);

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
		Zone result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(ZoneProviderAdapter.ZONE_URI + "/" + this.entity.getId_zone()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ZoneUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Zone> result = null;
		try {
			Cursor c = this.provider.query(ZoneProviderAdapter.ZONE_URI, this.adapter.getCols(), null, null, null);
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
			Zone zone = ZoneUtils.generateRandom(this.ctx);

			try {
				zone.setId_zone(this.entity.getId_zone());

				ContentValues values = this.adapter.itemToContentValues(zone);
				result = this.provider.update(
					Uri.parse(ZoneProviderAdapter.ZONE_URI
						+ "/"
						+ zone.getId_zone()),
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
			Zone zone = ZoneUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(zone);
				values.remove(ZoneSQLiteAdapter.COL_ID_ZONE);
				values.remove(ZoneSQLiteAdapter.COL_NOM);

				result = this.provider.update(ZoneProviderAdapter.ZONE_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(ZoneProviderAdapter.ZONE_URI + "/" + this.entity.getId_zone()), null, null);

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
				result = this.provider.delete(ZoneProviderAdapter.ZONE_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
