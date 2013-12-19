/**************************************************************************
 * CommandeListActivity.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

import com.gesture.R;

import com.gesture.harmony.view.HarmonyFragmentActivity;
import com.gesture.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.util.ComponentUtils;
import com.gesture.entity.Commande;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This class will display Commande entities in a list.
 *
 * This activity has a different behaviour wether you're on a tablet or a phone.
 * - On a phone, you will have a Commande list. A click on an item will get 
 * you to CommandeShowActivity.
 * - On a tablet, the CommandeShowFragment will be displayed right next to
 * the list.
 */
public class CommandeListActivity 
		extends HarmonyFragmentActivity
		implements HarmonyListFragment.OnClickCallback,
				HarmonyListFragment.OnLoadCallback {

	/** Associated list fragment. */
	protected CommandeListFragment listFragment;
	/** Associated detail fragment if any (in case of tablet). */
	protected CommandeShowFragment detailFragment;
	/** Last selected item position in the list. */
	private int lastSelectedItemPosition = 0;
	/** Last selected item. */
	private Commande lastSelectedItem;
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		
		this.detailFragment = (CommandeShowFragment) 
						this.getSupportFragmentManager().findFragmentById(
								R.id.fragment_show);

		this.listFragment = (CommandeListFragment)
						this.getSupportFragmentManager().findFragmentById(
								R.id.fragment_list);
		
		if (this.isDualMode() && this.detailFragment != null) {
			this.listFragment.setRetainInstance(true);
			this.detailFragment.setRetainInstance(true);

			
	        ComponentUtils.configureVerticalScrollbar(
							this.listFragment.getListView(),
							View.SCROLLBAR_POSITION_LEFT);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_commande_list);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(
				int requestCode,
				int resultCode,
				Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode <= SUPPORT_V4_RESULT_HACK) {
			switch(requestCode) {
				default:
					break;
			}
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
			this.lastSelectedItemPosition = position;
		
		if (this.isDualMode()) {
			this.selectListItem(this.lastSelectedItemPosition);
		} else {
			final Intent intent = new Intent(this, CommandeShowActivity.class);
			final Commande item = (Commande) l.getItemAtPosition(position);
			Bundle extras = new Bundle();
			extras.putParcelable(Commande.PARCEL, item);
			intent.putExtras(extras);
			this.startActivity(intent);
		}
	}

	/** 
	 * Load the detail fragment of the given item.
	 * 
	 * @param item The item to load
	 */
	private void loadDetailFragment(Commande item) {
		this.detailFragment.update(item);
	}

	/**
	 * Select a list item.
	 *
	 * @param listPosition The position of the item.
	 */
	private void selectListItem(int listPosition) {
		int listSize = this.listFragment.getListAdapter().getCount();
		if (listSize > 0) {
			if (listPosition >= listSize) {
				listPosition = listSize - 1;
			} else if (listPosition < 0) {
				listPosition = 0;
			}
			this.listFragment.getListView().setItemChecked(listPosition, true);
			Commande item = (Commande) 
					this.listFragment.getListAdapter().getItem(listPosition);
			this.lastSelectedItem = item;
			this.loadDetailFragment(item);
		} else {
			this.loadDetailFragment(null);
		}
	}

	@Override
	public void onListLoaded() {
		if (this.isDualMode()) {
			int newPosition =
				((CommandeListAdapter) this.listFragment.getListAdapter())
						.getPosition(this.lastSelectedItem);
			if (newPosition < 0) {
				this.selectListItem(this.lastSelectedItemPosition);
			} else {				
				this.selectListItem(newPosition);
			}
		}
	}
}
