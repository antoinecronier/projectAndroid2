package com.gesture.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

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
 * Définit un produit, un produit possède une quantité, ainsi qu'un état, un
 * matériel et un type
 * 
 * @author alexandre
 * 
 */
@Entity
public class Produit  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Produit";


	/* Members */
	@Id
	@Column(hidden=true)
	private int id_produit;

	public enum ProduitType {
		
		/**
         * fenetre
         */
		fenetre("fenetre"),
        /**
         * porte.
         */
		porte("porte");

        /** Type type. */
        @Id
        private String type;

        /**
         * Enum Type of the alert.
         *
         * @param value Integer Type value
         */
        private ProduitType(final String value) {
                this.type = value;
        }

        /**
         * Return the type.
         *
         * @return String Type
         */
        public final String getValue() {
                return this.type;
        }

        /**
         * Get the type by its name if it exists.
         *
         * @param value The type name
         * @return the corresponding type. null if nothing is found.
         */
        public static final ProduitType fromValue(final String value) {
        	ProduitType ret = null;
                if (value != null) {
                        for (final ProduitType type : ProduitType.values()) {
                                if (value.equals(type.type)) {
                                        ret = type;
                                }
                        }
                }

                return ret;
        }
	};

	public enum ProduitEtat {
		/**
         * encours
         */
		encours("encours"),
        /**
         * porte.
         */
		valide("valide"),
		/**
         * rebut
         */
		rebut("rebut"),
        /**
         * standby.
         */
		standby("standby");

        /** Type type. */
        @Id
        private String type;

        /**
         * Enum Type of the alert.
         *
         * @param value Integer Type value
         */
        private ProduitEtat(final String value) {
                this.type = value;
        }

        /**
         * Return the type.
         *
         * @return String Type
         */
        public final String getValue() {
                return this.type;
        }

        /**
         * Get the type by its name if it exists.
         *
         * @param value The type name
         * @return the corresponding type. null if nothing is found.
         */
        public static final ProduitEtat fromValue(final String value) {
        	ProduitEtat ret = null;
                if (value != null) {
                        for (final ProduitEtat type : ProduitEtat.values()) {
                                if (value.equals(type.type)) {
                                        ret = type;
                                }
                        }
                }

                return ret;
        }
	};

	public enum ProduitMateriel {
		/**
         * aluminium
         */
		aluminium("aluminium"),
        /**
         * acier.
         */
		acier("acier"),
		/**
         * rebut
         */
		acierrenforce("acierrenforce");

        /** Type type. */
        @Id
        private String type;

        /**
         * Enum Type of the alert.
         *
         * @param value Integer Type value
         */
        private ProduitMateriel(final String value) {
                this.type = value;
        }

        /**
         * Return the type.
         *
         * @return String Type
         */
        public final String getValue() {
                return this.type;
        }

        /**
         * Get the type by its name if it exists.
         *
         * @param value The type name
         * @return the corresponding type. null if nothing is found.
         */
        public static final ProduitMateriel fromValue(final String value) {
        	ProduitMateriel ret = null;
                if (value != null) {
                        for (final ProduitMateriel type : ProduitMateriel.values()) {
                                if (value.equals(type.type)) {
                                        ret = type;
                                }
                        }
                }

                return ret;
        }
	};

	@Column(type=Type.ENUM)
	private ProduitType type;
	
	@Column(type=Type.ENUM)
	private ProduitEtat etat;
	
	@Column(type=Type.ENUM)
	private ProduitMateriel materiel;
	
	@ManyToOne
	private Commande commande;

	@Column(type=Type.INTEGER)
	private int quantite;
	
	@Column(type=Type.INTEGER)
	private int avancement;

	

	/**
	 * Default constructor.
	 */
	public Produit() {

	}

	/**
	 * @return the id_produit
	 */
	public int getId_produit() {
	     return this.id_produit;
	}

	/**
	 * @param value the id_produit to set
	 */
	public void setId_produit(final int value) {
	     this.id_produit = value;
	}

	/**
	 * @return the type
	 */
	public ProduitType getType() {
	     return this.type;
	}

	/**
	 * @param value the type to set
	 */
	public void setType(final ProduitType value) {
	     this.type = value;
	}

	/**
	 * @return the etat
	 */
	public ProduitEtat getEtat() {
	     return this.etat;
	}

	/**
	 * @param value the etat to set
	 */
	public void setEtat(final ProduitEtat value) {
	     this.etat = value;
	}

	/**
	 * @return the materiel
	 */
	public ProduitMateriel getMateriel() {
	     return this.materiel;
	}

	/**
	 * @param value the materiel to set
	 */
	public void setMateriel(final ProduitMateriel value) {
	     this.materiel = value;
	}

	/**
	 * @return the commande
	 */
	public Commande getCommande() {
	     return this.commande;
	}

	/**
	 * @param value the commande to set
	 */
	public void setCommande(final Commande value) {
	     this.commande = value;
	}

	/**
	 * @return the quantite
	 */
	public int getQuantite() {
	     return this.quantite;
	}

	/**
	 * @param value the quantite to set
	 */
	public void setQuantite(final int value) {
	     this.quantite = value;
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
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId_produit());

		if (this.getType() != null) {
			dest.writeInt(1);
			dest.writeString(this.getType().getValue());
		} else {
			dest.writeInt(0);
		}

		if (this.getEtat() != null) {
			dest.writeInt(1);
			dest.writeString(this.getEtat().getValue());
		} else {
			dest.writeInt(0);
		}

		if (this.getMateriel() != null) {
			dest.writeInt(1);
			dest.writeString(this.getMateriel().getValue());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getCommande(), flags);
		dest.writeInt(this.getQuantite());
		dest.writeInt(this.getAvancement());
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId_produit(parc.readInt());

		int typeBool = parc.readInt();
		if (typeBool == 1) {
			this.setType(ProduitType.fromValue(parc.readString()));
		}

		int etatBool = parc.readInt();
		if (etatBool == 1) {
			this.setEtat(ProduitEtat.fromValue(parc.readString()));
		}

		int materielBool = parc.readInt();
		if (materielBool == 1) {
			this.setMateriel(ProduitMateriel.fromValue(parc.readString()));
		}

		this.setCommande((Commande) parc.readParcelable(Commande.class.getClassLoader()));
		this.setQuantite(parc.readInt());
		this.setAvancement(parc.readInt());
	}




	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Produit(Parcel parc) {
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
	public static final Parcelable.Creator<Produit> CREATOR
	    = new Parcelable.Creator<Produit>() {
		public Produit createFromParcel(Parcel in) {
		    return new Produit(in);
		}
		
		public Produit[] newArray(int size) {
		    return new Produit[size];
		}
	};

}
