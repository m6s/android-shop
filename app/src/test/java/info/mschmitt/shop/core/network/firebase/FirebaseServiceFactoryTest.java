package info.mschmitt.shop.core.network.firebase;

import info.mschmitt.testing.EmailRandomizer;
import info.mschmitt.testing.LoggingUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Matthias Schmitt
 */
public class FirebaseServiceFactoryTest {
    private IdentityToolkitService identityToolkitService;
    private SecureTokenService secureTokenService;

    @Before
    public void setUp() {
        Logger logger = LoggingUtils.prettyPrintLogger();
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> logger.log(Level.INFO, message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();
        FirebaseServiceFactory factory = new FirebaseServiceFactory(httpClient);
        identityToolkitService = factory.createIdentityToolkitService();
        secureTokenService = factory.createSecureTokenService();
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
        SignInResponseBody responseBody = identityToolkitService.signIn(requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(email);
    }

    @Test
    public void refreshIdToken() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String refreshToken = signUp(email, "PASSWORD").refreshToken;
        RefreshIdTokenRequestBody refreshIdTokenRequestBody = new RefreshIdTokenRequestBody();
        refreshIdTokenRequestBody.refreshToken = refreshToken;
        RefreshIdTokenResponseBody responseBody =
                secureTokenService.refreshIdToken("refresh_token", refreshToken).blockingGet();
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
        UpdateProfileResponseBody responseBody = identityToolkitService.updateProfile(requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(requestBody.email);
    }

    @Test
    public void getProfile() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String password = "PASSWORD";
        SignUpResponseBody signUpResponseBody = signUp(email, password);
        GetProfileRequestBody requestBody = new GetProfileRequestBody();
        requestBody.idToken = signUpResponseBody.idToken;
        GetProfileResponseBody responseBody = identityToolkitService.getProfile(requestBody).blockingGet();
        assertThat(responseBody.users).hasSize(1);
    }

    @Test
    public void sendEmailVerification() {
        String email = EmailRandomizer.createEmail("user", "example.com");
        String password = "PASSWORD";
        SignUpResponseBody signUpResponseBody = signUp(email, password);
        SendEmailVerificationRequestBody requestBody = new SendEmailVerificationRequestBody();
        requestBody.idToken = signUpResponseBody.idToken;
        SendEmailVerificationResponseBody responseBody =
                identityToolkitService.sendEmailVerification(requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(email);
    }

    private SignUpResponseBody signUp(String email, String password) {
        SignUpRequestBody requestBody = new SignUpRequestBody();
        requestBody.email = email;
        requestBody.password = password;
        SignUpResponseBody responseBody = identityToolkitService.signUp(requestBody).blockingGet();
        assertThat(responseBody.email).isEqualTo(requestBody.email);
        return responseBody;
    }
}
