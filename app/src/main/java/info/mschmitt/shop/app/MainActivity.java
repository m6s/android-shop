package info.mschmitt.shop.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import info.mschmitt.shop.core.CrashReporter;
import info.mschmitt.shop.core.UsageTracker;
import info.mschmitt.shop.core.network.ApiClient;
import info.mschmitt.shop.core.storage.DataStore;

public class MainActivity extends AppCompatActivity {
    private CrashReporter crashReporter;
    private UsageTracker usageTracker;
    private DataStore dataStore;
    private ApiClient apiClient;
    private NavController.OnDestinationChangedListener onDestinationChangedListener =
            MainActivity.this::onDestinationChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ShopApplication.instance.getModule().inject(this);
        getSupportFragmentManager().setFragmentFactory(new FragmentFactory() {
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                if (className.equals(SplashFragment.class.getName())) {
                    return new SplashFragment();
                } else if (className.equals(OnboardingFragment.class.getName())) {
                    return new OnboardingFragment(crashReporter, usageTracker, dataStore,
                            apiClient);
                } else if (className.equals(HomeFragment.class.getName())) {
                    return new HomeFragment(crashReporter, usageTracker, dataStore, apiClient);
                } else if (className.equals(SettingsFragment.class.getName())) {
                    return new SettingsFragment(crashReporter, usageTracker, dataStore, apiClient);
                } else if (className.equals(ArticleListFragment.class.getName())) {
                    return new ArticleListFragment(crashReporter, usageTracker, dataStore,
                            apiClient);
                } else if (className.equals(ArticleDetailsFragment.class.getName())) {
                    return new ArticleDetailsFragment(crashReporter, usageTracker, dataStore,
                            apiClient);
                }
                return super.instantiate(classLoader, className);
            }
        });
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().getPrimaryNavigationFragment();
        assert navHostFragment != null;
        navHostFragment.getNavController().addOnDestinationChangedListener(onDestinationChangedListener);
    }

    @Override
    protected void onPause() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().getPrimaryNavigationFragment();
        assert navHostFragment != null;
        navHostFragment.getNavController().removeOnDestinationChangedListener(onDestinationChangedListener);
        super.onPause();
    }

    private void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination,
                                      @Nullable Bundle arguments) {
        switch (destination.getId()) {
            case R.id.articleListFragment:
                usageTracker.trackArticleListScreen();
                break;
        }
    }

    public void setDependencies(CrashReporter crashReporter, UsageTracker usageTracker,
                                DataStore dataStore,
                                ApiClient apiClient) {
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.dataStore = dataStore;
        this.apiClient = apiClient;
    }
}
