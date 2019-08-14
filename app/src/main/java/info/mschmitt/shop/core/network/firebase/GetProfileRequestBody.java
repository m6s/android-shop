package info.mschmitt.shop.core.network.firebase;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class GetProfileRequestBody {
    /**
     * A Firebase Auth ID token for the user.
     */
    @SerializedName("idToken") public String idToken;
}
