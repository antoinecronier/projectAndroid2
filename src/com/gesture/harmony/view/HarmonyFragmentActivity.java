/**************************************************************************
 * HarmonyFragmentActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gesture.WindowsGesture2Application;
import com.gesture.WindowsGesture2ApplicationBase.DeviceType;
import com.gesture.menu.WindowsGesture2Menu;

/**
 * Custom FragmentActivity for harmony projects.
 * This fragment activity helps you use the menu wrappers, detect alone if
 * you're in tablet/dual mode.
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {
	/** Hack number for support v4 onActivityResult. */
	protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean result = true;

		try {
			WindowsGesture2Menu.getInstance(this).clear(menu);
			WindowsGesture2Menu.getInstance(this).updateMenu(menu,
																		  this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		if (result) {
			result = super.onPrepareOptionsMenu(menu);
		}

		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = WindowsGesture2Menu.getInstance(this).dispatch(
																	item, this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
																  Intent data) {
		try {
			WindowsGesture2Menu.getInstance(this).onActivityResult(
										   requestCode, resultCode, data, this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Is this device in tablet mode ?
	 *
	 * @return true if tablet mode
	 */
	public boolean isDualMode() {
		return WindowsGesture2Application.getDeviceType(this).equals(DeviceType.TABLET);
	}
}
