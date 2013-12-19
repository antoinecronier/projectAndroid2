package com.gesture.view.appcode;

/**
 * Constantes g�n�rales � l'application
 */
public class Constantes {

	/**
	 * Constantes BDD
	 */
	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "WindowsGesture";

	// Tables Name
	public static final String TABLE_NAME_USER = "USER";
	public static final String TABLE_NAME_ZONE = "ZONE";
	public static final String TABLE_NAME_COMMANDE = "COMMANDE";
	public static final String TABLE_NAME_CLIENT = "CLIENT";
	public static final String TABLE_NAME_PRODUIT = "PRODUIT";
	public static final String TABLE_NAME_LOG = "LOG";
	public static final String TABLE_NAME_MACHINE = "MACHINE";

	// Columns name -USER-
	public static final String USER_ID = "id_user";
	public static final String USER_LOGIN = "login";
	public static final String USER_PWD = "password";
	public static final String USER_ROLE = "role";

	// Columns name -ZONE-
	public static final String ZONE_ID = "id_zone";
	public static final String ZONE_NOM = "nom";

	// Columns name -MACHINE-
	public static final String MACHINE_ID = "id_user";
	public static final String MACHINE_ZONE_ID = "id_zone";
	public static final String MACHINE_NAME = "nom";

	// Columns name -COMMANDE-
	public static final String COMMANDE_ID = "id_cmd";
	public static final String COMMANDE_DATECREATION = "dateCreation";
	public static final String COMMANDE_DATEFIN = "dateFin";
	public static final String COMMANDE_DATELIVRAISON = "dateLivraison";
	public static final String COMMANDE_AVANCEMENT = "avancement";
	public static final String COMMANDE_PRODUITS = "produits";
	public static final String COMMANDE_CLIENT_ID = "id_client";

	// Columns name -CLIENT-
	public static final String CLIENT_ID = "id_client";
	public static final String CLIENT_NOM = "nom";

	// Columns name -PRODUIT-
	public static final String PRODUIT_ID = "id_produit";
	public static final String PRODUIT_AVANCEMENT = "avancement";
	public static final String PRODUIT_TYPE = "type";
	public static final String PRODUIT_ETAT = "etat";
	public static final String PRODUIT_MATERIEL = "materiel";
	public static final String PRODUIT_COMMANDE_ID = "id_commande";

	// Columns name -LOG-
	public static final String LOG_ID = "id_log";
	public static final String LOG_PRODUIT_ID = "id_produit";
	public static final String LOG_MACHINE_ID = "id_machine";
	public static final String LOG_DUREE = "duree";
	public static final String LOG_DATEENTREE = "dateEntre";
	public static final String LOG_DATESORTIE = "dateSortie";
	public static final String LOG_USER_ID = "id_user";

	// Create Table USER
	public static final String CREATE_TABLE_USER = "CREATE TABLE "
			+ TABLE_NAME_USER + "" + "(" 
			+ USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ USER_LOGIN+ " TEXT NOT NULL," + "" 
			+ USER_PWD + " TEXT NOT NULL," + ""
			+ USER_ROLE + " INTEGER NOT NULL);";

	// Create Table Client
	public static final String CREATE_TABLE_CLIENT = "CREATE TABLE "
			+ TABLE_NAME_CLIENT + "" + "(" 
			+ CLIENT_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ CLIENT_NOM+ " TEXT NOT NULL);";

	// Create Table ZONE
	public static final String CREATE_TABLE_ZONE = "CREATE TABLE "
			+ TABLE_NAME_ZONE + "" + "(" 
			+ ZONE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ ZONE_NOM+ " TEXT NOT NULL );";

