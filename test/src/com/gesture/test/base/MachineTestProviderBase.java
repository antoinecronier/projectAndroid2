/**************************************************************************
 * MachineTestProviderBase.java, WindowsGesture2 Android
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

import com.gesture.provider.MachineProviderAdapter;

import com.gesture.data.MachineSQLiteAdapter;

import com.gesture.entity.Machine;

import com.gesture.fixture.MachineDataLoader;
import com.gesture.fixture.MachineDataLoader;

import java.util.ArrayList;
import com.gesture.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Machine database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit MachineTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class MachineTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected MachineSQLiteAdapter adapter;

	protected Machine entity;
	protected ContentResolver provider;

	protected ArrayList<Machine> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new MachineSQLiteAdapter(this.ctx);

		this.entities = new ArrayList<Machine>(MachineDataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		this.nbEntities += MachineDataLoader.getInstance(this.ctx).getMap().size();
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
			Machine machine = MachineUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(machine);
				values.remove(MachineSQLiteAdapter.COL_ID_MACHINE);
				result = this.provider.insert(MachineProviderAdapter.MACHINE_URI, values);

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
		Machine result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(MachineProviderAdapter.MACHINE_URI + "/" + this.entity.getId_machine()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			MachineUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Machine> result = null;
		try {
			Cursor c = this.provider.query(MachineProviderAdapter.MACHINE_URI, this.adapter.getCols(), null, null, null);
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
			Machine machine = MachineUtils.generateRandom(this.ctx);

			try {
				machine.setId_machine(this.entity.getId_machine());

				ContentValues values = this.adapter.itemToContentValues(machine);
				result = this.provider.update(
					Uri.parse(MachineProviderAdapter.MACHINE_URI
						+ "/"
						+ machine.getId_machine()),
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
			Machine machine = MachineUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(machine);
				values.remove(MachineSQLiteAdapter.COL_ID_MACHINE);
				values.remove(MachineSQLiteAdapter.COL_NOM);

				result = this.provider.update(MachineProviderAdapter.MACHINE_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(MachineProviderAdapter.MACHINE_URI + "/" + this.entity.getId_machine()), null, null);

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
				result = this.provider.delete(MachineProviderAdapter.MACHINE_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
