/**************************************************************************
 * MachineDataLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.gesture.entity.Machine;
import com.gesture.entity.Zone;
import com.gesture.entity.LogTraca;



/**
 * MachineDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class MachineDataLoader
						extends FixtureBase<Machine> {
	/** MachineDataLoader name. */
	private static final String FILE_NAME = "Machine";

	/** Constant field for id_machine. */
	private static final String ID_MACHINE = "id_machine";
	/** Constant field for nom. */
	private static final String NOM = "nom";
	/** Constant field for zone. */
	private static final String ZONE = "zone";
	/** Constant field for LogTracas. */
	private static final String LOGTRACAS = "logTracas";


	/** MachineDataLoader instance (Singleton). */
	private static MachineDataLoader instance;

	/**
	 * Get the MachineDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static MachineDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new MachineDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private MachineDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Machine extractItem(final Map<?, ?> columns) {
		final Machine machine =
				new Machine();

		return this.extractItem(columns, machine);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param machine Entity to extract
	 * @return A Machine entity
	 */
	protected Machine extractItem(final Map<?, ?> columns,
				Machine machine) {

		if (columns.get(ID_MACHINE) != null) {
			machine.setId_machine(
				(Integer) columns.get(ID_MACHINE));
		}

		if (columns.get(NOM) != null) {
			machine.setNom(
				(String) columns.get(NOM));
		}

		if (columns.get(ZONE) != null) {
			final Zone zone =
				ZoneDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(ZONE));
			if (zone != null) {
				machine.setZone(zone);
			}
		}

		if (columns.get(LOGTRACAS) != null) {
			ArrayList<LogTraca> logTracas =
				new ArrayList<LogTraca>();
			final Map<?, ?> logTracasMap =
				(Map<?, ?>) columns.get(LOGTRACAS);
			for (final Object logTracaName : logTracasMap.values()) {
				if (LogTracaDataLoader.getInstance(
					this.ctx).items.containsKey(
							(String) logTracaName)) {
				logTracas.add(
						LogTracaDataLoader.getInstance(
								this.ctx).items.get((String) logTracaName));
				}
			}
			machine.setLogTracas(logTracas);
		}


		return machine;
	}
	/**
	 * Loads Machines into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Machine machine : this.items.values()) {
			machine.setId_machine(
					manager.persist(machine));
		}
		manager.flush();
	}

	/**
	 * Give priority for fixtures insertion in database.
	 * 0 is the first.
	 * @return The order
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * Get the fixture file name.
	 * @return A String representing the file name
	 */
	@Override
	public String getFixtureFileName() {
		return FILE_NAME;
	}
}
