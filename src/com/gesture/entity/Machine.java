package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nullable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Une machine est nommé et rélié a une zone, on y enregistre des logs
 * @author alexandre
 *
 */
@Entity
public class Machine  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Machine";


	/* Members */
	@Id
	@Column(hidden=true)
	private int id_machine;
	
	@Column(type=Type.LOGIN)
	private String nom;
	
	@ManyToOne
	private Zone zone;
	
	@OneToMany
	@Column(nullable=true)
	private ArrayList<LogTraca> LogTracas;

	

	/**
	 * Default constructor.
	 */
	public Machine() {

	}

	/**
	 * @return the id_machine
	 */
	public int getId_machine() {
	     return this.id_machine;
	}

	/**
	 * @param value the id_machine to set
	 */
	public void setId_machine(final int value) {
	     this.id_machine = value;
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
	 * @return the zone
	 */
	public Zone getZone() {
	     return this.zone;
	}

	/**
	 * @param value the zone to set
	 */
	public void setZone(final Zone value) {
	     this.zone = value;
	}

	/**
	 * @return the Logs
	 */
	public ArrayList<LogTraca> getLogs() {
	     return this.LogTracas;
	}

	/**
	 * @param value the Logs to set
	 */
	public void setLogs(final ArrayList<LogTraca> value) {
	     this.LogTracas = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_machine());
		dest.writeString(this.getNom());

		dest.writeParcelable(this.getZone(), flags);

		if (this.getLogTracas() != null) {
			dest.writeInt(this.getLogTracas().size());
			for (LogTraca item : this.getLogTracas()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId_machine(parc.readInt());
		this.setNom(parc.readString());

		this.setZone((Zone) parc.readParcelable(Zone.class.getClassLoader()));

		int nbLogTracas = parc.readInt();
		if (nbLogTracas > -1) {
			ArrayList<LogTraca> items =
				new ArrayList<LogTraca>();
			for (int i = 0; i < nbLogTracas; i++) {
				items.add((LogTraca) parc.readParcelable(
						LogTraca.class.getClassLoader()));
			}
			this.setLogTracas(items);
		}
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Machine(Parcel parc) {
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
	public static final Parcelable.Creator<Machine> CREATOR
	    = new Parcelable.Creator<Machine>() {
		public Machine createFromParcel(Parcel in) {
		    return new Machine(in);
		}
		
		public Machine[] newArray(int size) {
		    return new Machine[size];
		}
	};

	/**
	 * @return the LogTracas
	 */
	public ArrayList<LogTraca> getLogTracas() {
	     return this.LogTracas;
	}

	/**
	 * @param value the LogTracas to set
	 */
	public void setLogTracas(final ArrayList<LogTraca> value) {
	     this.LogTracas = value;
	}

}
