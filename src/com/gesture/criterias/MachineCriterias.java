/**************************************************************************
 * MachineCriterias.java, WindowsGesture2 Android
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

import com.gesture.entity.Machine;

/**
 * MachineCriterias.
 *
 * This class can help you forge requests for your Machine Entity.
 * For more details, see CriteriasBase.
 */
public class MachineCriterias extends CriteriasBase<Machine> {
	/** String to parcel machineCriteria. */
	public static final String PARCELABLE =
			"machineCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public MachineCriterias(final GroupType type) {
		super(type);
	}
}
