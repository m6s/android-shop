package info.mschmitt.shop.core.network.firebase;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class SignUpResponseBody {
    /**
     * A Firebase Auth ID token for the newly created user.
     */
    @SerializedName("idToken") public String idToken;
    /**
     * The email for the newly created user or empty if anonymous sign-up.
     */
    @SerializedName("email") public String email;
    /**
     * A Firebase Auth refresh token for the newly created user.
     */
    @SerializedName("refreshToken") public String refreshToken;
    /**
     * The number of seconds in which the ID token expires.
     */
    @SerializedName("expiresIn") public long expiresIn;
    /**
     * The uid of the newly created user.
     */
    @SerializedName("localId") public String localId;
    /**
     * Not specified. Will be "identitytoolkit#SignupNewUserResponse".
     */
    @SerializedName("kind") public String kind;
}
