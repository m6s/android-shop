package info.mschmitt.shop.core.network;

import info.mschmitt.shop.core.database.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Matthias Schmitt
 */
public class RestClientTest {
    private RestClient restClient;

    @Before
    public void setUp() {
        restClient = new RestClient(new ApiService() {});
    }

    @Test
    public void getArticles() {
        List<Article> articles = restClient.getArticles().blockingGet();
        assertThat(articles).isNotEmpty();
    }
}
