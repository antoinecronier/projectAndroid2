/**************************************************************************
 * UserTestDBBase.java, WindowsGesture2 Android
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

import com.gesture.data.UserSQLiteAdapter;
import com.gesture.entity.User;

import com.gesture.fixture.UserDataLoader;

import java.util.ArrayList;

import com.gesture.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** User database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit UserTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class UserTestDBBase extends TestDBBase {
	protected Context ctx;

	protected UserSQLiteAdapter adapter;

	protected User entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new UserSQLiteAdapter(this.ctx);
		this.adapter.open();

		ArrayList<User> entities = new ArrayList<User>(UserDataLoader.getInstance(this.ctx).getMap().values());
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
			User user = UserUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(user);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		User result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId_user()); // TODO Generate by @Id annotation

			UserUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			User user = UserUtils.generateRandom(this.ctx);
			user.setId_user(this.entity.getId_user());

			result = (int)this.adapter.update(user);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId_user());
			Assert.assertTrue(result >= 0);
		}
	}
}
