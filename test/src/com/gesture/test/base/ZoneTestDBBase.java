/**************************************************************************
 * ZoneTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.ZoneSQLiteAdapter;
import com.gesture.entity.Zone;


import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** Zone database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ZoneTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneTestDBBase extends TestDBBase {
	protected Context ctx;

	protected ZoneSQLiteAdapter adapter;

	protected Zone entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ZoneSQLiteAdapter(this.ctx);
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
			Zone zone = ZoneUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(zone);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Zone result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_zone()); // TODO Generate by @Id annotation

			ZoneUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Zone zone = ZoneUtils.generateRandom(this.ctx);
			zone.setId_zone(this.entity.getId_zone());

			result = (int)this.adapter.update(zone);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_zone());
			Assert.assertTrue(result >= 0);
		}
	}
}
