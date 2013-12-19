/**************************************************************************
 * LogTestProviderBase.java, WindowsGesture2 Android
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

import com.gesture.provider.LogProviderAdapter;

import com.gesture.data.LogSQLiteAdapter;

import com.gesture.entity.Log;


import java.util.ArrayList;
import com.gesture.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Log database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit LogTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class LogTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected LogSQLiteAdapter adapter;

	protected Log entity;
	protected ContentResolver provider;

	protected ArrayList<Log> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new LogSQLiteAdapter(this.ctx);

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
			Log log = LogUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(log, 0);
				values.remove(LogSQLiteAdapter.COL_ID_LOG);
				result = this.provider.insert(LogProviderAdapter.LOG_URI, values);

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
		Log result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(LogProviderAdapter.LOG_URI + "/" + this.entity.getId_log()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			LogUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Log> result = null;
		try {
			Cursor c = this.provider.query(LogProviderAdapter.LOG_URI, this.adapter.getCols(), null, null, null);
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
			Log log = LogUtils.generateRandom(this.ctx);

			try {
				log.setId_log(this.entity.getId_log());

				ContentValues values = this.adapter.itemToContentValues(log, 0);
				result = this.provider.update(
					Uri.parse(LogProviderAdapter.LOG_URI
						+ "/"
						+ log.getId_log()),
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
			Log log = LogUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(log, 0);
				values.remove(LogSQLiteAdapter.COL_ID_LOG);

				result = this.provider.update(LogProviderAdapter.LOG_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(LogProviderAdapter.LOG_URI + "/" + this.entity.getId_log()), null, null);

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
				result = this.provider.delete(LogProviderAdapter.LOG_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
