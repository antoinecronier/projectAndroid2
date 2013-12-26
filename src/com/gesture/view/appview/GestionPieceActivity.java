package com.gesture.view.appview;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimePrinter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.criterias.CommandeCriterias;
import com.gesture.criterias.LogTracaCriterias;
import com.gesture.criterias.ProduitCriterias;
import com.gesture.criterias.UserCriterias;
import com.gesture.criterias.base.Criteria.Type;
import com.gesture.criterias.base.CriteriasBase.GroupType;
import com.gesture.data.CommandeSQLiteAdapter;
import com.gesture.data.LogTracaSQLiteAdapter;
import com.gesture.data.ProduitSQLiteAdapter;
import com.gesture.data.UserSQLiteAdapter;
import com.gesture.data.WindowsGesture2SQLiteOpenHelper;
import com.gesture.entity.Commande;
import com.gesture.entity.LogTraca;
import com.gesture.entity.Machine;
import com.gesture.entity.Produit;
import com.gesture.entity.User;
import com.gesture.entity.Zone;
import com.gesture.provider.LogTracaProviderAdapter;
import com.gesture.provider.utils.CommandeProviderUtils;
import com.gesture.provider.utils.LogTracaProviderUtils;
import com.gesture.provider.utils.UserProviderUtils;
import com.gesture.view.appcode.Constantes;
import com.gesture.view.appcode.LiaisonAutomate;
import com.gesture.view.zone.ZoneListActivity;

/**
 * Gère la production sur une machine donnée pour un produit donnée en générant
 * un log
 * 
 * @author alexandre
 * 
 */
public class GestionPieceActivity extends Activity {

	Context monContext;
	User userForInstance;
	LogTraca currentLog;
	Zone currentZone;
	Machine currentMachine;
	Produit currentProduit;
	Commande currentCommande;
	LiaisonAutomate automate;

	SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(GestionPieceActivity.this);

	AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Init object */
		userForInstance = new User();
		currentLog = new LogTraca();
		currentZone = new Zone();
		currentMachine = new Machine();
		currentProduit = new Produit();

		setContentView(R.layout.app_gestion_piece);
		monContext = (Context) this;

		Bundle monBundle;
		monBundle = this.getIntent().getExtras();

		/* Set object from preference */
		userForInstance = (User) monBundle.get("CurrentUser");
		currentZone = (Zone) monBundle.get("CurrentZone");
		currentMachine = (Machine) monBundle.get("CurrentMachine");

		/* Set object from DB */
		CommandeCriterias critCommande = new CommandeCriterias(GroupType.AND);
		critCommande.add(CommandeSQLiteAdapter.COL_AVANCEMENT, "0",
				Type.SUPERIOR);

		/* Récupère les commandes étant en progression */
		ArrayList<Commande> commandes = new CommandeProviderUtils(monContext)
				.query(critCommande);

		if (!commandes.isEmpty()) {
			currentCommande = commandes.get(0);

			ArrayList<Produit> produits = new CommandeProviderUtils(monContext)
					.getAssociateProduits(currentCommande);

			/* Check log exist, if not create */
			/*
			 * getPathSegments().get(1) check # we have to remplace by commande
			 * ID
			 */
			Uri idCommande = null;
			idCommande.encode(String
					.valueOf(LogTracaProviderAdapter.LOGTRACA_URI + "/"
							+ currentCommande.getId_cmd()
							+ "/produitOnCommande"));
			Cursor monCu = new LogTracaProviderAdapter(monContext).query(
					idCommande, null, null, null, null);

			/* Set currentLog if have it */
			if (monCu != null) {
				currentLog.setDateEntre(monCu.getString(monCu
						.getColumnIndex(LogTracaSQLiteAdapter.COL_DATEENTRE)));
				currentLog.setDateSortie(monCu.getString(monCu
						.getColumnIndex(LogTracaSQLiteAdapter.COL_DATESORTIE)));
				currentLog.setDuree(monCu.getString(monCu
						.getColumnIndex(LogTracaSQLiteAdapter.COL_DUREE)));
				currentLog.setId_log(monCu.getInt(monCu
						.getColumnIndex(LogTracaSQLiteAdapter.COL_ID_LOG)));
				currentLog
						.getMachine()
						.setId_machine(
								monCu.getInt(monCu
										.getColumnIndex(LogTracaSQLiteAdapter.COL_MACHINE)));
				currentLog
						.getProduit()
						.setId_produit(
								monCu.getInt(monCu
										.getColumnIndex(LogTracaSQLiteAdapter.COL_PRODUIT)));
				currentLog
						.getUser()
						.setId_user(
								monCu.getInt(monCu
										.getColumnIndex(LogTracaSQLiteAdapter.COL_USER)));
			} else {
				currentLog.setDateEntre(DateTime.now().toString());
				currentLog.setMachine(currentMachine);
				currentLog.setProduit(currentProduit);
				currentLog.setUser(userForInstance);

				/* Show box to enter duration */
				builder = new Builder(monContext);
				final EditText text = new EditText(monContext);

				builder.setTitle("Entrer delais de conception").setMessage("")
						.setView(text);

				/* Button to set duration */
				builder.setPositiveButton("Valider",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (Integer.getInteger(text.getText()
										.toString()) != null) {
									currentLog.setDuree(text.getText()
											.toString());
								} else {
									builder.create().show();
								}
							}
						});
				builder.create().show();
			}
		}

		ImageButton buttonConnexion = (ImageButton) this
				.findViewById(R.id.imageButton1);
		buttonConnexion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// made save

				Editor edit = prefs.edit();
				edit.putInt("LastCurrentUser", userForInstance.getId_user());
				edit.putString("LastCurrentScreen",
						ZoneListActivity.class.toString());
				edit.commit();
				GestionPieceActivity.this
						.finishActivity(Constantes.GESTION_PIECE_ACTIVITY);
			}
		});

		Button textViewBtn_start_pdt = (Button) this
				.findViewById(R.id.btn_start_pdt);
		textViewBtn_start_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Démarre l'usinage */
				automate = new LiaisonAutomate(currentLog);
			}
		});

		Button textViewBtn_stop_pdt = (Button) this
				.findViewById(R.id.btn_stop_pdt);
		textViewBtn_stop_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Arrête l'usinage en gardant la durée restante */
				currentLog.setDuree(automate.getLogtraca().getDuree());
				LogTracaProviderUtils provider = new LogTracaProviderUtils(
						monContext);
				provider.insert(currentLog);
				automate = null;
			}
		});

		Button textViewBtn_next_pdt = (Button) this
				.findViewById(R.id.btn_next_pdt);
		textViewBtn_next_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO implement next item
				currentLog.setDateSortie(DateTime.now().toString());
				LogTracaProviderUtils provider = new LogTracaProviderUtils(
						monContext);
				provider.insert(currentLog);
				new GestionPieceActivity();
			}
		});

		Button textViewBtn_rebut_pdt = (Button) this
				.findViewById(R.id.btn_rebut_pdt);
		textViewBtn_rebut_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Button textViewBtn_change_zone = (Button) this
				.findViewById(R.id.btn_change_zone);
		textViewBtn_change_zone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		/* Remplit les objects de la fenêtre */

		TextView textViewZone = (TextView) this.findViewById(R.id.tvZone);
		TextView textViewProduit = (TextView) this.findViewById(R.id.tvProduit);
		TextView textViewStatut = (TextView) this.findViewById(R.id.tvStatut);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
