package info.mschmitt.shop.core.util;

import androidx.annotation.NonNull;
import io.reactivex.*;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias Schmitt
 */
public class RxJava2ErrorCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory wrappedFactory;

    public RxJava2ErrorCallAdapterFactory(RxJava2CallAdapterFactory wrappedFactory) {
        this.wrappedFactory = wrappedFactory;
    }

    @Override
    public CallAdapter get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = wrappedFactory.get(returnType, annotations, retrofit);
        Map<Integer, Class<? extends Exception>> map = new HashMap<>();
        for (Annotation annotation : annotations) {
            if (annotation instanceof HttpExceptionMapping.List) {
                HttpExceptionMapping.List list = (HttpExceptionMapping.List) annotation;
                for (HttpExceptionMapping mapping : list.value()) {
                    map.put(mapping.errorCode(), mapping.exceptionClass());
                }
            } else if (annotation instanceof HttpExceptionMapping) {
                HttpExceptionMapping mapping = (HttpExceptionMapping) annotation;
                map.put(mapping.errorCode(), mapping.exceptionClass());
            }
        }
        if (map.isEmpty()) {
            return callAdapter;
        }
        return new ErrorCallAdapter<>(callAdapter, map, retrofit);
    }

    private static class ErrorCallAdapter<R, S> implements CallAdapter<R, S> {
        private final CallAdapter<R, S> callAdapter;
        private final Map<Integer, Class<? extends Exception>> map;
        private final Retrofit retrofit;

        ErrorCallAdapter(CallAdapter<R, S> callAdapter, Map<Integer, Class<? extends Exception>> map,
                         Retrofit retrofit) {
            this.callAdapter = callAdapter;
            this.map = map;
            this.retrofit = retrofit;
        }

        @Override
        @NonNull
        public Type responseType() {
            return callAdapter.responseType();
        }

        @Override
        @NonNull
        public S adapt(@NonNull Call<R> call) {
            S adapted = callAdapter.adapt(call);
            if (adapted instanceof Completable) {
                Completable completable = (Completable) adapted;
                return (S) completable.onErrorResumeNext(throwable -> Completable.error(map(throwable)));
            } else if (adapted instanceof Maybe<?>) {
                Maybe<?> maybe = (Maybe<?>) adapted;
                return (S) maybe.onErrorResumeNext(throwable -> {
                    return Maybe.error(map(throwable));
                });
            } else if (adapted instanceof Single<?>) {
                Single<?> single = (Single<?>) adapted;
                return (S) single.onErrorResumeNext(throwable -> Single.error(map(throwable)));
            } else if (adapted instanceof Observable<?>) {
                Observable<?> observable = (Observable<?>) adapted;
                return (S) observable.onErrorResumeNext(throwable -> {
                    return Observable.error(map(throwable));
                });
            } else if (adapted instanceof Flowable<?>) {
                Flowable<?> flowable = (Flowable<?>) adapted;
                return (S) flowable.onErrorResumeNext(throwable -> {
                    return Flowable.error(map(throwable));
                });
            } else {
                return adapted;
            }
        }

        Throwable map(Throwable throwable) {
            if (!(throwable instanceof HttpException)) {
                return throwable;
            }
            HttpException httpException = (HttpException) throwable;
            Class<? extends Exception> exceptionClass = map.get(httpException.code());
            if (exceptionClass == null) {
                return httpException;
            }
            Response<?> response = httpException.response();
            if (response == null) {
                return throwable;
            }
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                return throwable;
            }
            Converter<ResponseBody, ? extends Exception> converter =
                    retrofit.responseBodyConverter(exceptionClass, new Annotation[]{});
            try {
                return converter.convert(errorBody);
            } catch (IOException e) {
                return e;
            }
        }
    }
}
