/**************************************************************************
 * ZoneShowActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.zone;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.view.zone.ZoneShowFragment.DeleteCallback;

import android.os.Bundle;

/** Zone show Activity.
 *
 * This only contains a ZoneShowFragment.
 *
 * @see android.app.Activity
 */
public class ZoneShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_zone_show);
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
