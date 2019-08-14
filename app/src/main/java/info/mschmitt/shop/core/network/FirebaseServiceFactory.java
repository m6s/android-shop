package info.mschmitt.shop.core.network;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Matthias Schmitt
 */
public class FirebaseServiceFactory {
    public static final String API_KEY = "AIzaSyBhfQrEexLAvIBO3vd4fkwZu9i3u1KP3ek";

    public static Response addApiKeyQueryParam(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("key", API_KEY).build();
        // Request customization: add request headers
        return chain.proceed(request.newBuilder().url(url).build());
    }
}
