package info.mschmitt.shop.core.network;

import info.mschmitt.shop.core.database.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Matthias Schmitt
 */
public class ApiClientTest {
    private ApiClient apiClient;

    @Before
    public void setUp() {
        apiClient = new ApiClient(new ShopService() {});
    }

    @Test
    public void getArticles() {
        List<Article> articles = apiClient.getArticles().blockingGet();
        assertThat(articles).isNotEmpty();
    }
}
