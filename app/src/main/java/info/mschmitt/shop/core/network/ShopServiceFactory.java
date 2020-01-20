package info.mschmitt.shop.core.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;

import info.mschmitt.shop.core.network.messages.RefreshIdTokenResponseBody;
import info.mschmitt.shop.core.storage.DataStore;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Matthias Schmitt
 */
public class ShopServiceFactory {
    private static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";
    private final Gson gson;
    private final OkHttpClient httpClient;
    private final SecureTokenService secureTokenService;
    private final DataStore dataStore;
    private final UserAgent userAgent;

    public ShopServiceFactory(OkHttpClient httpClient, SecureTokenService secureTokenService,
                              DataStore dataStore, UserAgent userAgent) {
        this.httpClient = httpClient;
        this.secureTokenService = secureTokenService;
        this.dataStore = dataStore;
        this.userAgent = userAgent;
        gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    }

    public ShopService createShopService() {
        OkHttpClient httpClient = this.httpClient
                .newBuilder()
                .authenticator(this::authenticate)
                .addInterceptor(this::addHeaders)
                .build();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .baseUrl(ShopService.BASE_URL)
                .build()
                .create(ShopService.class);
    }

    private Request authenticate(Route route, Response response) throws IOException {
        String refreshToken = dataStore.getRefreshToken();
        RefreshIdTokenResponseBody responseBody;
        try {
            responseBody = secureTokenService
                    .refreshIdToken(SecureTokenService.GRANT_TYPE_REFRESH_TOKEN, refreshToken)
                    .blockingGet();
        } catch (RuntimeException ex) {
            if (ex.getCause() instanceof IOException) {
                throw (IOException) ex.getCause();
            } else {
                throw ex;
            }
        }
        dataStore.setAccessToken(responseBody.accessToken);
        Request.Builder builder = response.request().newBuilder();
        addAuthorizationHeader(builder, responseBody.accessToken);
        return builder.build();
    }

    private void addAuthorizationHeader(Request.Builder builder, String idToken) {
        String authorization = String.format("Bearer %s", idToken);
        builder.header("Authorization", authorization);
    }

    private Response addHeaders(Interceptor.Chain chain) throws IOException {
        String language = Locale.getDefault().getLanguage();
        Request.Builder builder = chain
                .request()
                .newBuilder()
                .addHeader("User-Agent", userAgent.header)
                .addHeader("Accept-Language", language);
        String token = dataStore.getAccessToken();
        if (token != null && !token.isEmpty()) {
            String authorization = String.format("Bearer %s", token);
            builder.addHeader("Authorization", authorization);
        }
        return chain.proceed(builder.build());
    }
}
