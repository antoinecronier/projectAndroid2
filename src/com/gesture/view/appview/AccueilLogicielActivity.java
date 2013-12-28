package com.gesture.view.appview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gesture.R;
import com.gesture.entity.User;
import com.gesture.view.appcode.Constantes;
import com.gesture.view.commande.CommandeCreateActivity;
import com.gesture.view.logtraca.LogTracaListActivity;
import com.gesture.view.machine.MachineCreateActivity;
import com.gesture.view.user.UserCreateActivity;
import com.gesture.view.zone.ZoneCreateActivity;

/**
 * Menu d'accueil del'admin logiciel.<br>
 * Redirige vers les différents écrans de gestion du parc
 * @author Nanis
 */

public class AccueilLogicielActivity extends Activity implements OnClickListener{

	Context monContext;
	User userForInstance;
	
	private Button btn_ajout_user;
	private Button btn_ajout_zone;
	private Button btn_ajout_machine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_accueil_logiciel);
		
		monContext = (Context) this;
		
		Bundle monBundle;
		monBundle = this.getIntent().getExtras();

		userForInstance = (User) monBundle.get("CurrentUser");
		
		// récupération des boutons
		btn_ajout_user = (Button) findViewById(R.id.btn_ajout_user);
		btn_ajout_zone = (Button) findViewById(R.id.btn_ajout_zone);
		btn_ajout_machine = (Button) findViewById(R.id.btn_ajout_machine);
		
		//set du onclicklistener sur les boutons
		btn_ajout_user.setOnClickListener(this);
		btn_ajout_zone.setOnClickListener(this);
		btn_ajout_machine.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void onClick(View view) {
		//Redirection selon le choix de l'user
		if (view == btn_ajout_user) {
			Intent intent = new Intent(AccueilLogicielActivity.this,
					UserCreateActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilLogicielActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btn_ajout_zone) {
			Intent intent = new Intent(AccueilLogicielActivity.this,
					ZoneCreateActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilLogicielActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btn_ajout_machine) {
			Intent intent = new Intent(AccueilLogicielActivity.this,
					MachineCreateActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilLogicielActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
	}

}
