package info.mschmitt.shop.core.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class GetProfileRequestBody {
    /**
     * A Firebase Auth ID token for the user.
     */
    @SerializedName("idToken") String idToken;
}
