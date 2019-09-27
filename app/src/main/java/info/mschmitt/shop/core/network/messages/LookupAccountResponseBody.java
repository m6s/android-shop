package info.mschmitt.shop.core.network.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class LookupAccountResponseBody {
    /**
     * The account associated with the given Firebase ID token. Check below for more details.
     */
    @SerializedName("users") public List<User> users;

    public class User {
        /**
         * The uid of the current user.
         */
        @SerializedName("localId") public String localId;
        /**
         * The email of the account.
         */
        @SerializedName("email") public String email;
        /**
         * Whether or not the account's email has been verified.
         */
        @SerializedName("emailVerified") public boolean emailVerified;
        /**
         * The display name for the account.
         */
        @SerializedName("displayName") public String displayName;
        /**
         * List of all linked provider objects which contain "providerId" and "federatedId".
         */
        @SerializedName("providerUserInfo") public List<JsonObject> providerUserInfo;
        /**
         * The photo Url for the account.
         */
        @SerializedName("photoUrl") public String photoUrl;
        /**
         * Hash version of password.
         */
        @SerializedName("passwordHash") public String passwordHash;
        /**
         * The timestamp, in milliseconds, that the account password was last changed.
         */
        @SerializedName("passwordUpdatedAt") public double passwordUpdatedAt;
        /**
         * The timestamp, in seconds, which marks a boundary, before which Firebase ID token are considered revoked.
         */
        @SerializedName("validSince") public String validSince;
        /**
         * Whether the account is disabled or not.
         */
        @SerializedName("disabled") public boolean disabled;
        /**
         * The timestamp, in milliseconds, that the account last logged in at.
         */
        @SerializedName("lastLoginAt") public String lastLoginAt;
        /**
         * The timestamp, in milliseconds, that the account was created at.
         */
        @SerializedName("createdAt") public String createdAt;
        /**
         * Whether the account is authenticated by the developer.
         */
        @SerializedName("customAuth") public boolean customAuth;
    }
}
