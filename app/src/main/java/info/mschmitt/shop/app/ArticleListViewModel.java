package info.mschmitt.shop.app;

import android.app.Application;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import info.mschmitt.shop.core.util.HandleableEvent;
import info.mschmitt.shop.core.util.ViewModelUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class ArticleListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Article>> articles;
    private final MutableLiveData<Boolean> loading;
    private final MutableLiveData<HandleableEvent<Throwable>> error;
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Disposable getArticlesDisposable;

    public ArticleListViewModel(SavedStateHandle handle, Application application, CrashReporter crashReporter,
                                UsageTracker usageTracker, Database database, RestClient restClient) {
        super(application);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
        articles = handle.getLiveData("articles");
        loading = handle.getLiveData("loading", false);
        error = handle.getLiveData("error");
    }

    public static ArticleListViewModel of(Fragment fragment, CrashReporter crashReporter, UsageTracker usageTracker,
                                          Database database, RestClient restClient) {
        return ViewModelUtils.provide(fragment, ArticleListViewModel.class,
                (handle, application) -> new ArticleListViewModel(handle, application, crashReporter, usageTracker,
                        database, restClient));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public LiveData<HandleableEvent<Throwable>> getError() {
        return error;
    }

    public LiveData<List<Article>> getArticles() {
        if (articles.getValue() == null && getArticlesDisposable == null) {
            loading.setValue(true);
            getArticlesDisposable =
                    restClient.getArticles().subscribe(this::onGetArticlesSuccess, this::onGetArticlesError);
            disposables.add(getArticlesDisposable);
        }
        return articles;
    }

    private void onGetArticlesSuccess(List<Article> articles) {
        loading.postValue(false);
        this.articles.postValue(articles);
    }

    private void onGetArticlesError(Throwable throwable) {
        loading.postValue(false);
        error.postValue(new HandleableEvent<>(throwable));
        usageTracker.trackException(throwable);
    }
}
