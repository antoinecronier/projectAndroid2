package com.gesture.view.appcode;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;
import com.gesture.view.appview.GestionPieceActivity;

public class LiaisonAutomate {

	LogTraca logtraca;

	public LogTraca getLogtraca() {
		return logtraca;
	}

	/**
	 * Simule le timer d'une machine
	 * 
	 * @param logTraca
	 */
	public LiaisonAutomate(LogTraca logTraca) {
		this.logtraca = logTraca;
		
		int delay = Integer.parseInt(logTraca.getDuree());
		delay = delay / 100;

		/*Attend chaque 1% et enlève 1% de la durée à la fin de chaque boucle.*/
		for (int index = 0; index < 100; index++) {
			try {
				Thread.sleep(delay);
				logtraca.setDuree(String.valueOf(Integer.parseInt(logTraca
						.getDuree()) - delay));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
