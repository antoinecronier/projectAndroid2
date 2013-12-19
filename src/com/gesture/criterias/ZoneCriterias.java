/**************************************************************************
 * ZoneCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.Zone;

/**
 * ZoneCriterias.
 *
 * This class can help you forge requests for your Zone Entity.
 * For more details, see CriteriasBase.
 */
public class ZoneCriterias extends CriteriasBase<Zone> {
	/** String to parcel zoneCriteria. */
	public static final String PARCELABLE =
			"zoneCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ZoneCriterias(final GroupType type) {
		super(type);
	}
}
