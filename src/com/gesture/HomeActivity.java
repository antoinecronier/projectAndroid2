/**************************************************************************
 * HomeActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.view.user.UserListActivity;
import com.gesture.view.produit.ProduitListActivity;
import com.gesture.view.machine.MachineListActivity;
import com.gesture.view.zone.ZoneListActivity;
import com.gesture.view.commande.CommandeListActivity;
import com.gesture.view.logtraca.LogTracaListActivity;
import com.gesture.view.client.ClientListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
		implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		this.initButtons();
	}

	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		this.findViewById(R.id.user_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.produit_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.machine_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.zone_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.commande_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.logtraca_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.client_list_button)
						.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.user_list_button:
				intent = new Intent(this,
						UserListActivity.class);
				break;

			case R.id.produit_list_button:
				intent = new Intent(this,
						ProduitListActivity.class);
				break;

			case R.id.machine_list_button:
				intent = new Intent(this,
						MachineListActivity.class);
				break;

			case R.id.zone_list_button:
				intent = new Intent(this,
						ZoneListActivity.class);
				break;

			case R.id.commande_list_button:
				intent = new Intent(this,
						CommandeListActivity.class);
				break;

			case R.id.logtraca_list_button:
				intent = new Intent(this,
						LogTracaListActivity.class);
				break;

			case R.id.client_list_button:
				intent = new Intent(this,
						ClientListActivity.class);
				break;

			default:
				intent = null;
				break;
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
