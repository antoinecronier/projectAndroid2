/**************************************************************************
 * CommandeListFragment.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.view.commande;

import java.util.ArrayList;

import com.gesture.criterias.CommandeCriterias;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.gesture.provider.CommandeProviderAdapter;
import com.gesture.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.gesture.R;
import com.gesture.entity.Commande;

/** Commande list fragment.
 *
 * This fragment gives you an interface to list all your Commandes.
 *
 * @see android.app.Fragment
 */
public class CommandeListFragment
		extends HarmonyListFragment<Commande>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected CommandeListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_commande_list,
						null);

		this.initializeHackCustomList(view,
				R.id.commandeProgressLayout,
				R.id.commandeListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.commande_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new CommandeListAdapter(this.getActivity());

		// Start out with a progress indicator.
		this.setListShown(false);

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/* Do click action inside your fragment here. */
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		Loader<Cursor> result = null;
		CommandeCriterias crit = null;
		if (bundle != null) {
			crit = (CommandeCriterias) bundle.get(
						CommandeCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new CommandeListLoader(this.getActivity(),
				CommandeProviderAdapter.COMMANDE_URI,
				CommandeSQLiteAdapter.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new CommandeListLoader(this.getActivity(),
				CommandeProviderAdapter.COMMANDE_URI,
				CommandeSQLiteAdapter.ALIASED_COLS,
				null,
				null,
				null);
		}
		return result;
	}

	@Override
	public void onLoadFinished(
			Loader<Cursor> loader,
			Cursor data) {

		// Set the new data in the adapter.
		data.setNotificationUri(this.getActivity().getContentResolver(),
				CommandeProviderAdapter.COMMANDE_URI);

		ArrayList<Commande> users = new CommandeSQLiteAdapter(
				this.getActivity()).cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new CommandeListAdapter
					.CommandeSectionIndexer(users));
		this.mAdapter.setNotifyOnChange(true);
		this.mAdapter.notifyDataSetChanged();
		this.mAdapter.setPinnedPartitionHeadersEnabled(false);
		this.mAdapter.setSectionHeaderDisplayEnabled(false);

		if (this.getListAdapter() == null) {
			this.setListAdapter(this.mAdapter);
		}

		// The list should now be shown.
		if (this.isResumed()) {
			this.setListShown(true);
		} else {
			this.setListShownNoAnimation(true);
		}

		super.onLoadFinished(loader, data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Clear the data in the adapter.
		this.mAdapter.clear();
	}

	@Override
	public void onClickAdd() {
		Intent intent = new Intent(this.getActivity(),
					CommandeCreateActivity.class);
		this.startActivity(intent);
	}

}
