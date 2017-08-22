package com.mkrworld.stories.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.delhivery.woosh.utils.AppConfig;
import com.delhivery.woosh.utils.Tracer;

/**
 * Class is used to get the information regarding the network connection of
 * device. Weather the device is connected or not, if connected then connection
 * is slow or not.
 */
public class ConnectivityInfo {
	private static final String	TAG	= AppConfig.BASE_TAG + ConnectivityInfo.class.getSimpleName();

	/**
	 * Get the Information of network
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		Tracer.debug(TAG, "ConnectivityInfo.getNetworkInfo()");
		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	}

	/**
	 * Method to check weather the device is connected to the network or not
	 * 
	 * @param context
	 * @return TRUE if connected with network, else return FALSE
	 */
	public static boolean isConnected(Context context) {
		Tracer.debug(TAG, "ConnectivityInfo.isConnected()");
		NetworkInfo info = ConnectivityInfo.getNetworkInfo(context);
		return (info != null && info.isConnected());
	}

	/**
	 * Check if there is any connectivity to a Wifi network
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static boolean isConnectedWifi(Context context) {
		Tracer.debug(TAG, "ConnectivityInfo.isConnectedWifi()");
		NetworkInfo info = ConnectivityInfo.getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
	}

	/**
	 * Check if there is any connectivity to a mobile network
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static boolean isConnectedMobile(Context context) {
		Tracer.debug(TAG, "ConnectivityInfo.isConnectedMobile()");
		NetworkInfo info = ConnectivityInfo.getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	}

	/**
	 * Check if there is fast connectivity
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnectedFast(Context context) {
		Tracer.debug(TAG, "ConnectivityInfo.isConnectedFast()");
		NetworkInfo info = ConnectivityInfo.getNetworkInfo(context);
		return (info != null && info.isConnected() && ConnectivityInfo.isConnectionFast(info.getType(), info.getSubtype()));
	}

	/**
	 * Check if the connection is fast
	 * 
	 * @param type
	 * @param subType
	 * @return
	 */
	public static boolean isConnectionFast(int type, int subType) {
		Tracer.debug(TAG, "ConnectivityInfo.isConnectionFast() Type: " + type + "   SubType: " + subType);
		if (type == ConnectivityManager.TYPE_WIFI) {
			return true;
		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				return true; // ~ 1-2 Mbps
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return true; // ~ 5 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return true; // ~ 10-20 Mbps
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return false; // ~25 kbps
			case TelephonyManager.NETWORK_TYPE_LTE:
				return true; // ~ 10+ Mbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			default:
				return false;
			}
		} else {
			return false;
		}
	}
}