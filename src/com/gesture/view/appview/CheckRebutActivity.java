package com.gesture.view.appview;

import com.gesture.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class CheckRebutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_check_rebut);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
