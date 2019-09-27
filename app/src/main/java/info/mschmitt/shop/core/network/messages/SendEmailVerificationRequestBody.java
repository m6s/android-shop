package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class SendEmailVerificationRequestBody {
    /**
     * The type of confirmation code to send. Should always be "VERIFY_EMAIL".
     */
    @SerializedName("requestType") public final String requestType = "VERIFY_EMAIL";
    /**
     * The Firebase ID token of the user to verify.
     */
    @SerializedName("idToken") public String idToken;
}
