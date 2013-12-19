/**************************************************************************
 * MachineProviderUtilsBase.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.gesture.criterias.MachineCriterias;
import com.gesture.criterias.ZoneCriterias;
import com.gesture.criterias.LogTracaCriterias;
import com.gesture.criterias.base.Criteria;
import com.gesture.criterias.base.Criteria.Type;
import com.gesture.criterias.base.value.ArrayValue;
import com.gesture.criterias.base.CriteriasBase;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.MachineSQLiteAdapter;
import com.gesture.data.ZoneSQLiteAdapter;
import com.gesture.data.LogTracaSQLiteAdapter;

import com.gesture.entity.Machine;
import com.gesture.entity.Zone;
import com.gesture.entity.LogTraca;

import com.gesture.provider.MachineProviderAdapter;
import com.gesture.provider.ZoneProviderAdapter;
import com.gesture.provider.LogTracaProviderAdapter;
import com.gesture.provider.WindowsGesture2Provider;

/**
 * Machine Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class MachineProviderUtilsBase
			extends ProviderUtilsBase<Machine> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "MachineProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public MachineProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Machine item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		MachineSQLiteAdapter adapt =
				new MachineSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(MachineSQLiteAdapter.COL_ID_MACHINE);

		operations.add(ContentProviderOperation.newInsert(
				MachineProviderAdapter.MACHINE_URI)
						.withValues(itemValues)
						.build());

		if (item.getLogTracas() != null && item.getLogTracas().size() > 0) {
			String LogTracasSelection = LogTracaSQLiteAdapter.COL_ID_LOG + " IN (";
			String[] LogTracasSelectionArgs = new String[item.getLogTracas().size()];
			for (int i = 0; i < item.getLogTracas().size(); i++) {
				LogTracasSelectionArgs[i] = String.valueOf(item.getLogTracas().get(i).getId_log());
				LogTracasSelection += "? ";
				if (i != item.getLogTracas().size() - 1) {
					 LogTracasSelection += ", ";
				}
			}
			LogTracasSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(LogTracaProviderAdapter.LOGTRACA_URI)
					.withValueBackReference(
							LogTracaSQLiteAdapter
									.COL_MACHINELOGTRACASINTERNAL,
							0)
					.withSelection(LogTracasSelection, LogTracasSelectionArgs)
					.build());
		}

		try {
			ContentProviderResult[] results = 
					prov.applyBatch(WindowsGesture2Provider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}


	/**
	 * Delete from DB.
	 * @param item Machine
	 * @return number of row affected
	 */
	public int delete(final Machine item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				MachineProviderAdapter.MACHINE_URI,
				String.valueOf(item.getId_machine()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Machine
	 */
	public Machine query(final int id) {
		Machine result = null;
		MachineSQLiteAdapter adapt =
					new MachineSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		MachineCriterias crits =
				new MachineCriterias(GroupType.AND);
		crits.add(MachineSQLiteAdapter.ALIASED_COL_ID_MACHINE,
					String.valueOf(id));

		Cursor cursor = prov.query(
			MachineProviderAdapter.MACHINE_URI,
			MachineSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

			if (result.getZone() != null) {
				result.setZone(
					this.getAssociateZone(result));
			}
			result.setLogTracas(
				this.getAssociateLogTracas(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<Machine>
	 */
	public ArrayList<Machine> queryAll() {
		ArrayList<Machine> result =
					new ArrayList<Machine>();
		MachineSQLiteAdapter adapt =
					new MachineSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				MachineProviderAdapter.MACHINE_URI,
				MachineSQLiteAdapter.ALIASED_COLS,
				null,
				null,
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<Machine>
	 */
	public ArrayList<Machine> query(
				CriteriasBase<Machine> criteria) {
		ArrayList<Machine> result =
					new ArrayList<Machine>();
		MachineSQLiteAdapter adapt =
					new MachineSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				MachineProviderAdapter.MACHINE_URI,
				MachineSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Machine
	 
	 * @return number of rows updated
	 */
	public int update(final Machine item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		MachineSQLiteAdapter adapt =
				new MachineSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				MachineProviderAdapter.MACHINE_URI,
				String.valueOf(item.getId_machine()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getLogTracas() != null && item.getLogTracas().size() > 0) {
			// Set new LogTracas for Machine
			LogTracaCriterias LogTracasCrit =
						new LogTracaCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(LogTracaSQLiteAdapter.COL_ID_LOG);
			crit.addValue(values);
			LogTracasCrit.add(crit);


			for (int i = 0; i < item.getLogTracas().size(); i++) {
				values.addValue(String.valueOf(
						item.getLogTracas().get(i).getId_log()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					LogTracaProviderAdapter.LOGTRACA_URI)
						.withValue(
								LogTracaSQLiteAdapter
										.COL_MACHINELOGTRACASINTERNAL,
								item.getId_machine())
					.withSelection(
							LogTracasCrit.toSQLiteSelection(),
							LogTracasCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated LogTracas
			crit.setType(Type.NOT_IN);
			LogTracasCrit.add(LogTracaSQLiteAdapter.COL_MACHINELOGTRACASINTERNAL,
					String.valueOf(item.getId_machine()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					LogTracaProviderAdapter.LOGTRACA_URI)
						.withValue(
								LogTracaSQLiteAdapter
										.COL_MACHINELOGTRACASINTERNAL,
								null)
					.withSelection(
							LogTracasCrit.toSQLiteSelection(),
							LogTracasCrit.toSQLiteSelectionArgs())
					.build());
		}


		try {
			ContentProviderResult[] results = prov.applyBatch(WindowsGesture2Provider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/** Relations operations. */
	/**
	 * Get associate Zone.
	 * @param item Machine
	 * @return Zone
	 */
	public Zone getAssociateZone(
			final Machine item) {
		Zone result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor zoneCursor = prov.query(
				ZoneProviderAdapter.ZONE_URI,
				ZoneSQLiteAdapter.ALIASED_COLS,
				ZoneSQLiteAdapter.COL_ID_ZONE + "= ?",
				new String[]{String.valueOf(item.getZone().getId_zone())},
				null);

		if (zoneCursor.getCount() > 0) {
			zoneCursor.moveToFirst();
			ZoneSQLiteAdapter zoneAdapt =
					new ZoneSQLiteAdapter(this.getContext());
			result = zoneAdapt.cursorToItem(zoneCursor);
		} else {
			result = null;
		}
		zoneCursor.close();

		return result;
	}

	/**
	 * Get associate LogTracas.
	 * @param item Machine
	 * @return LogTraca
	 */
	public ArrayList<LogTraca> getAssociateLogTracas(
			final Machine item) {
		ArrayList<LogTraca> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor logTracaCursor = prov.query(
				LogTracaProviderAdapter.LOGTRACA_URI,
				LogTracaSQLiteAdapter.ALIASED_COLS,
				LogTracaSQLiteAdapter.COL_MACHINELOGTRACASINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId_machine())},
				null);

		LogTracaSQLiteAdapter logTracaAdapt =
				new LogTracaSQLiteAdapter(this.getContext());
		result = logTracaAdapt.cursorToItems(
						logTracaCursor);
		logTracaCursor.close();

		return result;
	}

}