	// Create Table PRODUIT
	public static final String CREATE_TABLE_PRODUIT = "CREATE TABLE "
			+ TABLE_NAME_PRODUIT + "" + "(" 
			+ PRODUIT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ PRODUIT_TYPE+ " TEXT NOT NULL," + "" 
			+ PRODUIT_ETAT + " TEXT NOT NULL," + ""
			+ PRODUIT_MATERIEL + " TEXT NOT NULL," + "" 
			+ PRODUIT_COMMANDE_ID+ " INTEGER NOT NULL," + ""
			+ PRODUIT_AVANCEMENT+ " INTEGER" 
			+ " FOREIGN KEY(" + PRODUIT_COMMANDE_ID + ") "
			+ "REFERENCES " + TABLE_NAME_COMMANDE 
			+ "(" + COMMANDE_ID + "));";

	// CREATE Table MACHINE
	public static final String CREATE_TABLE_MACHINE = "CREATE TABLE "
			+ TABLE_NAME_MACHINE + "" + "(" 
			+ MACHINE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ MACHINE_NAME + " TEXT NOT NULL," + "" 
			+ MACHINE_ZONE_ID+ " INTEGER NOT NULL " 
			+ " FOREIGN KEY(" + MACHINE_ZONE_ID + ") "
			+ "REFERENCES " + TABLE_NAME_ZONE 
			+ "(" + ZONE_ID + "));";

	// CREATE Table COMMANDE
	public static final String CREATE_TABLE_COMMANDE = "CREATE TABLE "
			+ TABLE_NAME_COMMANDE + "" + "(" 
			+ COMMANDE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ COMMANDE_CLIENT_ID+ " TEXT NOT NULL " + "" 
			+ COMMANDE_DATECREATION+ " TEXT NOT NULL," + "" 
			+ COMMANDE_DATEFIN + " TEXT," + ""
			+ COMMANDE_DATELIVRAISON + " TEXT," + "" 
			+ COMMANDE_AVANCEMENT+ " INTEGER" 
			+ " FOREIGN KEY(" + COMMANDE_CLIENT_ID + ") " 
			+ "REFERENCES "	+ TABLE_NAME_CLIENT 
			+ "(" + CLIENT_ID + "));";

	// CREATE Table LOG
	public static final String CREATE_TABLE_LOG = "CREATE TABLE "
			+ TABLE_NAME_LOG + "" + "(" 
			+ LOG_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + "" 
			+ LOG_PRODUIT_ID+ " INTEGER NOT NULL " + "" 
			+ LOG_MACHINE_ID + " INTEGER NOT NULL "	+ "" 
			+ LOG_USER_ID+ " INTEGER NOT NULL " + "" 
			+ LOG_DUREE + " TEXT," + "" 
			+ LOG_DATEENTREE + " TEXT," + ""
			+ LOG_DATESORTIE + " TEXT," 
			+ " FOREIGN KEY(" + LOG_PRODUIT_ID+ ") " 
			+ "REFERENCES " + TABLE_NAME_PRODUIT + "(" + PRODUIT_ID+ ")" 
			+ " FOREIGN KEY(" + LOG_USER_ID+ ") " 
			+ "REFERENCES " + TABLE_NAME_USER + "(" + USER_ID+ ")" 
			+ "FOREIGN KEY(" + LOG_MACHINE_ID + ") " 
			+ "REFERENCES "	+ TABLE_NAME_MACHINE + "(" + MACHINE_ID + ");";
	
	/**
	 * COnstantes Activities
	 */
	public static final int LOGIN_ACTIVITY = 0;
	public static final int ACCUEIL_USER_ACTIVITY = 1;
	public static final int GESTION_PIECE_ACTIVITY = 11;
	public static final int ACCUEIL_QUALITE_ACTIVITY = 2;
	public static final int CREER_COMMANDE_ACTIVITY = 21;
	public static final int PARAM_TRACA_ACTIVITY = 22;
	public static final int CHECK_REBUT_ACTIVITY = 23;
	public static final int LISTE_COMMANDE_ACTIVITY = 24;
	public static final int DETAIL_COMMANDE_ACTIVITY = 25;
	public static final int ACCUEIL_LOGICIEL_ACTIVITY = 3;
	public static final int ADD_MACHINE_ACTIVITY = 31;
	public static final int ADD_USER_ACTIVITY = 32;
	public static final int ADD_ZONE_ACTIVITY = 33;
	
}
