package info.mschmitt.shop.core.network.firebase;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class UpdateAccountResponseBody {
    /**
     * The uid of the current user.
     */
    @SerializedName("localId") public String localId;
    /**
     * User's email address.
     */
    @SerializedName("email") public String email;
    /**
     * User's new display name.
     */
    @SerializedName("displayName") public String displayName;
    /**
     * User's new photo url.
     */
    @SerializedName("photoUrl") public String photoUrl;
    /**
     * Hash version of password.
     */
    @SerializedName("passwordHash") public String passwordHash;
    /**
     * List of all linked provider objects which contain "providerId" and "federatedId".
     */
    @SerializedName("providerUserInfo") public List<JsonObject> providerUserInfo;
    /**
     * New Firebase Auth ID token for user.
     */
    @SerializedName("idToken") public String idToken;
    /**
     * A Firebase Auth refresh token.
     */
    @SerializedName("refreshToken") public String refreshToken;
    /**
     * The number of seconds in which the ID token expires.
     */
    @SerializedName("expiresIn") public String expiresIn;
}
