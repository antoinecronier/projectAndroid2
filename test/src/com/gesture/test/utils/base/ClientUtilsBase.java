/**************************************************************************
 * ClientUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Client;
import com.gesture.test.utils.*;




public abstract class ClientUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Client generateRandom(Context ctx){
		Client client = new Client();

		client.setId_client(TestUtils.generateRandomInt(0,100) + 1);
		client.setNom("nom_"+TestUtils.generateRandomString(10));

		return client;
	}

	public static boolean equals(Client client1, Client client2){
		boolean ret = true;
		Assert.assertNotNull(client1);
		Assert.assertNotNull(client2);
		if (client1!=null && client2 !=null){
			Assert.assertEquals(client1.getId_client(), client2.getId_client());
			Assert.assertEquals(client1.getNom(), client2.getNom());
		}

		return ret;
	}
}

