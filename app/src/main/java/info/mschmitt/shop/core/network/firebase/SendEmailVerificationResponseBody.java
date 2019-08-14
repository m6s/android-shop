package info.mschmitt.shop.core.network.firebase;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class SendEmailVerificationResponseBody {
    /**
     * The email of the account.
     */
    @SerializedName("email") public String email;
}
