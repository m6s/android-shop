package info.mschmitt.shop.core.network;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Matthias Schmitt
 */
public interface SecureTokenService {
    String BASE_URL = "https://securetoken.googleapis.com/v1/";
    String API_KEY = "AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek";

    //@formatter:off
    //curl 'https://securetoken.googleapis.com/v1/token?key=AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek' -H 'Content-Type: application/x-www-form-urlencoded' --data 'grant_type=refresh_token&refresh_token=xyz'
    //@formatter:on
    @POST("./token")
    Single<RefreshIdTokenResponseBody> refreshIdToken(@Query("key") String key, @Query("grant_type") String grantType,
                                                      @Query("refresh_token") String refreshToken);
}
