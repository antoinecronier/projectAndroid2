/**************************************************************************
 * LogCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.LogTraca;

/**
 * LogCriterias.
 *
 * This class can help you forge requests for your Log Entity.
 * For more details, see CriteriasBase.
 */
public class LogCriterias extends CriteriasBase<LogTraca> {
	/** String to parcel logCriteria. */
	public static final String PARCELABLE =
			"logCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public LogCriterias(final GroupType type) {
		super(type);
	}
}
