/**************************************************************************
 * ProduitUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Produit;
import com.gesture.test.utils.*;



import com.gesture.entity.Commande;
import com.gesture.fixture.CommandeDataLoader;
import com.gesture.entity.Produit.ProduitType;
import com.gesture.entity.Produit.ProduitEtat;
import com.gesture.entity.Produit.ProduitMateriel;
import java.util.ArrayList;

public abstract class ProduitUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Produit generateRandom(Context ctx){
		Produit produit = new Produit();

		produit.setId_produit(TestUtils.generateRandomInt(0,100) + 1);
		produit.setType(ProduitType.values()[TestUtils.generateRandomInt(0,ProduitType.values().length)]);
		produit.setEtat(ProduitEtat.values()[TestUtils.generateRandomInt(0,ProduitEtat.values().length)]);
		produit.setMateriel(ProduitMateriel.values()[TestUtils.generateRandomInt(0,ProduitMateriel.values().length)]);
		ArrayList<Commande> commandes =
			new ArrayList<Commande>(CommandeDataLoader.getInstance(ctx).getMap().values());
		if (!commandes.isEmpty()) {
			produit.setCommande(commandes.get(TestUtils.generateRandomInt(0, commandes.size())));
		}
		produit.setQuantite(TestUtils.generateRandomInt(0,100));
		produit.setAvancement(TestUtils.generateRandomInt(0,100));

		return produit;
	}

	public static boolean equals(Produit produit1, Produit produit2){
		boolean ret = true;
		Assert.assertNotNull(produit1);
		Assert.assertNotNull(produit2);
		if (produit1!=null && produit2 !=null){
			Assert.assertEquals(produit1.getId_produit(), produit2.getId_produit());
			Assert.assertEquals(produit1.getType(), produit2.getType());
			Assert.assertEquals(produit1.getEtat(), produit2.getEtat());
			Assert.assertEquals(produit1.getMateriel(), produit2.getMateriel());
			if (produit1.getCommande() != null
					&& produit2.getCommande() != null) {
				Assert.assertEquals(produit1.getCommande().getId_cmd(),
						produit2.getCommande().getId_cmd());

			}
			Assert.assertEquals(produit1.getQuantite(), produit2.getQuantite());
			Assert.assertEquals(produit1.getAvancement(), produit2.getAvancement());
		}

		return ret;
	}
}

