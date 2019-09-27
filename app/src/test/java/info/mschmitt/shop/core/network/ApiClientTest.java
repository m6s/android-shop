package info.mschmitt.shop.core.network;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import info.mschmitt.shop.core.storage.Article;
import info.mschmitt.shop.core.storage.DataStore;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Matthias Schmitt
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = Application.class)
public class ApiClientTest {
    private ApiClient apiClient;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        DataStore dataStore = new DataStore(context.getFilesDir(), Schedulers.trampoline());
        apiClient = new ApiClient(context.getCacheDir(), dataStore, "");
    }

    @Test
    public void getArticles() {
        List<Article> articles = apiClient.getArticles().blockingGet();
        assertThat(articles).isNotEmpty();
    }
}
