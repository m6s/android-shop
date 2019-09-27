package info.mschmitt.shop.core.network;

import android.os.Build;

import info.mschmitt.shop.app.BuildConfig;

/**
 * @author Matthias Schmitt
 */
public class UserAgent {
    public final String header =
            String.format("Shop-Android/%s (Android %s)", BuildConfig.VERSION_CODE,
                    Build.VERSION.SDK_INT);
}
