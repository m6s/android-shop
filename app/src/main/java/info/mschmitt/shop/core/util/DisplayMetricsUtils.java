package info.mschmitt.shop.core.util;

import android.content.res.Resources;

/**
 * @author Matthias Schmitt
 */
public class DisplayMetricsUtils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
