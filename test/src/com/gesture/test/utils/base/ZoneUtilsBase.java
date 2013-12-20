/**************************************************************************
 * ZoneUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Zone;
import com.gesture.test.utils.*;




public abstract class ZoneUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Zone generateRandom(Context ctx){
		Zone zone = new Zone();

		zone.setId_zone(TestUtils.generateRandomInt(0,100) + 1);
		zone.setNom("nom_"+TestUtils.generateRandomString(10));

		return zone;
	}

	public static boolean equals(Zone zone1, Zone zone2){
		boolean ret = true;
		Assert.assertNotNull(zone1);
		Assert.assertNotNull(zone2);
		if (zone1!=null && zone2 !=null){
			Assert.assertEquals(zone1.getId_zone(), zone2.getId_zone());
			Assert.assertEquals(zone1.getNom(), zone2.getNom());
		}

		return ret;
	}
}

