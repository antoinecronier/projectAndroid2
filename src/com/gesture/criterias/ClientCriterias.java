/**************************************************************************
 * ClientCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.Client;

/**
 * ClientCriterias.
 *
 * This class can help you forge requests for your Client Entity.
 * For more details, see CriteriasBase.
 */
public class ClientCriterias extends CriteriasBase<Client> {
	/** String to parcel clientCriteria. */
	public static final String PARCELABLE =
			"clientCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ClientCriterias(final GroupType type) {
		super(type);
	}
}
