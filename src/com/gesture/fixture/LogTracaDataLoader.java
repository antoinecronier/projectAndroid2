/**************************************************************************
 * LogTracaDataLoader.java, WindowsGesture2 Android
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
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import android.content.Context;

import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;
import com.gesture.entity.Machine;
import com.gesture.entity.User;

import com.gesture.harmony.util.DateUtils;

/**
 * LogTracaDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class LogTracaDataLoader
						extends FixtureBase<LogTraca> {
	/** LogTracaDataLoader name. */
	private static final String FILE_NAME = "LogTraca";

	/** Constant field for id_log. */
	private static final String ID_LOG = "id_log";
	/** Constant field for produit. */
	private static final String PRODUIT = "produit";
	/** Constant field for machine. */
	private static final String MACHINE = "machine";
	/** Constant field for user. */
	private static final String USER = "user";
	/** Constant field for duree. */
	private static final String DUREE = "duree";
	/** Constant field for dateEntre. */
	private static final String DATEENTRE = "dateEntre";
	/** Constant field for dateSortie. */
	private static final String DATESORTIE = "dateSortie";


	/** LogTracaDataLoader instance (Singleton). */
	private static LogTracaDataLoader instance;

	/**
	 * Get the LogTracaDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static LogTracaDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new LogTracaDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private LogTracaDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected LogTraca extractItem(final Map<?, ?> columns) {
		final LogTraca logTraca =
				new LogTraca();

		return this.extractItem(columns, logTraca);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param logTraca Entity to extract
	 * @return A LogTraca entity
	 */
	protected LogTraca extractItem(final Map<?, ?> columns,
				LogTraca logTraca) {

		if (columns.get(ID_LOG) != null) {
			logTraca.setId_log(
				(Integer) columns.get(ID_LOG));
		}

		if (columns.get(PRODUIT) != null) {
			final Produit produit =
				ProduitDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(PRODUIT));
			if (produit != null) {
				logTraca.setProduit(produit);
			}
		}

		if (columns.get(MACHINE) != null) {
			final Machine machine =
				MachineDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(MACHINE));
			if (machine != null) {
				logTraca.setMachine(machine);
			}
		}

		if (columns.get(USER) != null) {
			final User user =
				UserDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(USER));
			if (user != null) {
				logTraca.setUser(user);
			}
		}

		if (columns.get(DUREE) != null) {
			logTraca.setDuree(
				(String) columns.get(DUREE));
		}

		if (columns.get(DATEENTRE) != null) {
			logTraca.setDateEntre(
				(String) columns.get(DATEENTRE));
		}

		if (columns.get(DATESORTIE) != null) {
			logTraca.setDateSortie(
				(String) columns.get(DATESORTIE));
		}


		return logTraca;
	}
	/**
	 * Loads LogTracas into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final LogTraca logTraca : this.items.values()) {
			logTraca.setId_log(
					manager.persist(logTraca));
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
