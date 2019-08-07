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
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;

/**
 * @author Matthias Schmitt
 */
public class HomeFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;
    private FragmentHomeBinding binding;

    public HomeFragment(CrashReporter crashReporter, UsageTracker usageTracker, Database database,
                        RestClient restClient) {
        super(R.layout.fragment_home);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.bind(view);
        binding.settingsButton.setOnClickListener(this::onSettingsClick);
        binding.articleListButton.setOnClickListener(this::onArticleListClick);
    }

    private void onSettingsClick(View view) {
        disableInteractions();
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToSettingsFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void onArticleListClick(View view) {
        disableInteractions();
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToArticleListFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void disableInteractions() {
        binding.settingsButton.setEnabled(false);
        binding.articleListButton.setEnabled(false);
    }
}
