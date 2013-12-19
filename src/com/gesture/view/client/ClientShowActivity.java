/**************************************************************************
 * ClientShowActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.client;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.view.client.ClientShowFragment.DeleteCallback;

import android.os.Bundle;

/** Client show Activity.
 *
 * This only contains a ClientShowFragment.
 *
 * @see android.app.Activity
 */
public class ClientShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_client_show);
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
