package info.mschmitt.shop.app;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import info.mschmitt.shop.app.databinding.FragmentOnboardingBinding;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;

/**
 * @author Matthias Schmitt
 */
public class OnboardingFragment extends Fragment {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;
    private FragmentOnboardingBinding binding;

    public OnboardingFragment(CrashReporter crashReporter, UsageTracker usageTracker, Database database, RestClient restClient) {
        super(R.layout.fragment_onboarding);
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboardingBinding.bind(view);
    }
}
