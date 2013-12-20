package com.gesture.view.appview;

import com.gesture.R;
import com.gesture.R.layout;
import com.gesture.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChoixMachineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choix_machine);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choix_machine, menu);
		return true;
	}

}
