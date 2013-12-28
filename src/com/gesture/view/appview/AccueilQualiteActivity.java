package com.gesture.view.appview;

import android.app.Activity;
import android.content.Intent;

/**
 * Ecran d'accueil de l'admin qualité.<br>
 * Dispatche l'user vers les différentes fonctions inhérentes au poste.
 * @author Nanis
 */
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;

import com.gesture.R;
import com.gesture.entity.User;
import com.gesture.view.appcode.Constantes;
import com.gesture.view.client.ClientCreateActivity;
import com.gesture.view.commande.CommandeCreateActivity;
import com.gesture.view.commande.CommandeListActivity;
import com.gesture.view.logtraca.LogTracaListActivity;
import com.gesture.view.zone.ZoneListActivity;

/**
 * Menu d'accueil de l'admin qualité.<br>
 * Redirige vers les différents écrans de gestion traça et commande.
 * @author Nanis
 *
 */

public class AccueilQualiteActivity extends Activity implements OnClickListener{

	private Button btnCreateCmd;
	private Button btnCheckRebut;
	private Button btnTraca;
	private Button btnListeCmd;
	private Button btnCmdEncours;
	private Button btnAddClient;
	private ImageButton btnDeco;
	private User userForInstance = new User();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.app_accueil_qualite);

		// Récupération user
		Bundle monBundle;
		monBundle = this.getIntent().getExtras();

		userForInstance = (User) monBundle.get("CurrentUser");

		// récupération des boutons
		btnCreateCmd = (Button) findViewById(R.id.btn_create_cmd);
		btnCheckRebut = (Button) findViewById(R.id.btn_check_qualite);
		btnTraca = (Button) findViewById(R.id.btn_traca);
		btnListeCmd = (Button) findViewById(R.id.btn_liste_cmd);
		btnCmdEncours = (Button) findViewById(R.id.btn_cmd_encours);
		btnAddClient = (Button) findViewById(R.id.btn_add_client);
		btnDeco = (ImageButton) findViewById(R.id.imageButton1);
		
		//set du onclicklistener sur les boutons
		btnCreateCmd.setOnClickListener(this);
		btnCheckRebut.setOnClickListener(this);
		btnTraca.setOnClickListener(this);
		btnListeCmd.setOnClickListener(this);
		btnCmdEncours.setOnClickListener(this);
		btnAddClient.setOnClickListener(this);
		btnDeco.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	// Listen events
	public void onClick(View view) {
		//Redirection selon le choix de l'user
		if (view == btnCreateCmd) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					CommandeCreateActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnCheckRebut) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					CheckRebutActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnTraca) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					LogTracaListActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnListeCmd) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					CommandeListActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnCmdEncours) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					CommandeListActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnAddClient) {
			Intent intent = new Intent(AccueilQualiteActivity.this,
					ClientCreateActivity.class);
			intent.putExtra("CurrentUser", (Parcelable) userForInstance);
			AccueilQualiteActivity.this.startActivityForResult(intent,
					Constantes.LOGIN_ACTIVITY);
		}
		if (view == btnDeco) {
			this.finishActivity(Constantes.LOGIN_ACTIVITY);
		}
	}
}
