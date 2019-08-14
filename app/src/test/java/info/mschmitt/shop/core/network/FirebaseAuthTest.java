package info.mschmitt.shop.core.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Matthias Schmitt
 */
public class FirebaseAuthTest {
    private IdentityToolkitService identityToolkitService;
    private SecureTokenService secureTokenService;

    @Before
    public void setUp() {
        Logger logger = LoggingUtils.prettyPrintLogger();
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> logger.log(Level.INFO, message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();
        Gson gson = new GsonBuilder().create();
        OkHttpClient identityToolkitHttpClient =
                httpClient.newBuilder().addInterceptor(this::addJsonContentTypeHeader).build();
        identityToolkitService = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(identityToolkitHttpClient)
                .baseUrl(IdentityToolkitService.BASE_URL)
                .build()
                .create(IdentityToolkitService.class);
        OkHttpClient secureTokenHttpClient =
                httpClient.newBuilder().addInterceptor(this::addUrlEncodedContentTypeHeader).build();
        secureTokenService = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(secureTokenHttpClient)
                .baseUrl(SecureTokenService.BASE_URL)
                .build()
                .create(SecureTokenService.class);
    }

    private Response addJsonContentTypeHeader(Interceptor.Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder().addHeader("Content-Type", "application/json").build());
    }

    private Response addUrlEncodedContentTypeHeader(Interceptor.Chain chain) throws IOException {
        return chain.proceed(
                chain.request().newBuilder().addHeader("Content-Type", "application/x-www-form-urlencoded").build());
    }

    @Test
    public void signUpWithEmailAndPassword() {
        signUp(EmailRandomizer.createEmail("user", "example.com"), "PASSWORD");
    }

    @Test
    public void signUpAnonymously() {
        signUp(null, null);
    }

    @Test
    public void signInWithPassword() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String password = "PASSWORD";
        signUp(email, password);
        SignInRequestBody requestBody = new SignInRequestBody();
        requestBody.email = email;
        requestBody.password = password;
        SignInResponseBody responseBody =
                identityToolkitService.signInWithPassword(IdentityToolkitService.API_KEY, requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(email);
    }

    @Test
    public void refreshIdToken() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String refreshToken = signUp(email, "PASSWORD").refreshToken;
        RefreshIdTokenRequestBody refreshIdTokenRequestBody = new RefreshIdTokenRequestBody();
        refreshIdTokenRequestBody.refreshToken = refreshToken;
        RefreshIdTokenResponseBody responseBody = secureTokenService
                .refreshIdToken(SecureTokenService.API_KEY, "refresh_token", refreshToken)
                .blockingGet();
        assertThat(responseBody.expiresIn).isGreaterThan(0L);
        assertThat(responseBody.tokenType).isEqualTo("Bearer");
    }

    @Test
    public void updateProfile() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String password = "PASSWORD";
        SignUpResponseBody signUpResponseBody = signUp(email, password);
        UpdateProfileRequestBody requestBody = new UpdateProfileRequestBody();
        requestBody.idToken = signUpResponseBody.idToken;
        requestBody.email = EmailRandomizer.createEmail("user", "example.com");
        UpdateProfileResponseBody responseBody =
                identityToolkitService.updateProfile(IdentityToolkitService.API_KEY, requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(requestBody.email);
    }

    private SignUpResponseBody signUp(String email, String password) {
        SignUpRequestBody requestBody = new SignUpRequestBody();
        requestBody.email = email;
        requestBody.password = password;
        SignUpResponseBody responseBody =
                identityToolkitService.signUp(IdentityToolkitService.API_KEY, requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(requestBody.email);
        return responseBody;
    }
}
