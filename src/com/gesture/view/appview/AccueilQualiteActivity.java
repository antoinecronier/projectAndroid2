package com.gesture.view.appview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.gesture.R;

public class AccueilQualiteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_accueil_qualite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}