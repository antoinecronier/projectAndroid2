/**************************************************************************
 * MachineEditActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.machine;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Machine edit Activity.
 *
 * This only contains a MachineEditFragment.
 *
 * @see android.app.Activity
 */
public class MachineEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_machine_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
