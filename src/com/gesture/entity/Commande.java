package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nullable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Column.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Une commande, liée a un client, représente une somme de produit, 
 * elle possède * un avancement ainsi que des dates de controle
 * 
 * @author alexandre
 * 
 */
@Entity
public class Commande  implements Serializable , Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Commande";


	/* Members */
	@Id
	@Column(hidden=true)
	private int id_cmd;
	
	@ManyToOne
	private Client client;
	
	@Column(type=Type.DATETIME)
	private String dateCreation;
	
	@Column(type=Type.DATETIME, nullable=true)
	private String dateFin;
	
	@Column(type=Type.DATETIME, nullable=true)
	private String dateLivraison;
	
	@Column(type=Type.INTEGER)
	private int avancement;
	
	@OneToMany
	private ArrayList<Produit> produits;

	

	/**
	 * Default constructor.
	 */
	public Commande() {

	}

	/**
	 * @return the id_cmd
	 */
	public int getId_cmd() {
	     return this.id_cmd;
	}

	/**
	 * @param value the id_cmd to set
	 */
	public void setId_cmd(final int value) {
	     this.id_cmd = value;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
	     return this.client;
	}

	/**
	 * @param value the client to set
	 */
	public void setClient(final Client value) {
	     this.client = value;
	}

	/**
	 * @return the dateCreation
	 */
	public String getDateCreation() {
	     return this.dateCreation;
	}

	/**
	 * @param value the dateCreation to set
	 */
	public void setDateCreation(final String value) {
	     this.dateCreation = value;
	}

	/**
	 * @return the dateFin
	 */
	public String getDateFin() {
	     return this.dateFin;
	}

	/**
	 * @param value the dateFin to set
	 */
	public void setDateFin(final String value) {
	     this.dateFin = value;
	}

	/**
	 * @return the dateLivraison
	 */
	public String getDateLivraison() {
	     return this.dateLivraison;
	}

	/**
	 * @param value the dateLivraison to set
	 */
	public void setDateLivraison(final String value) {
	     this.dateLivraison = value;
	}

	/**
	 * @return the avancement
	 */
	public int getAvancement() {
	     return this.avancement;
	}

	/**
	 * @param value the avancement to set
	 */
	public void setAvancement(final int value) {
	     this.avancement = value;
	}

	/**
	 * @return the produits
	 */
	public ArrayList<Produit> getProduits() {
	     return this.produits;
	}

	/**
	 * @param value the produits to set
	 */
	public void setProduits(final ArrayList<Produit> value) {
	     this.produits = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_cmd());

		dest.writeParcelable(this.getClient(), flags);
		dest.writeString(this.getDateCreation());
		dest.writeString(this.getDateFin());
		dest.writeString(this.getDateLivraison());
		dest.writeInt(this.getAvancement());

		if (this.getProduits() != null) {
			dest.writeInt(this.getProduits().size());
			for (Produit item : this.getProduits()) {
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
		this.setId_cmd(parc.readInt());

		this.setClient((Client) parc.readParcelable(Client.class.getClassLoader()));
		this.setDateCreation(parc.readString());
		this.setDateFin(parc.readString());
		this.setDateLivraison(parc.readString());
		this.setAvancement(parc.readInt());

		int nbProduits = parc.readInt();
		if (nbProduits > -1) {
			ArrayList<Produit> items =
				new ArrayList<Produit>();
			for (int i = 0; i < nbProduits; i++) {
				items.add((Produit) parc.readParcelable(
						Produit.class.getClassLoader()));
			}
			this.setProduits(items);
		}
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Commande(Parcel parc) {
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
	public static final Parcelable.Creator<Commande> CREATOR
	    = new Parcelable.Creator<Commande>() {
		public Commande createFromParcel(Parcel in) {
		    return new Commande(in);
		}
		
		public Commande[] newArray(int size) {
		    return new Commande[size];
		}
	};

}
