package com.gesture.view.appview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.gesture.R;
import com.gesture.entity.User;
import com.gesture.view.appcode.Constantes;

public class AccueilUserActivity extends Activity {

	Context monContext;
	User userForInstance;

	/*
	 * Intent monIntent = new Intent(ChoixZoneActivity.this,
	 * LoginActivity.class); monIntent.putExtra("CurrentUser", userForInstance);
	 * ChoixZoneActivity.this.startActivityForResult(monIntent, 21);
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_accueil_user);

		monContext = (Context) this;

		Bundle monBundle;
		monBundle = this.getIntent().getExtras();

		userForInstance = (User) monBundle.get("CurrentUser");

		ImageButton buttonConnexion = (ImageButton) this
				.findViewById(R.id.imageButton1);
		buttonConnexion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(AccueilUserActivity.this);
				Editor edit = prefs.edit();
				edit.putInt("LastCurrentUser", userForInstance.getId_user());
				edit.putString("LastCurrentScreen",
						AccueilUserActivity.class.toString());
				AccueilUserActivity.this
						.finishActivity(Constantes.ACCUEIL_USER_ACTIVITY);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
