package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.crypto.Mac;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Un log est lié a un produit et a une machine, il permet de connètre les
 * durées de fabrication ainsi que les dates d'usinage
 * 
 * @author alexandre
 * 
 */
@Entity
public class LogTraca  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Log";

	/* Members */
	@Id
	@Column(hidden=true)
	private int id_log;
	
	@ManyToOne
	private Produit produit;
	
	@ManyToOne
	private Machine machine;
	
	@ManyToOne
	private User user;
	
	@Column(type=Type.DATETIME, nullable=true)
	private String duree;
	
	@Column(type=Type.DATETIME)
	private String dateEntre;
	
	@Column(type=Type.DATETIME)
	private String dateSortie;

	

	/**
	 * Default constructor.
	 */
	public LogTraca() {

	}

	/**
	 * @return the id_log
	 */
	public int getId_log() {
	     return this.id_log;
	}

	/**
	 * @param value the id_log to set
	 */
	public void setId_log(final int value) {
	     this.id_log = value;
	}

	/**
	 * @return the produit
	 */
	public Produit getProduit() {
	     return this.produit;
	}

	/**
	 * @param value the produit to set
	 */
	public void setProduit(final Produit value) {
	     this.produit = value;
	}

	/**
	 * @return the machine
	 */
	public Machine getMachine() {
	     return this.machine;
	}

	/**
	 * @param value the machine to set
	 */
	public void setMachine(final Machine value) {
	     this.machine = value;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
	     return this.user;
	}

	/**
	 * @param value the user to set
	 */
	public void setUser(final User value) {
	     this.user = value;
	}

	/**
	 * @return the duree (here you have to set duration in int)
	 */
	public String getDuree() {
	     return this.duree;
	}

	/**
	 * @param value the duree to set
	 */
	public void setDuree(final String value) {
	     this.duree = value;
	}

	/**
	 * @return the dateEntre
	 */
	public String getDateEntre() {
	     return this.dateEntre;
	}

	/**
	 * @param value the dateEntre to set
	 */
	public void setDateEntre(final String value) {
	     this.dateEntre = value;
	}

	/**
	 * @return the dateSortie
	 */
	public String getDateSortie() {
	     return this.dateSortie;
	}

	/**
	 * @param value the dateSortie to set
	 */
	public void setDateSortie(final String value) {
	     this.dateSortie = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_log());

		dest.writeParcelable(this.getProduit(), flags);

		dest.writeParcelable(this.getMachine(), flags);

		dest.writeParcelable(this.getUser(), flags);
		dest.writeString(this.getDuree());
		dest.writeString(this.getDateEntre());
		dest.writeString(this.getDateSortie());
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId_log(parc.readInt());

		this.setProduit((Produit) parc.readParcelable(Produit.class.getClassLoader()));

		this.setMachine((Machine) parc.readParcelable(Machine.class.getClassLoader()));

		this.setUser((User) parc.readParcelable(User.class.getClassLoader()));
		this.setDuree(parc.readString());
		this.setDateEntre(parc.readString());
		this.setDateSortie(parc.readString());
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public LogTraca(Parcel parc) {
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
	public static final Parcelable.Creator<LogTraca> CREATOR
	    = new Parcelable.Creator<LogTraca>() {
		public LogTraca createFromParcel(Parcel in) {
		    return new LogTraca(in);
		}
		
		public LogTraca[] newArray(int size) {
		    return new LogTraca[size];
		}
	};

}
