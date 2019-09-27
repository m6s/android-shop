package info.mschmitt.shop.app;

import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import info.mschmitt.shop.core.CrashReporter;
import info.mschmitt.shop.core.UsageTracker;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.storage.Article;
import info.mschmitt.shop.core.storage.Config;
import info.mschmitt.shop.core.storage.DataStore;
import io.reactivex.Single;

/**
 * @author Matthias Schmitt
 */
public class TestApplication extends ShopApplication {
    public static DataStore dataStore;
    public static ApiClient apiClient;
    private ShopModule module;

    private static DataStore mockDatabase() {
        Config config = new Config();
        config.backend = Config.BACKEND_TESTING;
        DataStore dataStore = Mockito.mock(DataStore.class);
        Mockito.doAnswer(invocation -> config.backend).when(dataStore).getBackend();
        Mockito.doAnswer(invocation -> config.backend = invocation.getArgument(0)).when(dataStore)
                .setBackend(Mockito.anyString());
        Mockito
                .doAnswer(invocation -> config.developerMode)
                .when(dataStore)
                .getDeveloperModeEnabled();
        Mockito.doAnswer(invocation -> config.developerMode = invocation.getArgument(0))
                .when(dataStore)
                .setDeveloperModeEnabled(Mockito.anyBoolean());
        Mockito.doAnswer(invocation -> config.email).when(dataStore).getEmail();
        Mockito
                .doAnswer(invocation -> config.testingProfile.accessToken)
                .when(dataStore)
                .getAccessToken();
        Mockito
                .doAnswer(
                        invocation -> config.testingProfile.accessToken = invocation.getArgument(0))
                .when(dataStore)
                .setAccessToken(Mockito.anyString());
        Mockito.doAnswer(invocation -> config.tracking).when(dataStore).getTrackingEnabled();
        Mockito.doAnswer(invocation -> config.tracking = invocation.getArgument(0)).when(dataStore)
                .setTrackingEnabled(Mockito.anyBoolean());
        return dataStore;
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
            TestApplication.dataStore = mockDatabase();
            CrashReporter crashReporter = new CrashReporter();
            UsageTracker usageTracker = new UsageTracker(TestApplication.dataStore);
            TestApplication.apiClient = mockApiClient();
            module = new ShopModule(crashReporter, usageTracker, TestApplication.dataStore,
                    apiClient);
        }
        return module;
    }
}
