package info.mschmitt.shop.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.Locale;

/**
 * @author Matthias Schmitt
 */
public class ShopModule {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;

    public ShopModule(CrashReporter crashReporter, UsageTracker usageTracker, Database database, RestClient restClient) {
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
    }

    public static ShopModule create(Context context) {
        Database database = createDatabase(context);
        RestClient restClient = createApiClient(context, database);
        CrashReporter crashReporter = new CrashReporter();
        UsageTracker usageTracker = new UsageTracker(database);
        return new ShopModule(crashReporter, usageTracker, database, restClient);
    }

    private static Database createDatabase(Context context) {
        File filesDir = context.getFilesDir();
        return new Database(filesDir, Schedulers.from(AsyncTask.SERIAL_EXECUTOR));
    }

    public static RestClient createApiClient(Context context, Database database) {
        File cacheDir = context.getCacheDir();
        String userAgent = String.format(Locale.US, "%s/%s (Linux; Android %s) okhttp3", BuildConfig.APPLICATION_ID,
                BuildConfig.VERSION_CODE, Build.VERSION.RELEASE);
        return new RestClient(cacheDir, database, userAgent);
    }

    public void inject(ShopApplication application) {
        application.setDependencies();
    }

    public void inject(MainActivity activity) {
        activity.setDependencies(crashReporter, usageTracker, database, restClient);
    }
}
