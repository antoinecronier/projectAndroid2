/**************************************************************************
 * WindowsGesture2Menu.java, WindowsGesture2 Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.gesture.menu;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * WindowsGesture2Menu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class WindowsGesture2Menu
				extends WindowsGesture2MenuBase {

	/** Singleton unique instance. */
	private static volatile WindowsGesture2Menu singleton;

	/**
	 * Constructor.
	 * @param ctx The Context
	 * @throws Exception If something bad happened
	 */
	public WindowsGesture2Menu(final Context ctx) throws Exception {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @throws Exception If something bad happened
	 */
	public WindowsGesture2Menu(final Context ctx,
						final Fragment fragment) throws Exception {
		super(ctx, fragment);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @return WindowsGesture2Menu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized WindowsGesture2Menu getInstance(
						final Context ctx) throws Exception {
		return getInstance(ctx, null);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @return WindowsGesture2Menu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized WindowsGesture2Menu getInstance(
			final Context ctx, final Fragment fragment) throws Exception {
		if (singleton == null) {
			singleton = new WindowsGesture2Menu(ctx, fragment);
		}  else {
			singleton.ctx = ctx;
			singleton.fragment = fragment;
		}

		return singleton;
	}
}
