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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.gesture.provider.utils.CommandeProviderUtils;
import com.gesture.provider.utils.LogTracaProviderUtils;
import com.gesture.provider.utils.UserProviderUtils;
import com.gesture.view.appcode.Constantes;

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

			/* Check log exist if not create */
			LogTracaCriterias critLogTraca = new LogTracaCriterias(
					GroupType.AND);
			critCommande.add(LogTracaSQLiteAdapter.COL_MACHINE,
					String.valueOf(currentMachine.getId_machine()));
			critCommande.add(LogTracaSQLiteAdapter.COL_PRODUIT,
					String.valueOf(currentProduit.getId_produit()));
			// TODO inner-joint
			critCommande.add(ProduitSQLiteAdapter.COL_COMMANDE,
					String.valueOf(currentCommande.getId_cmd()));

			ArrayList<LogTraca> logsTraca = new LogTracaProviderUtils(
					monContext).query(critLogTraca);

			if (!logsTraca.isEmpty()) {
				currentLog = logsTraca.get(0);
			} else {
				currentLog.setDateEntre(DateTime.now().toString());
				currentLog.setMachine(currentMachine);
				currentLog.setProduit(currentProduit);
				currentLog.setUser(userForInstance);

				//LogTracaProviderUtils logTracaProvider = navigateUpTo(upIntent)
				/* Show box to enter duration */
			}
		}

		ImageButton buttonConnexion = (ImageButton) this
				.findViewById(R.id.imageButton1);
		buttonConnexion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// made save

				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(GestionPieceActivity.this);
				Editor edit = prefs.edit();
				edit.putInt("LastCurrentUser", userForInstance.getId_user());
				edit.putString("LastCurrentScreen",
						AccueilUserActivity.class.toString());
				GestionPieceActivity.this
						.finishActivity(Constantes.GESTION_PIECE_ACTIVITY);
			}
		});

		Button textViewBtn_start_pdt = (Button) this
				.findViewById(R.id.btn_start_pdt);
		textViewBtn_start_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Button textViewBtn_stop_pdt = (Button) this
				.findViewById(R.id.btn_stop_pdt);
		textViewBtn_stop_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Button textViewBtn_next_pdt = (Button) this
				.findViewById(R.id.btn_next_pdt);
		textViewBtn_next_pdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
