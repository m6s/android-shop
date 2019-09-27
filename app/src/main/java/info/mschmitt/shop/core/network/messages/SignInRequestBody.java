package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

/**
 * @author Matthias Schmitt
 */
public class SignInRequestBody {
    /**
     * Whether or not to return an ID and refresh token. Should always be true.
     */
    @SerializedName("returnSecureToken") public final boolean returnSecureToken = true;
    /**
     * The email for the user to create.
     */
    @SerializedName("email") public String email;
    /**
     * The password for the user to create.
     */
    @SerializedName("password") public String password;
}
