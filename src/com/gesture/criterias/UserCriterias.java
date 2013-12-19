/**************************************************************************
 * UserCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.User;

/**
 * UserCriterias.
 *
 * This class can help you forge requests for your User Entity.
 * For more details, see CriteriasBase.
 */
public class UserCriterias extends CriteriasBase<User> {
	/** String to parcel userCriteria. */
	public static final String PARCELABLE =
			"userCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public UserCriterias(final GroupType type) {
		super(type);
	}
}
