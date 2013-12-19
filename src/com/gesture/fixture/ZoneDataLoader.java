/**************************************************************************
 * ZoneDataLoader.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.fixture;

import java.util.Map;

import android.content.Context;

import com.gesture.entity.Zone;



/**
 * ZoneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ZoneDataLoader
						extends FixtureBase<Zone> {
	/** ZoneDataLoader name. */
	private static final String FILE_NAME = "Zone";

	/** Constant field for id_zone. */
	private static final String ID_ZONE = "id_zone";
	/** Constant field for nom. */
	private static final String NOM = "nom";


	/** ZoneDataLoader instance (Singleton). */
	private static ZoneDataLoader instance;

	/**
	 * Get the ZoneDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static ZoneDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ZoneDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ZoneDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Zone extractItem(final Map<?, ?> columns) {
		final Zone zone =
				new Zone();

		return this.extractItem(columns, zone);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param zone Entity to extract
	 * @return A Zone entity
	 */
	protected Zone extractItem(final Map<?, ?> columns,
				Zone zone) {

		if (columns.get(ID_ZONE) != null) {
			zone.setId_zone(
				(Integer) columns.get(ID_ZONE));
		}

		if (columns.get(NOM) != null) {
			zone.setNom(
				(String) columns.get(NOM));
		}


		return zone;
	}
	/**
	 * Loads Zones into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Zone zone : this.items.values()) {
			zone.setId_zone(
					manager.persist(zone));
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
