package info.mschmitt.shop.core.network.firebase;

/**
 * @author Matthias Schmitt
 */
public class SecureTokenServiceException {
    /**
     * The user's credential is no longer valid. The user must sign in again.
     */
    public final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    /**
     * The user account has been disabled by an administrator.
     */
    public final String USER_DISABLED = "USER_DISABLED";
    /**
     * The user corresponding to the refresh token was not found. It is likely the user was deleted.
     */
    public final String USER_NOT_FOUND = "USER_NOT_FOUND";
    /**
     * TODO Broken Firebase documentation API key not valid. Please pass a valid API key. (invalid API key provided)
     */
    public final String INVALID_API_KEY = "INVALID_API_KEY";
    /**
     * An invalid refresh token is provided.
     */
    public final String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
    /**
     * TODO Broken Firebase documentation Invalid JSON payload received. Unknown name \"refresh_tokens\": Cannot bind
     * query parameter. Field 'refresh_tokens' could not be found in request message.
     */
    public final String INVALID_PAYLOAD = "INVALID_PAYLOAD";
    /**
     * the grant type specified is invalid.
     */
    public final String INVALID_GRANT_TYPE = "INVALID_GRANT_TYPE";
    /**
     * no refresh token provided.
     */
    public final String MISSING_REFRESH_TOKEN = "MISSING_REFRESH_TOKEN";
}
