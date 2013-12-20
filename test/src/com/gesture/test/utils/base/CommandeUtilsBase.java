/**************************************************************************
 * CommandeUtilsBase.java, WindowsGesture2 Android
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
import com.gesture.entity.Commande;
import com.gesture.test.utils.*;



import com.gesture.entity.Client;
import com.gesture.fixture.ClientDataLoader;
import com.gesture.entity.Produit;
import com.gesture.fixture.ProduitDataLoader;
import java.util.ArrayList;

public abstract class CommandeUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Commande generateRandom(Context ctx){
		Commande commande = new Commande();

		commande.setId_cmd(TestUtils.generateRandomInt(0,100) + 1);
		ArrayList<Client> clients =
			new ArrayList<Client>(ClientDataLoader.getInstance(ctx).getMap().values());
		if (!clients.isEmpty()) {
			commande.setClient(clients.get(TestUtils.generateRandomInt(0, clients.size())));
		}
		commande.setDateCreation("dateCreation_"+TestUtils.generateRandomString(10));
		commande.setDateFin("dateFin_"+TestUtils.generateRandomString(10));
		commande.setDateLivraison("dateLivraison_"+TestUtils.generateRandomString(10));
		commande.setAvancement(TestUtils.generateRandomInt(0,100));
		ArrayList<Produit> produitss =
			new ArrayList<Produit>(ProduitDataLoader.getInstance(ctx).getMap().values());
		ArrayList<Produit> relatedProduitss = new ArrayList<Produit>();
		if (!produitss.isEmpty()) {
			relatedProduitss.add(produitss.get(TestUtils.generateRandomInt(0, produitss.size())));
			commande.setProduits(relatedProduitss);
		}

		return commande;
	}

	public static boolean equals(Commande commande1, Commande commande2){
		boolean ret = true;
		Assert.assertNotNull(commande1);
		Assert.assertNotNull(commande2);
		if (commande1!=null && commande2 !=null){
			Assert.assertEquals(commande1.getId_cmd(), commande2.getId_cmd());
			if (commande1.getClient() != null
					&& commande2.getClient() != null) {
				Assert.assertEquals(commande1.getClient().getId_client(),
						commande2.getClient().getId_client());

			}
			Assert.assertEquals(commande1.getDateCreation(), commande2.getDateCreation());
			Assert.assertEquals(commande1.getDateFin(), commande2.getDateFin());
			Assert.assertEquals(commande1.getDateLivraison(), commande2.getDateLivraison());
			Assert.assertEquals(commande1.getAvancement(), commande2.getAvancement());
			if (commande1.getProduits() != null
					&& commande2.getProduits() != null) {
				Assert.assertEquals(commande1.getProduits().size(),
					commande2.getProduits().size());
				for (int i=0;i<commande1.getProduits().size();i++){
					Assert.assertEquals(commande1.getProduits().get(i).getId_produit(),
								commande2.getProduits().get(i).getId_produit());
				}
			}
		}

		return ret;
	}
}

