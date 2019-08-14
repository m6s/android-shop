package info.mschmitt.shop.core.network;

import androidx.annotation.NonNull;
import io.reactivex.*;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Matthias Schmitt
 */
public class RxJava2ErrorCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory wrappedFactory;
    private final ErrorMapper errorMapper;

    public RxJava2ErrorCallAdapterFactory(RxJava2CallAdapterFactory wrappedFactory, ErrorMapper errorMapper) {
        this.wrappedFactory = wrappedFactory;
        this.errorMapper = errorMapper;
    }

    @Override
    @NonNull
    public CallAdapter get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = wrappedFactory.get(returnType, annotations, retrofit);
        return new CallAdapterWrapper<>(callAdapter, errorMapper);
    }

    public interface ErrorMapper {
        Throwable map(Throwable throwable);
    }

    private static class CallAdapterWrapper<R, S> implements CallAdapter<R, S> {
        private final CallAdapter<R, S> callAdapter;
        private final ErrorMapper errorMapper;

        CallAdapterWrapper(CallAdapter<R, S> callAdapter, ErrorMapper errorMapper) {
            this.callAdapter = callAdapter;
            this.errorMapper = errorMapper;
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
                return (S) completable.onErrorResumeNext(throwable -> Completable.error(errorMapper.map(throwable)));
            } else if (adapted instanceof Maybe<?>) {
                Maybe<?> maybe = (Maybe<?>) adapted;
                return (S) maybe.onErrorResumeNext(throwable -> {
                    return Maybe.error(errorMapper.map(throwable));
                });
            } else if (adapted instanceof Single<?>) {
                Single<?> single = (Single<?>) adapted;
                return (S) single.onErrorResumeNext(throwable -> Single.error(errorMapper.map(throwable)));
            } else if (adapted instanceof Observable<?>) {
                Observable<?> observable = (Observable<?>) adapted;
                return (S) observable.onErrorResumeNext(throwable -> {
                    return Observable.error(errorMapper.map(throwable));
                });
            } else if (adapted instanceof Flowable<?>) {
                Flowable<?> flowable = (Flowable<?>) adapted;
                return (S) flowable.onErrorResumeNext(throwable -> {
                    return Flowable.error(errorMapper.map(throwable));
                });
            } else {
                return adapted;
            }
        }
    }
}
