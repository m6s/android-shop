package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class LookupAccountRequestBody {
    /**
     * A Firebase Auth ID token for the user.
     */
    @SerializedName("idToken") public String idToken;
}
