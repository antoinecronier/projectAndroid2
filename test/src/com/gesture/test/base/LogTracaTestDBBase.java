/**************************************************************************
 * LogTracaTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.entity.LogTraca;


import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** LogTraca database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit LogTracaTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class LogTracaTestDBBase extends TestDBBase {
	protected Context ctx;

	protected LogTracaSQLiteAdapter adapter;

	protected LogTraca entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new LogTracaSQLiteAdapter(this.ctx);
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
			LogTraca logTraca = LogTracaUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(logTraca);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		LogTraca result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_log()); // TODO Generate by @Id annotation

			LogTracaUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			LogTraca logTraca = LogTracaUtils.generateRandom(this.ctx);
			logTraca.setId_log(this.entity.getId_log());

			result = (int)this.adapter.update(logTraca);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_log());
			Assert.assertTrue(result >= 0);
		}
	}
}
