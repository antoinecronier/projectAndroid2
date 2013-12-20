/**************************************************************************
 * UserUtilsBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 20, 2013
 *
 **************************************************************************/
package com.gesture.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.gesture.entity.User;
import com.gesture.test.utils.*;




public abstract class UserUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static User generateRandom(Context ctx){
		User user = new User();

		user.setId_user(TestUtils.generateRandomInt(0,100) + 1);
		user.setLogin("login_"+TestUtils.generateRandomString(10));
		user.setPassword("password_"+TestUtils.generateRandomString(10));
		user.setRole(TestUtils.generateRandomInt(0,100));

		return user;
	}

	public static boolean equals(User user1, User user2){
		boolean ret = true;
		Assert.assertNotNull(user1);
		Assert.assertNotNull(user2);
		if (user1!=null && user2 !=null){
			Assert.assertEquals(user1.getId_user(), user2.getId_user());
			Assert.assertEquals(user1.getLogin(), user2.getLogin());
			Assert.assertEquals(user1.getPassword(), user2.getPassword());
			Assert.assertEquals(user1.getRole(), user2.getRole());
		}

		return ret;
	}
}

