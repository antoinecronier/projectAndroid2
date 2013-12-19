/**************************************************************************
 * LogUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Log;
import com.gesture.test.utils.*;



import com.gesture.entity.Produit;
import com.gesture.fixture.ProduitDataLoader;
import com.gesture.entity.Machine;
import com.gesture.fixture.MachineDataLoader;
import com.gesture.entity.User;
import com.gesture.fixture.UserDataLoader;
import java.util.ArrayList;

public abstract class LogUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Log generateRandom(Context ctx){
		Log log = new Log();

		log.setId_log(TestUtils.generateRandomInt(0,100) + 1);
		ArrayList<Produit> produits =
			new ArrayList<Produit>(ProduitDataLoader.getInstance(ctx).getMap().values());
		if (!produits.isEmpty()) {
			log.setProduit(produits.get(TestUtils.generateRandomInt(0, produits.size())));
		}
		ArrayList<Machine> machines =
			new ArrayList<Machine>(MachineDataLoader.getInstance(ctx).getMap().values());
		if (!machines.isEmpty()) {
			log.setMachine(machines.get(TestUtils.generateRandomInt(0, machines.size())));
		}
		ArrayList<User> users =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!users.isEmpty()) {
			log.setUser(users.get(TestUtils.generateRandomInt(0, users.size())));
		}
		log.setDuree("duree_"+TestUtils.generateRandomString(10));
		log.setDateEntre("dateEntre_"+TestUtils.generateRandomString(10));
		log.setDateSortie("dateSortie_"+TestUtils.generateRandomString(10));

		return log;
	}

	public static boolean equals(Log log1, Log log2){
		boolean ret = true;
		Assert.assertNotNull(log1);
		Assert.assertNotNull(log2);
		if (log1!=null && log2 !=null){
			Assert.assertEquals(log1.getId_log(), log2.getId_log());
			if (log1.getProduit() != null
					&& log2.getProduit() != null) {
				Assert.assertEquals(log1.getProduit().getId_produit(),
						log2.getProduit().getId_produit());

			}
			if (log1.getMachine() != null
					&& log2.getMachine() != null) {
				Assert.assertEquals(log1.getMachine().getId_machine(),
						log2.getMachine().getId_machine());

			}
			if (log1.getUser() != null
					&& log2.getUser() != null) {
				Assert.assertEquals(log1.getUser().getId_user(),
						log2.getUser().getId_user());

			}
			Assert.assertEquals(log1.getDuree(), log2.getDuree());
			Assert.assertEquals(log1.getDateEntre(), log2.getDateEntre());
			Assert.assertEquals(log1.getDateSortie(), log2.getDateSortie());
		}

		return ret;
	}
}

