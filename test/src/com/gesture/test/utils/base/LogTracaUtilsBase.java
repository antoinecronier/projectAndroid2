/**************************************************************************
 * LogTracaUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.LogTraca;
import com.gesture.test.utils.*;



import com.gesture.entity.Produit;
import com.gesture.fixture.ProduitDataLoader;
import com.gesture.entity.Machine;
import com.gesture.fixture.MachineDataLoader;
import com.gesture.entity.User;
import com.gesture.fixture.UserDataLoader;
import java.util.ArrayList;

public abstract class LogTracaUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static LogTraca generateRandom(Context ctx){
		LogTraca logTraca = new LogTraca();

		logTraca.setId_log(TestUtils.generateRandomInt(0,100) + 1);
		ArrayList<Produit> produits =
			new ArrayList<Produit>(ProduitDataLoader.getInstance(ctx).getMap().values());
		if (!produits.isEmpty()) {
			logTraca.setProduit(produits.get(TestUtils.generateRandomInt(0, produits.size())));
		}
		ArrayList<Machine> machines =
			new ArrayList<Machine>(MachineDataLoader.getInstance(ctx).getMap().values());
		if (!machines.isEmpty()) {
			logTraca.setMachine(machines.get(TestUtils.generateRandomInt(0, machines.size())));
		}
		ArrayList<User> users =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!users.isEmpty()) {
			logTraca.setUser(users.get(TestUtils.generateRandomInt(0, users.size())));
		}
		logTraca.setDuree("duree_"+TestUtils.generateRandomString(10));
		logTraca.setDateEntre("dateEntre_"+TestUtils.generateRandomString(10));
		logTraca.setDateSortie("dateSortie_"+TestUtils.generateRandomString(10));

		return logTraca;
	}

	public static boolean equals(LogTraca logTraca1, LogTraca logTraca2){
		boolean ret = true;
		Assert.assertNotNull(logTraca1);
		Assert.assertNotNull(logTraca2);
		if (logTraca1!=null && logTraca2 !=null){
			Assert.assertEquals(logTraca1.getId_log(), logTraca2.getId_log());
			if (logTraca1.getProduit() != null
					&& logTraca2.getProduit() != null) {
				Assert.assertEquals(logTraca1.getProduit().getId_produit(),
						logTraca2.getProduit().getId_produit());

			}
			if (logTraca1.getMachine() != null
					&& logTraca2.getMachine() != null) {
				Assert.assertEquals(logTraca1.getMachine().getId_machine(),
						logTraca2.getMachine().getId_machine());

			}
			if (logTraca1.getUser() != null
					&& logTraca2.getUser() != null) {
				Assert.assertEquals(logTraca1.getUser().getId_user(),
						logTraca2.getUser().getId_user());

			}
			Assert.assertEquals(logTraca1.getDuree(), logTraca2.getDuree());
			Assert.assertEquals(logTraca1.getDateEntre(), logTraca2.getDateEntre());
			Assert.assertEquals(logTraca1.getDateSortie(), logTraca2.getDateSortie());
		}

		return ret;
	}
}

