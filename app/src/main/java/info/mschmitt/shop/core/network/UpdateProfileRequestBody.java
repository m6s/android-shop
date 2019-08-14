package info.mschmitt.shop.core.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class UpdateProfileRequestBody {
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
    @SerializedName("idToken") String idToken;
    /**
     * User's new display name.
     */
    @SerializedName("displayName") String displayName;
    /**
     * User's new photo url.
     */
    @SerializedName("photoUrl") String photoUrl;
    /**
     * List of attributes to delete, {@link UpdateProfileRequestBody#ATTRIBUTE_DISPLAY_NAME} or {@link
     * UpdateProfileRequestBody#ATTRIBUTE_PHOTO_URL}. This will nullify these values.
     */
    @SerializedName("deleteAttribute") List<String> deleteAttribute;
    /**
     * Whether or not to return an ID and refresh token.
     */
    @SerializedName("returnSecureToken") boolean returnSecureToken;
}
