/**************************************************************************
 * UserEditActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.user;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** User edit Activity.
 *
 * This only contains a UserEditFragment.
 *
 * @see android.app.Activity
 */
public class UserEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
