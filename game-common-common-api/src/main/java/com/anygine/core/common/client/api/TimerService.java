package com.anygine.core.common.client.api;

import java.util.ArrayList;
import java.util.List;

import playn.core.Sound;

import com.anygine.core.common.client.TimerCallback;
import com.anygine.core.common.client.inject.PlayNInjectorManager;
import com.google.inject.Singleton;

@Singleton
public class TimerService {

	private static class TimesAndCallback {
		public final long registerTime;
		public final long delayTime;
		public final TimerCallback callback;
		
		public TimesAndCallback(
				long registerTime, long delayTime, TimerCallback callback) {
			this.registerTime = registerTime;
			this.delayTime = delayTime;
			this.callback = callback;
		}
	}

	private final Sound warningSound;
	private final TimerCallback warningCallback; 
	private final List<TimesAndCallback> timesAndCallbacks;
	
	public TimerService() {
		// TODO: Change to a separate warning sound
		warningSound = PlayNInjectorManager.getInjector().getAssetManager().getSound("Sounds/Searching");
//    warningSound = PlayN.assetManager().getSound("Sounds/Searching");
		warningCallback = new TimerCallback() {
			@Override
			public void onTimer() {
				warningSound.play();
			}
		};
		timesAndCallbacks = new ArrayList<TimesAndCallback>();
	}
	
	public void registerCallback(
			float delaySecs, float warnAt, TimerCallback timerCallback) {
		long now = System.currentTimeMillis();
		// Add callback for warning sound if specified by original callback
		if (warnAt > 0.0f && warnAt < delaySecs) {
			timesAndCallbacks.add(
					new TimesAndCallback(
							now, (long) warnAt * 1000, warningCallback));
		}
		timesAndCallbacks.add(new TimesAndCallback(
				now, (long) delaySecs * 1000, timerCallback));
	}
	
	public void checkTimers() {
		List<TimesAndCallback> expiredList = new ArrayList<TimesAndCallback>();
		long now = System.currentTimeMillis();
		for (TimesAndCallback timesAndCallback : timesAndCallbacks) {
			if (timesAndCallback.registerTime + timesAndCallback.delayTime 
					< now) {
				timesAndCallback.callback.onTimer();
				expiredList.add(timesAndCallback);
			}
		}
		timesAndCallbacks.removeAll(expiredList);
	}
}
	
		
