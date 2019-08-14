package info.mschmitt.shop.core.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class RefreshIdTokenResponseBody {
    /**
     * The number of seconds in which the ID token expires.
     */
    @SerializedName("expires_in") long expiresIn;
    /**
     * The type of the refresh token, always "Bearer".
     */
    @SerializedName("token_type") String tokenType;
    /**
     * The Firebase Auth refresh token provided in the request or a new refresh token.
     */
    @SerializedName("refresh_token") String refreshToken;
    /**
     * A Firebase Auth ID token.
     */
    @SerializedName("id_token") String idToken;
    /**
     * The uid corresponding to the provided ID token.
     */
    @SerializedName("user_id") String userId;
    /**
     * Your Firebase project ID.
     */
    @SerializedName("project_id") String projectId;
    /**
     * Undocumented. Seems to be the same as {@link RefreshIdTokenResponseBody#idToken}.
     */
    @SerializedName("access_token") String accessToken;
}
