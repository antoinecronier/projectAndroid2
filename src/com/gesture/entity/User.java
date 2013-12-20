package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Définit un utilisateur de l'application
 * 
 * @author alexandre
 * 
 */
@Entity
public class User implements Serializable, Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"User";


	/**
	 * Serializable UID
	 */
	private static final long serialVersionUID = -3185036184140455157L;
	
	/* Members */
	@Id
	@Column(hidden=true)
	private int id_user;
	
	@Column(type=Type.LOGIN)
	private String login;
	
	@Column(type=Type.PASSWORD)
	private String password;
	
	@Column(hidden=true, type=Type.INTEGER)
	private int role;

	

	/**
	 * Default constructor.
	 */
	public User() {

	}

	/**
	 * @return the id_user
	 */
	public int getId_user() {
	     return this.id_user;
	}

	/**
	 * @param value the id_user to set
	 */
	public void setId_user(final int value) {
	     this.id_user = value;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
	     return this.login;
	}

	/**
	 * @param value the login to set
	 */
	public void setLogin(final String value) {
	     this.login = value;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
	     return this.password;
	}

	/**
	 * @param value the password to set
	 */
	public void setPassword(final String value) {
	     this.password = value;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
	     return this.role;
	}

	/**
	 * @param value the role to set
	 */
	public void setRole(final int value) {
	     this.role = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_user());
		dest.writeString(this.getLogin());
		dest.writeString(this.getPassword());
		dest.writeInt(this.getRole());
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId_user(parc.readInt());
		this.setLogin(parc.readString());
		this.setPassword(parc.readString());
		this.setRole(parc.readInt());
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public User(Parcel parc) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.readFromParcel(parc);

		// You can  implement your own parcel mechanics here.

	}

	/* This method is not regenerated. You can implement your own parcel mechanics here. */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.writeToParcelRegen(dest, flags);

		// You can  implement your own parcel mechanics here.
	}

	@Override
	public int describeContents() {
		// This should return 0 
		// or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
		return 0;
	}

	/**
	 * Parcelable creator.
	 */
	public static final Parcelable.Creator<User> CREATOR
	    = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
		    return new User(in);
		}
		
		public User[] newArray(int size) {
		    return new User[size];
		}
	};

}
