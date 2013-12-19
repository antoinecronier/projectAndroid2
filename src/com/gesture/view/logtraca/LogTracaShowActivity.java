/**************************************************************************
 * LogTracaShowActivity.java, WindowsGesture2 Android
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
import com.gesture.view.logtraca.LogTracaShowFragment.DeleteCallback;

import android.os.Bundle;

/** LogTraca show Activity.
 *
 * This only contains a LogTracaShowFragment.
 *
 * @see android.app.Activity
 */
public class LogTracaShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_logtraca_show);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemDeleted() {
		this.finish();
	}
}
