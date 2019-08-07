package info.mschmitt.shop.core.network;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.mschmitt.shop.app.BuildConfig;
import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.database.Database;
import io.reactivex.Single;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthias Schmitt
 */
public class RestClient {
    private static final String BASE_URL = "https://example.com/api/";
    private static final int MBYTE = 1024 * 1024;
    private static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";
    private final ApiService apiService;
    private final Database database;
    private final String userAgent;

    public RestClient(ApiService apiService) {
        this.apiService = apiService;
        database = null;
        userAgent = null;
    }

    public RestClient(File cacheDir, Database database, String userAgent) {
        this.database = database;
        this.userAgent = userAgent;
        Cache cache = new Cache(cacheDir, 10 * MBYTE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(loggingInterceptor);
        }
        // TODO Use this httpClient for initial login/re-auth
        OkHttpClient httpClient = builder.build();
        // Use this httpClient for authenticated communication
        httpClient = httpClient.newBuilder().authenticator(this::authenticate).addInterceptor(this::addHeaders).build();
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Single<List<Article>> getArticles() {
        // TODO Fetch from network using apiService. Adapt in/out parameters and propagate HTTP errors to throwables.
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
        return Single.just(articles).delay(2, TimeUnit.SECONDS);
    }

    private Request authenticate(Route route, Response response) {
        database.setToken(null);
        return null;
    }

    private Response addHeaders(Interceptor.Chain chain) throws IOException {
        String language = Locale.getDefault().getLanguage();
        Request.Builder builder =
                chain.request().newBuilder().addHeader("User-Agent", userAgent).addHeader("Accept-Language", language);
        String token = database.getToken();
        if (!TextUtils.isEmpty(token)) {
            String authorization = String.format("Bearer %s", token);
            builder.addHeader("Authorization", authorization);
        }
        return chain.proceed(builder.build());
    }
}
