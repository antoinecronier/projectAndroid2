/**************************************************************************
 * UserCreateActivity.java, WindowsGesture2 Android
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

/** 
 * User create Activity.
 *
 * This only contains a UserCreateFragment.
 *
 * @see android.app.Activity
 */
public class UserCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
