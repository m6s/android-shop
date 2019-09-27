package info.mschmitt.shop.core.network.messages;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class UpdateAccountRequestBody {
    public static final String ATTRIBUTE_DISPLAY_NAME = "DISPLAY_NAME";
    public static final String ATTRIBUTE_PHOTO_URL = "PHOTO_URL";
    /**
     * The user's new email.
     */
    @SerializedName("email") public String email;
    /**
     * The user's new email.
     */
    @SerializedName("password") public String password;
    /**
     * A Firebase Auth ID token for the user.
     */
    @SerializedName("idToken") public String idToken;
    /**
     * User's new display name.
     */
    @SerializedName("displayName") public String displayName;
    /**
     * User's new photo url.
     */
    @SerializedName("photoUrl") public String photoUrl;
    /**
     * List of attributes to delete, {@link UpdateAccountRequestBody#ATTRIBUTE_DISPLAY_NAME} or {@link
     * UpdateAccountRequestBody#ATTRIBUTE_PHOTO_URL}. This will nullify these values.
     */
    @SerializedName("deleteAttribute") public List<String> deleteAttribute;
    /**
     * Whether or not to return an ID and refresh token.
     */
    @SerializedName("returnSecureToken") public boolean returnSecureToken;
}
