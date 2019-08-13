package info.mschmitt.shop.app;

import android.app.Application;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import info.mschmitt.shop.core.util.HandleableEvent;
import info.mschmitt.shop.core.util.ViewModelUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class ArticleListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Article>> articles;
    private final MutableLiveData<Boolean> loading;
    private final MutableLiveData<HandleableEvent<Throwable>> errorEvent;
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final ApiClient apiClient;
    private Disposable getArticlesDisposable = Disposables.empty();

    public ArticleListViewModel(SavedStateHandle handle, Application application, CrashReporter crashReporter,
                                UsageTracker usageTracker, Database database, ApiClient apiClient) {
        super(application);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.apiClient = apiClient;
        articles = handle.getLiveData("articles");
        loading = handle.getLiveData("loading", false);
        errorEvent = handle.getLiveData("errorEvent");
    }

    public static ArticleListViewModel of(Fragment fragment, CrashReporter crashReporter, UsageTracker usageTracker,
                                          Database database, ApiClient apiClient) {
        return ViewModelUtils.provide(fragment, ArticleListViewModel.class,
                (handle, application) -> new ArticleListViewModel(handle, application, crashReporter, usageTracker,
                        database, apiClient));
    }

    @Override
    protected void onCleared() {
        getArticlesDisposable.dispose();
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public LiveData<HandleableEvent<Throwable>> getErrorEvent() {
        return errorEvent;
    }

    public LiveData<List<Article>> getArticles() {
        if (articles.getValue() == null) {
            loading.setValue(true);
            getArticlesDisposable =
                    apiClient.getArticles().subscribe(this::onGetArticlesSuccess, this::onGetArticlesError);
        }
        return articles;
    }

    private void onGetArticlesSuccess(List<Article> articles) {
        loading.postValue(false);
        this.articles.postValue(articles);
    }

    private void onGetArticlesError(Throwable throwable) {
        loading.postValue(false);
        errorEvent.postValue(new HandleableEvent<>(throwable));
        crashReporter.reportException(throwable);
    }
}
