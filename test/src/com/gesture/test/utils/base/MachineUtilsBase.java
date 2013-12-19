/**************************************************************************
 * MachineUtilsBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.gesture.entity.Machine;
import com.gesture.test.utils.*;



import com.gesture.entity.Zone;
import com.gesture.fixture.ZoneDataLoader;
import com.gesture.entity.LogTraca;
import com.gesture.fixture.LogTracaDataLoader;
import java.util.ArrayList;

public abstract class MachineUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Machine generateRandom(Context ctx){
		Machine machine = new Machine();

		machine.setId_machine(TestUtils.generateRandomInt(0,100) + 1);
		machine.setNom("nom_"+TestUtils.generateRandomString(10));
		ArrayList<Zone> zones =
			new ArrayList<Zone>(ZoneDataLoader.getInstance(ctx).getMap().values());
		if (!zones.isEmpty()) {
			machine.setZone(zones.get(TestUtils.generateRandomInt(0, zones.size())));
		}
		ArrayList<LogTraca> logTracass =
			new ArrayList<LogTraca>(LogTracaDataLoader.getInstance(ctx).getMap().values());
		ArrayList<LogTraca> relatedLogTracass = new ArrayList<LogTraca>();
		if (!logTracass.isEmpty()) {
			relatedLogTracass.add(logTracass.get(TestUtils.generateRandomInt(0, logTracass.size())));
			machine.setLogTracas(relatedLogTracass);
		}

		return machine;
	}

	public static boolean equals(Machine machine1, Machine machine2){
		boolean ret = true;
		Assert.assertNotNull(machine1);
		Assert.assertNotNull(machine2);
		if (machine1!=null && machine2 !=null){
			Assert.assertEquals(machine1.getId_machine(), machine2.getId_machine());
			Assert.assertEquals(machine1.getNom(), machine2.getNom());
			if (machine1.getZone() != null
					&& machine2.getZone() != null) {
				Assert.assertEquals(machine1.getZone().getId_zone(),
						machine2.getZone().getId_zone());

			}
			if (machine1.getLogTracas() != null
					&& machine2.getLogTracas() != null) {
				Assert.assertEquals(machine1.getLogTracas().size(),
					machine2.getLogTracas().size());
				for (int i=0;i<machine1.getLogTracas().size();i++){
					Assert.assertEquals(machine1.getLogTracas().get(i).getId_log(),
								machine2.getLogTracas().get(i).getId_log());
				}
			}
		}

		return ret;
	}
}

