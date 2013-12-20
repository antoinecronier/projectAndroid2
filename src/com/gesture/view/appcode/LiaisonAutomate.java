package com.gesture.view.appcode;

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import com.gesture.entity.LogTraca;
import com.gesture.entity.Produit;

public class LiaisonAutomate {

	static DateTime timerDuration;
	Timer monTimer;

	public static DateTime getTimerDuration() {
		return timerDuration;
	}

	public static void setTimerDuration(DateTime timerDuration) {
		LiaisonAutomate.timerDuration = timerDuration;
	}
	
	public LiaisonAutomate(LogTraca logTraca)
	{
		this.timerDuration = DateTime.parse(logTraca.getDuree());
		monTimer = new Timer("Automate");
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				//logTraca.setDuree(String.valueOf(0.0));
			}
		};
		/* time in millisecond */
		monTimer.schedule(timerTask, Long.parseLong(logTraca.getDuree()));
	}
}
