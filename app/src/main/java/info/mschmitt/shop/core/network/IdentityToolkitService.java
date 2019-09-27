package info.mschmitt.shop.core.network;

import info.mschmitt.shop.core.network.messages.DeleteAccountRequestBody;
import info.mschmitt.shop.core.network.messages.LookupAccountRequestBody;
import info.mschmitt.shop.core.network.messages.LookupAccountResponseBody;
import info.mschmitt.shop.core.network.messages.SendEmailVerificationRequestBody;
import info.mschmitt.shop.core.network.messages.SendEmailVerificationResponseBody;
import info.mschmitt.shop.core.network.messages.SignInRequestBody;
import info.mschmitt.shop.core.network.messages.SignInResponseBody;
import info.mschmitt.shop.core.network.messages.SignUpRequestBody;
import info.mschmitt.shop.core.network.messages.SignUpResponseBody;
import info.mschmitt.shop.core.network.messages.UpdateAccountRequestBody;
import info.mschmitt.shop.core.network.messages.UpdateAccountResponseBody;
import info.mschmitt.shop.core.util.HttpExceptionMapping;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Read <a href="https://firebase.google.com/docs/reference/rest/auth">Authentication and user
 * management</a> and
 * <a href="https://stackoverflow.com/q/54525554">Restrict API key usage</a>
 *
 * @author Matthias Schmitt
 */
public interface IdentityToolkitService {
    String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";

    /**
     * Create a new email and password user or sign in a user anonymously.
     * <p>
     * A successful request is indicated by a 200 OK HTTP status code. The response contains the Firebase ID token and
     * refresh token associated with the new account or anonymous user.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"email":"user@example.com","password":"PASSWORD","returnSecureToken":true}'
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"returnSecureToken":true}'
    //@formatter:on
    @POST("./accounts:signUp")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Single<SignUpResponseBody> signUp(@Body SignUpRequestBody body);

    /**
     * Sign in a user with an email and password.
     * <p>
     * A successful request is indicated by a 200 OK HTTP status code. The response contains the Firebase ID token and
     * refresh token associated with the existing email/password account.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"email":"user@example.com","password":"PASSWORD","returnSecureToken":true}'
    //@formatter:on
    @POST("./accounts:signInWithPassword")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Single<SignInResponseBody> signIn(@Body SignInRequestBody body);

    /**
     * Change a user's email, change a user's password, link an email/password, or update a user's profile (display name
     * / photo URL).
     * <p>
     * {@link UpdateAccountRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:update?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"ID_TOKEN","displayName":"NAME","photoUrl":"URL","returnSecureToken":true}'
    //@formatter:on
    @POST("./accounts:update")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Single<UpdateAccountResponseBody> updateAccount(@Body UpdateAccountRequestBody body);

    /**
     * Get a user's data.
     * <p>
     * {@link LookupAccountRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"FIREBASE_ID_TOKEN"}'
    //@formatter:on
    @POST("./accounts:lookup")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Single<LookupAccountResponseBody> lookupAccount(@Body LookupAccountRequestBody body);

    /**
     * Send an email verification for the current user.
     * <p>
     * {@link SendEmailVerificationRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"requestType":"VERIFY_EMAIL","idToken":"FIREBASE_ID_TOKEN"}'
    //@formatter:on
    @POST("./accounts:sendOobCode")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Single<SendEmailVerificationResponseBody> sendEmailVerification(@Body SendEmailVerificationRequestBody body);

    /**
     * Delete current user.
     * <p>
     * {@link DeleteAccountRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:delete?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"FIREBASE_ID_TOKEN"}'
    //@formatter:on
    @POST("./accounts:delete")
    @Headers("Content-Type: application/json")
    @HttpExceptionMapping(errorCode = 400, exceptionClass = FirebaseServiceException.class)
    Completable deleteAccount(@Body DeleteAccountRequestBody body);
}
