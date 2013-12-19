/**************************************************************************
 * LogTracaCreateActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.logtraca;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * LogTraca create Activity.
 *
 * This only contains a LogTracaCreateFragment.
 *
 * @see android.app.Activity
 */
public class LogTracaCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_logtraca_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
