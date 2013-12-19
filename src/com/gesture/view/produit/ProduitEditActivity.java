/**************************************************************************
 * ProduitEditActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.produit;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Produit edit Activity.
 *
 * This only contains a ProduitEditFragment.
 *
 * @see android.app.Activity
 */
public class ProduitEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_produit_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
