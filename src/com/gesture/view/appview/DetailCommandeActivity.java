package com.gesture.view.appview;

import com.gesture.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class DetailCommandeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_detail_commande);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
