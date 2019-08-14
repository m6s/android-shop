package info.mschmitt.shop.app;

import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.database.Config;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class TestApplication extends ShopApplication {
    public static Database database;
    public static ApiClient apiClient;
    private ShopModule module;

    private static Database mockDatabase() {
        Config config = new Config();
        config.backend = "testing";
        Database database = Mockito.mock(Database.class);
        Mockito.doAnswer(invocation -> config.backend).when(database).getBackend();
        Mockito.doAnswer(invocation -> config.backend = invocation.getArgument(0))
                .when(database)
                .setBackend(Mockito.anyString());
        Mockito.doAnswer(invocation -> config.developerMode).when(database).getDeveloperModeEnabled();
        Mockito.doAnswer(invocation -> config.developerMode = invocation.getArgument(0))
                .when(database)
                .setDeveloperModeEnabled(Mockito.anyBoolean());
        Mockito.doAnswer(invocation -> config.email).when(database).getEmail();
        Mockito.doAnswer(invocation -> config.token).when(database).getToken();
        Mockito.doAnswer(invocation -> config.token = invocation.getArgument(0))
                .when(database)
                .setToken(Mockito.anyString());
        Mockito.doAnswer(invocation -> config.tracking).when(database).getTrackingEnabled();
        Mockito.doAnswer(invocation -> config.tracking = invocation.getArgument(0))
                .when(database)
                .setTrackingEnabled(Mockito.anyBoolean());
        return database;
    }

    private ApiClient mockApiClient() {
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.name = "Article A";
        articles.add(article);
        article = new Article();
        article.name = "Article B";
        articles.add(article);
        article = new Article();
        article.name = "Article C";
        articles.add(article);
        ApiClient apiClient = Mockito.mock(ApiClient.class);
        Mockito.doAnswer(invocation -> Single.just(articles)).when(apiClient).getArticles();
        return apiClient;
    }

    @NotNull
    @Override
    public ShopModule getModule() {
        if (module == null) {
            TestApplication.database = mockDatabase();
            CrashReporter crashReporter = new CrashReporter();
            UsageTracker usageTracker = new UsageTracker(TestApplication.database);
            TestApplication.apiClient = mockApiClient();
            module = new ShopModule(crashReporter, usageTracker, TestApplication.database, apiClient);
        }
        return module;
    }
}
