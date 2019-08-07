package info.mschmitt.shop.app;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.mschmitt.shop.app.databinding.FragmentArticleListBinding;
import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;
import info.mschmitt.shop.core.util.HandleableEvent;

import java.util.List;

/**
 * @author Matthias Schmitt
 */
public class ArticleListFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;
    private FragmentArticleListBinding binding;
    private ArticleListViewModel viewModel;
    private ArticleListAdapter adapter;

    public ArticleListFragment(CrashReporter crashReporter, UsageTracker usageTracker, Database database,
                               RestClient restClient) {
        super(R.layout.fragment_article_list);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleListBinding.bind(view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticleListAdapter();
        adapter.onArticleClickListener = this::onArticleClick;
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyItemChanged(0);
        viewModel = ArticleListViewModel.of(this, crashReporter, usageTracker, database, restClient);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        viewModel.getArticles().observe(viewLifecycleOwner, this::onArticlesChanged);
        viewModel.isLoading().observe(viewLifecycleOwner, this::onLoadingChanged);
        viewModel.getError().observe(viewLifecycleOwner, this::onErrorChanged);
    }

    private void onArticleClick(Article article) {
        disableInteractions();
        NavDirections directions =
                ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailsFragment(article);
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void disableInteractions() {
        // TODO
    }

    private void onErrorChanged(HandleableEvent<Throwable> event) {
        Throwable error = event.getContentIfNotHandled();
        if (error == null) {
            return;
        }
        new AlertDialog.Builder(requireContext()).setTitle("Error")
                .setMessage(error.getLocalizedMessage())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
                .setOnDismissListener(dialog -> {})
                .show();
    }

    private void onLoadingChanged(boolean loading) {
        binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void onArticlesChanged(List<Article> articles) {
        adapter.articles.clear();
        adapter.articles.addAll(articles);
        adapter.notifyDataSetChanged();
    }
}
