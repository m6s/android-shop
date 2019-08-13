package info.mschmitt.shop.app;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import info.mschmitt.shop.app.databinding.FragmentHomeBinding;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;

/**
 * @author Matthias Schmitt
 */
public class HomeFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final ApiClient apiClient;
    private FragmentHomeBinding binding;
    private long aLong;
    private long bLong;

    public HomeFragment(CrashReporter crashReporter, UsageTracker usageTracker, Database database,
                        ApiClient apiClient) {
        super(R.layout.fragment_home);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.apiClient = apiClient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.bind(view);
        binding.settingsButton.setOnClickListener(this::onSettingsClick);
        binding.articleListButton.setOnClickListener(this::onArticleListClick);
    }

    private void onSettingsClick(View view) {
        setEnabled(false);
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToSettingsFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void onArticleListClick(View view) {
        setEnabled(false);
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToArticleListFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void setEnabled(boolean enabled) {
        binding.settingsButton.setEnabled(enabled);
        binding.articleListButton.setEnabled(enabled);
    }
}
