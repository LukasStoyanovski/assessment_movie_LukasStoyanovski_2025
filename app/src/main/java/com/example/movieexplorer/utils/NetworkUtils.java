package com.example.movieexplorer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Utility class for checking the device's internet connectivity status.
 * Supports both modern (API 23+) and legacy Android versions.
 */
public class NetworkUtils {

    /**
     * Returns true if the device is currently connected to the internet,
     * either via Wi-Fi or mobile data.
     *
     * @param context The application or activity context
     * @return true if internet is available, false otherwise
     */
    public static boolean isConnected(Context context) {
        // Get system connectivity service
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // If service is unavailable, return false
        if (cm == null) return false;

        // For Android 6.0+ (API 23 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());

            // Check if connected to either Wi-Fi or mobile data
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            // For Android versions below 6.0
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
    }
}
