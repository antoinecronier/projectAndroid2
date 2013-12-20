package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Une zone est nommé 
 * @author alexandre
 *
 */
@Entity
public class Zone  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Zone";


	/*Members*/
	@Id
	@Column(hidden=true)
	private int id_zone;
	
	@Column(type=Type.LOGIN)
	private  String nom;
	


	/**
	 * Default constructor.
	 */
	public Zone() {

	}

	/**
	 * @return the id_zone
	 */
	public int getId_zone() {
	     return this.id_zone;
	}

	/**
	 * @param value the id_zone to set
	 */
	public void setId_zone(final int value) {
	     this.id_zone = value;
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
		dest.writeInt(this.getId_zone());
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
		this.setId_zone(parc.readInt());
		this.setNom(parc.readString());
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Zone(Parcel parc) {
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
	public static final Parcelable.Creator<Zone> CREATOR
	    = new Parcelable.Creator<Zone>() {
		public Zone createFromParcel(Parcel in) {
		    return new Zone(in);
		}
		
		public Zone[] newArray(int size) {
		    return new Zone[size];
		}
	};

}
