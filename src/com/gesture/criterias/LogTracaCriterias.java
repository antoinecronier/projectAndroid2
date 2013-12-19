/**************************************************************************
 * LogTracaCriterias.java, WindowsGesture2 Android
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
 * LogTracaCriterias.
 *
 * This class can help you forge requests for your LogTraca Entity.
 * For more details, see CriteriasBase.
 */
public class LogTracaCriterias extends CriteriasBase<LogTraca> {
	/** String to parcel logTracaCriteria. */
	public static final String PARCELABLE =
			"logTracaCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public LogTracaCriterias(final GroupType type) {
		super(type);
	}
}
