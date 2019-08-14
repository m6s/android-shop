package info.mschmitt.shop.core.network.firebase;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class RefreshIdTokenRequestBody {
    /**
     * The refresh token's grant type, always "refresh_token".
     */
    @SerializedName("grant_type") public final String grantType = "refresh_token";
    /**
     * A Firebase Auth refresh token.
     */
    @SerializedName("refresh_token") public String refreshToken;
}
