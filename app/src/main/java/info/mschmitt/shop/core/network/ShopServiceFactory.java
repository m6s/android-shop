package info.mschmitt.shop.core.network;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.mschmitt.shop.core.database.Database;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Matthias Schmitt
 */
public class ShopServiceFactory {
    private static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";
    private final Gson gson;
    private final OkHttpClient httpClient;
    private final Database database;
    private final String userAgent;

    public ShopServiceFactory(OkHttpClient httpClient, Database database, String userAgent) {
        this.httpClient = httpClient;
        this.database = database;
        this.userAgent = userAgent;
        gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    }

    public ShopService createShopService() {
        OkHttpClient httpClient =
                this.httpClient.newBuilder().authenticator(this::authenticate).addInterceptor(this::addHeaders).build();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .baseUrl(ShopService.BASE_URL)
                .build()
                .create(ShopService.class);
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
