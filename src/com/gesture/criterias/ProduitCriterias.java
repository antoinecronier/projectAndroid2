/**************************************************************************
 * ProduitCriterias.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.criterias;

import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.Criteria;

import com.gesture.entity.Produit;

/**
 * ProduitCriterias.
 *
 * This class can help you forge requests for your Produit Entity.
 * For more details, see CriteriasBase.
 */
public class ProduitCriterias extends CriteriasBase<Produit> {
	/** String to parcel produitCriteria. */
	public static final String PARCELABLE =
			"produitCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ProduitCriterias(final GroupType type) {
		super(type);
	}
}
