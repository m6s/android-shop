package info.mschmitt.shop.app;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

import info.mschmitt.shop.core.CrashReporter;
import info.mschmitt.shop.core.UsageTracker;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.network.UserAgent;
import info.mschmitt.shop.core.storage.DataStore;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * @author Matthias Schmitt
 */
public class ShopModule {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final DataStore dataStore;
    private final ApiClient apiClient;

    public ShopModule(CrashReporter crashReporter, UsageTracker usageTracker, DataStore dataStore,
                      ApiClient apiClient) {
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.dataStore = dataStore;
        this.apiClient = apiClient;
    }

    public static ShopModule create(Context context) {
        DataStore dataStore = createDataStore(context);
        ApiClient apiClient = createApiClient(context, dataStore);
        CrashReporter crashReporter = new CrashReporter();
        UsageTracker usageTracker = new UsageTracker(dataStore);
        return new ShopModule(crashReporter, usageTracker, dataStore, apiClient);
    }

    private static DataStore createDataStore(Context context) {
        File filesDir = context.getFilesDir();
        return new DataStore(filesDir, Schedulers.from(AsyncTask.SERIAL_EXECUTOR));
    }

    public static ApiClient createApiClient(Context context, DataStore dataStore) {
        File cacheDir = context.getCacheDir();
        OkHttpClient httpClient = ApiClient.createHttpClient(cacheDir);
        UserAgent userAgent = new UserAgent();
        return new ApiClient(httpClient, dataStore, userAgent);
    }

    public void inject(ShopApplication application) {
        application.setDependencies();
    }

    public void inject(MainActivity activity) {
        activity.setDependencies(crashReporter, usageTracker, dataStore, apiClient);
    }
}
