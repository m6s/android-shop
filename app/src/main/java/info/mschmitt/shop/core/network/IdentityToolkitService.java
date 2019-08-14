package info.mschmitt.shop.core.network;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Read <a href="https://firebase.google.com/docs/reference/rest/auth">Authentication and user management</a> and
 * <a href="https://stackoverflow.com/q/54525554">Restrict API key usage</a>
 *
 * @author Matthias Schmitt
 */
public interface IdentityToolkitService {
    String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";
    String API_KEY = "AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek";

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
    Single<SignUpResponseBody> signUp(@Query("key") String key, @Body SignUpRequestBody body);

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
    Single<SignInResponseBody> signInWithPassword(@Query("key") String key, @Body SignInRequestBody body);

    /**
     * Change a user's email, change a user's password, or update a user's profile (display name / photo URL).
     * <p>
     * {@link UpdateProfileRequestBody#idToken} required.
     */
    //@formatter:off
    //curl 'https://identitytoolkit.googleapis.com/v1/accounts:update?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/json' --data-binary '{"idToken":"ID_TOKEN","displayName":"NAME","photoUrl":"URL","returnSecureToken":true}'
    //@formatter:on
    @POST("./accounts:update")
    Single<UpdateProfileResponseBody> updateProfile(@Query("key") String key, @Body UpdateProfileRequestBody body);
}
