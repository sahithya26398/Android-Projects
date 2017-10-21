
package com.aqua.orl.aquautility;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Method;

public class WifiApManager {
	private final WifiManager mWifiManager;
	private Context context;

	public WifiApManager(Context context) {
		this.context = context;
		mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
	}

	public boolean setWifiApEnabled(WifiConfiguration wifiConfig, boolean enabled) {
		try {
			if (enabled) { // disable WiFi in any case
				mWifiManager.setWifiEnabled(false);
			}

			Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			return (Boolean) method.invoke(mWifiManager, wifiConfig, enabled);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}

	/**
	 * Gets the Wi-Fi enabled state.
	 * @see #isWifiApEnabled()
	 */
	public com.aqua.orl.aquautility.WIFI_AP_STATE getWifiApState() {
		try {
			Method method = mWifiManager.getClass().getMethod("getWifiApState");

			int tmp = ((Integer)method.invoke(mWifiManager));

			// Fix for Android 4
			if (tmp >= 10) {
				tmp = tmp - 10;
			}

			return com.aqua.orl.aquautility.WIFI_AP_STATE.class.getEnumConstants()[tmp];
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return com.aqua.orl.aquautility.WIFI_AP_STATE.WIFI_AP_STATE_FAILED;
		}
	}

	/**
	 * Return whether Wi-Fi AP is enabled or disabled.
	 * @return {@code true} if Wi-Fi AP is enabled
	 * @see #getWifiApState()
	 *
	 * @hide Dont open yet
	 */
	public boolean isWifiApEnabled() {
		return getWifiApState() == com.aqua.orl.aquautility.WIFI_AP_STATE.WIFI_AP_STATE_ENABLED;
	}

	/**
	 * Gets the Wi-Fi AP Configuration.
	 * @return AP details in {@link WifiConfiguration}
	 */
	public WifiConfiguration getWifiApConfiguration() {
		try {
			Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
			return (WifiConfiguration) method.invoke(mWifiManager);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return null;
		}
	}

	/**
	 * Sets the Wi-Fi AP Configuration.
	 * @return {@code true} if the operation succeeded, {@code false} otherwise
	 */
	public boolean setWifiApConfiguration(WifiConfiguration wifiConfig) {
		try {
			Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
			return (Boolean) method.invoke(mWifiManager, wifiConfig);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}


}
