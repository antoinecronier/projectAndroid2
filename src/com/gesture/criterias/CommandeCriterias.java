/**************************************************************************
 * CommandeCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.Commande;

/**
 * CommandeCriterias.
 *
 * This class can help you forge requests for your Commande Entity.
 * For more details, see CriteriasBase.
 */
public class CommandeCriterias extends CriteriasBase<Commande> {
	/** String to parcel commandeCriteria. */
	public static final String PARCELABLE =
			"commandeCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public CommandeCriterias(final GroupType type) {
		super(type);
	}
}
