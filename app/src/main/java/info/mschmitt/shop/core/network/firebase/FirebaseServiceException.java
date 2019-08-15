package info.mschmitt.shop.core.network.firebase;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class FirebaseServiceException extends Exception {
    /**
     * The email address is already in use by another account.
     */
    public static final String EMAIL_EXISTS = "EMAIL_EXISTS";
    /**
     * Password sign-in is disabled for this project. Anonymous user sign-in is disabled for this project.
     */
    public static final String OPERATION_NOT_ALLOWED = "OPERATION_NOT_ALLOWED";
    /**
     * We have blocked all requests from this device due to unusual activity. Try again later.
     */
    public static final String TOO_MANY_ATTEMPTS_TRY_LATER = "TOO_MANY_ATTEMPTS_TRY_LATER";
    /**
     * There is no user record corresponding to this identifier. The user may have been deleted.
     */
    public static final String EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND";
    /**
     * The password is invalid or the user does not have a password.
     */
    public static final String INVALID_PASSWORD = "INVALID_PASSWORD";
    /**
     * The user account has been disabled by an administrator.
     */
    public static final String USER_DISABLED = "USER_DISABLED";
    /**
     * The user's credential is no longer valid. The user must sign in again.
     */
    public static final String INVALID_ID_TOKEN = "INVALID_ID_TOKEN";
    /**
     * The password must be 6 characters long or more.
     */
    public static final String WEAK_PASSWORD = "WEAK_PASSWORD";
    /**
     * There is no user record corresponding to this identifier. The user may have been deleted.
     * The user corresponding to the refresh token was not found. It is likely the user was deleted.
     */
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    /**
     * The user's credential is no longer valid. The user must sign in again.
     */
    public static final String CREDENTIAL_TOO_OLD_LOGIN_AGAIN = "CREDENTIAL_TOO_OLD_LOGIN_AGAIN";
    /**
     * The user's credential is no longer valid. The user must sign in again.
     */
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
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
    @SerializedName("error") public Error error;

    public static class Error {
        @SerializedName("code") public int code;
        @SerializedName("message") public String message;
        @SerializedName("domain") public String domain;
        @SerializedName("reason") public String reason;
        @SerializedName("errors") public List<Error> errors;
    }
}
