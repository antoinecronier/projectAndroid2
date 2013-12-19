/**************************************************************************
 * MachineTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.entity.Machine;

import com.gesture.fixture.MachineDataLoader;

import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** Machine database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit MachineTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class MachineTestDBBase extends TestDBBase {
	protected Context ctx;

	protected MachineSQLiteAdapter adapter;

	protected Machine entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new MachineSQLiteAdapter(this.ctx);
		this.adapter.open();

		ArrayList<Machine> entities = new ArrayList<Machine>(MachineDataLoader.getInstance(this.ctx).getMap().values());
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
			Machine machine = MachineUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(machine);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Machine result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_machine()); // TODO Generate by @Id annotation

			MachineUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Machine machine = MachineUtils.generateRandom(this.ctx);
			machine.setId_machine(this.entity.getId_machine());

			result = (int)this.adapter.update(machine);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_machine());
			Assert.assertTrue(result >= 0);
		}
	}
}
