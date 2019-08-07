package info.mschmitt.shop.app;

import info.mschmitt.shop.core.database.Config;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.ApiService;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

/**
 * @author Matthias Schmitt
 */
public class TestApplication extends ShopApplication {
    public static Database database;
    private ShopModule module;

    private static Database mockDatabase() {
        Config config = new Config();
        config.backend = "testing";
        Database database = Mockito.mock(Database.class);
        Mockito.doAnswer(invokation -> config.backend).when(database).getBackend();
        Mockito.doAnswer(invocation -> config.backend = invocation.getArgument(0))
                .when(database)
                .setBackend(Mockito.anyString());
        Mockito.doAnswer(invokation -> config.developerMode).when(database).getDeveloperModeEnabled();
        Mockito.doAnswer(invocation -> config.developerMode = invocation.getArgument(0))
                .when(database)
                .setDeveloperModeEnabled(Mockito.anyBoolean());
        Mockito.doAnswer(invokation -> config.email).when(database).getEmail();
        Mockito.doAnswer(invokation -> config.token).when(database).getToken();
        Mockito.doAnswer(invocation -> config.token = invocation.getArgument(0))
                .when(database)
                .setToken(Mockito.anyString());
        Mockito.doAnswer(invocation -> config.tracking).when(database).getTrackingEnabled();
        Mockito.doAnswer(invocation -> config.tracking = invocation.getArgument(0))
                .when(database)
                .setTrackingEnabled(Mockito.anyBoolean());
        return database;
    }

    @NotNull
    @Override
    public ShopModule getModule() {
        if (module == null) {
            TestApplication.database = mockDatabase();
            CrashReporter crashReporter = new CrashReporter();
            UsageTracker usageTracker = new UsageTracker(TestApplication.database);
            RestClient restClient = new RestClient(new ApiService() {});
            module = new ShopModule(crashReporter, usageTracker, TestApplication.database, restClient);
        }
        return module;
    }
}
