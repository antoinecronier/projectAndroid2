package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Client definit un client (personne qui passe des commandes)
 * @author alexandre
 *
 */
@Entity
public class Client  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Client";


	/* Members */
	@Id
	@Column(hidden=true)
	private int id_client;
	
	@Column(type=Type.LOGIN)
	private String nom;


	/**
	 * Default constructor.
	 */
	public Client() {

	}

	/**
	 * @return the id_client
	 */
	public int getId_client() {
	     return this.id_client;
	}

	/**
	 * @param value the id_client to set
	 */
	public void setId_client(final int value) {
	     this.id_client = value;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
	     return this.nom;
	}

	/**
	 * @param value the nom to set
	 */
	public void setNom(final String value) {
	     this.nom = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_client());
		dest.writeString(this.getNom());
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId_client(parc.readInt());
		this.setNom(parc.readString());
	}




	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Client(Parcel parc) {
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
	public static final Parcelable.Creator<Client> CREATOR
	    = new Parcelable.Creator<Client>() {
		public Client createFromParcel(Parcel in) {
		    return new Client(in);
		}
		
		public Client[] newArray(int size) {
		    return new Client[size];
		}
	};

}
