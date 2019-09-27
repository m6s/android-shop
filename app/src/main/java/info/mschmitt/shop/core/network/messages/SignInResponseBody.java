package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class SignInResponseBody {
    /**
     * A Firebase Auth ID token for the authenticated user.
     */
    @SerializedName("idToken") public String idToken;
    /**
     * The email for the authenticated user.
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
     * The uid of the authenticated user.
     */
    @SerializedName("localId") public String localId;
    /**
     * Whether the email is for an existing account.
     */
    @SerializedName("registered") public String registered;
    /**
     * Not specified
     */
    @SerializedName("kind") public String kind;
}
