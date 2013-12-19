/**************************************************************************
 * StringValue.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.criterias.base.value;

import java.util.ArrayList;

/**
 * StringValue for criteria.
 * A StringValue is a simple String used for criterias.
 */
public class StringValue extends CriteriaValue {
	/**
	 * List of values.
	 */
	private String value;

	/**
	 * Contructor.
	 * @param value The value of this string.
	 */
	public StringValue(String value) {
		super();
		this.value = value;
	}

	/**
	 * Set the value of this StringValue.
	 * @param value The new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Get this StringValue's value.
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	@Override
	public String toSQLiteString() {
		return this.value;
	}

	@Override
	public String toSQLiteSelection() {
		return "?";
	}

	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {
		array.add(this.value);
	}
}
