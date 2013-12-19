/**************************************************************************
 * UserDataLoader.java, WindowsGesture2 Android
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

import com.gesture.entity.User;



/**
 * UserDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class UserDataLoader
						extends FixtureBase<User> {
	/** UserDataLoader name. */
	private static final String FILE_NAME = "User";

	/** Constant field for id_user. */
	private static final String ID_USER = "id_user";
	/** Constant field for login. */
	private static final String LOGIN = "login";
	/** Constant field for password. */
	private static final String PASSWORD = "password";
	/** Constant field for role. */
	private static final String ROLE = "role";


	/** UserDataLoader instance (Singleton). */
	private static UserDataLoader instance;

	/**
	 * Get the UserDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static UserDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new UserDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private UserDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected User extractItem(final Map<?, ?> columns) {
		final User user =
				new User();

		return this.extractItem(columns, user);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param user Entity to extract
	 * @return A User entity
	 */
	protected User extractItem(final Map<?, ?> columns,
				User user) {

		if (columns.get(ID_USER) != null) {
			user.setId_user(
				(Integer) columns.get(ID_USER));
		}

		if (columns.get(LOGIN) != null) {
			user.setLogin(
				(String) columns.get(LOGIN));
		}

		if (columns.get(PASSWORD) != null) {
			user.setPassword(
				(String) columns.get(PASSWORD));
		}

		if (columns.get(ROLE) != null) {
			user.setRole(
				(Integer) columns.get(ROLE));
		}


		return user;
	}
	/**
	 * Loads Users into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final User user : this.items.values()) {
			user.setId_user(
					manager.persist(user));
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
