package com.gesture.view.appview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

import com.gesture.R;
import com.gesture.entity.User;
import com.gesture.entity.Zone;
import com.gesture.view.appcode.Constantes;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

/**
 * Interface utilisateur pour la sélection de la zone de travail
 * 
 * @author alexandre
 * 
 */
public class AccueilUserActivity extends Activity {

	Context monContext;
	User userForInstance;
	PinnedHeaderListView listView;

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
						.getDefaultSharedPreferences(monContext);
				Editor edit = prefs.edit();
				edit.putInt("LastCurrentUser", userForInstance.getId_user());
				edit.putString("LastCurrentScreen",
						AccueilUserActivity.class.toString());
				AccueilUserActivity.this
						.finishActivity(Constantes.ACCUEIL_USER_ACTIVITY);
			}
		});

		listView = (PinnedHeaderListView) this
				.findViewById(R.id.zoneListContainer);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Need to launch selecction of machine
				Zone selectedZone = (Zone) listView.getItemAtPosition(arg2);
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(monContext);
				Editor edit = prefs.edit();
				edit.putInt("LastCurrentZone", selectedZone.getId_zone());
				edit.putString("LastCurrentScreen",
						AccueilUserActivity.class.toString());
				Intent monIntent = new Intent(AccueilUserActivity.this,
						GestionPieceActivity.class);
				monIntent.putExtra("CurrentZone", (Parcelable) selectedZone);
				AccueilUserActivity.this.startActivityForResult(monIntent,
						Constantes.ACCUEIL_USER_ACTIVITY);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
