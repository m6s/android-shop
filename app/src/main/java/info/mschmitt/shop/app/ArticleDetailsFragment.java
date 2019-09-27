package info.mschmitt.shop.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import info.mschmitt.shop.app.databinding.FragmentArticleDetailsBinding;
import info.mschmitt.shop.core.CrashReporter;
import info.mschmitt.shop.core.UsageTracker;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.storage.Article;
import info.mschmitt.shop.core.storage.DataStore;

/**
 * @author Matthias Schmitt
 */
public class ArticleDetailsFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final DataStore dataStore;
    private final ApiClient apiClient;
    private FragmentArticleDetailsBinding binding;
    private Article article;

    public ArticleDetailsFragment(CrashReporter crashReporter, UsageTracker usageTracker,
                                  DataStore dataStore,
                                  ApiClient apiClient) {
        super(R.layout.fragment_article_details);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.dataStore = dataStore;
        this.apiClient = apiClient;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = ArticleDetailsFragmentArgs.fromBundle(requireArguments()).getArticle();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleDetailsBinding.bind(view);
        binding.nameTextView.setText(article.name);
    }
}
