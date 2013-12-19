/**************************************************************************
 * ClientDataLoader.java, WindowsGesture2 Android
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

import com.gesture.entity.Client;



/**
 * ClientDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ClientDataLoader
						extends FixtureBase<Client> {
	/** ClientDataLoader name. */
	private static final String FILE_NAME = "Client";

	/** Constant field for id_client. */
	private static final String ID_CLIENT = "id_client";
	/** Constant field for nom. */
	private static final String NOM = "nom";


	/** ClientDataLoader instance (Singleton). */
	private static ClientDataLoader instance;

	/**
	 * Get the ClientDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static ClientDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ClientDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ClientDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Client extractItem(final Map<?, ?> columns) {
		final Client client =
				new Client();

		return this.extractItem(columns, client);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param client Entity to extract
	 * @return A Client entity
	 */
	protected Client extractItem(final Map<?, ?> columns,
				Client client) {

		if (columns.get(ID_CLIENT) != null) {
			client.setId_client(
				(Integer) columns.get(ID_CLIENT));
		}

		if (columns.get(NOM) != null) {
			client.setNom(
				(String) columns.get(NOM));
		}


		return client;
	}
	/**
	 * Loads Clients into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Client client : this.items.values()) {
			client.setId_client(
					manager.persist(client));
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
