/**************************************************************************
 * HarmonyFragment.java, WindowsGesture2 Android
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
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gesture.menu.WindowsGesture2Menu;

/**
 * Harmony custom Fragment.
 * This fragment will help you use a lot of harmony's functionnality
 * (menu wrappers, etc.)
 */
public abstract class HarmonyFragment extends SherlockFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		try {
			WindowsGesture2Menu.getInstance(this.getActivity(), this)
											.clear(menu);
			WindowsGesture2Menu.getInstance(this.getActivity(), this)
										  .updateMenu(menu, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;

		try {
			result = WindowsGesture2Menu.getInstance(
				   this.getActivity(), this).dispatch(item, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			WindowsGesture2Menu.getInstance(this.getActivity(), this)
			.onActivityResult(requestCode, resultCode, data, this.getActivity(),
			this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
