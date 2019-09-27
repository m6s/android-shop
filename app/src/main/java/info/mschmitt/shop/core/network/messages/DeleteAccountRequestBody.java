package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class DeleteAccountRequestBody {
    /**
     * The Firebase ID token of the user to delete.
     */
    @SerializedName("idToken") public String idToken;
}
