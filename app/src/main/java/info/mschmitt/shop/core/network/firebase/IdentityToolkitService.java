package info.mschmitt.shop.core.network.firebase;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Read <a href="https://firebase.google.com/docs/reference/rest/auth">Authentication and user management</a> and
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
    Single<SignInResponseBody> signIn(@Body SignInRequestBody body);

    /**
     * Change a user's email, change a user's password, link an email/password, or update a user's profile (display name
     * / photo URL).
     * <p>
     * {@link UpdateProfileRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:update?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"ID_TOKEN","displayName":"NAME","photoUrl":"URL","returnSecureToken":true}'
    //@formatter:on
    @POST("./accounts:update")
    @Headers("Content-Type: application/json")
    Single<UpdateProfileResponseBody> updateProfile(@Body UpdateProfileRequestBody body);

    /**
     * Get a user's data.
     * <p>
     * {@link GetProfileRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"FIREBASE_ID_TOKEN"}'
    //@formatter:on
    @POST("./accounts:lookup")
    @Headers("Content-Type: application/json")
    Single<GetProfileResponseBody> getProfile(@Body GetProfileRequestBody body);

    /**
     * Send an email verification for the current user.
     * <p>
     * {@link UpdateProfileRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"requestType":"VERIFY_EMAIL","idToken":"FIREBASE_ID_TOKEN"}'
    //@formatter:on
    @POST("./accounts:sendOobCode")
    @Headers("Content-Type: application/json")
    Single<SendEmailVerificationResponseBody> sendEmailVerification(@Body SendEmailVerificationRequestBody body);
}
