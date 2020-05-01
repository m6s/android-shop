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

import java.util.List;

import info.mschmitt.shop.app.databinding.FragmentArticleListBinding;
import info.mschmitt.shop.core.CrashReporter;
import info.mschmitt.shop.core.UsageTracker;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.storage.Article;
import info.mschmitt.shop.core.storage.DataStore;
import info.mschmitt.shop.core.util.HandleableEvent;

/**
 * @author Matthias Schmitt
 */
public class ArticleListFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final DataStore dataStore;
    private final ApiClient apiClient;
    private FragmentArticleListBinding binding;
    private ArticleListViewModel viewModel;
    private ArticleListAdapter adapter;

    public ArticleListFragment(CrashReporter crashReporter, UsageTracker usageTracker,
                               DataStore dataStore, ApiClient apiClient) {
        super(R.layout.fragment_article_list);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.dataStore = dataStore;
        this.apiClient = apiClient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleListBinding.bind(view);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticleListAdapter();
        adapter.onArticleClickListener = this::onArticleClick;
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyItemChanged(0);
        viewModel =
                ArticleListViewModel.of(this, crashReporter, usageTracker, dataStore, apiClient);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        viewModel.getArticles().observe(viewLifecycleOwner, this::onArticlesChanged);
        viewModel.isLoading().observe(viewLifecycleOwner, this::onLoadingChanged);
        viewModel.getErrorEvent().observe(viewLifecycleOwner, this::onErrorEventChanged);
    }

    private void onArticleClick(Article article) {
        setEnabled(false);
        NavDirections directions =
                ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailsFragment(
                        article);
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void onErrorEventChanged(HandleableEvent<Throwable> event) {
        if (event.handled) {
            return;
        }
        new AlertDialog.Builder(requireContext()).setTitle("Error")
                .setMessage(event.content.getLocalizedMessage())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
                .setOnDismissListener(dialog -> event.handled = true)
                .show();
    }

    private void onLoadingChanged(boolean loading) {
        setEnabled(!loading);
        binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void onArticlesChanged(List<Article> articles) {
        adapter.setArticles(articles);
    }

    private void setEnabled(boolean enabled) {
        adapter.setEnabled(enabled);
    }
}
