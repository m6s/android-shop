package info.mschmitt.shop.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.preference.*;
import info.mschmitt.shop.app.databinding.FragmentSettingsBinding;
import info.mschmitt.shop.core.database.Database;
import info.mschmitt.shop.core.network.RestClient;
import info.mschmitt.shop.core.services.CrashReporter;
import info.mschmitt.shop.core.services.UsageTracker;

import java.util.Locale;

/**
 * @author Matthias Schmitt
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private final CrashReporter crashReporter;
    private final UsageTracker usageTracker;
    private final Database database;
    private final RestClient restClient;
    private FragmentSettingsBinding binding;
    private Preference buildPreference;
    private PreferenceGroup accountGroup;
    private Preference createAccountPreference;
    private Preference changeEmailPreference;
    private Preference changePasswordPreference;
    private Preference logInPreference;
    private Preference logOutPreference;
    private Preference deleteAccountPreference;
    private PreferenceGroup aboutGroup;
    private Preference licensesPreference;
    private Preference privacyPreference;
    private Preference imprintPreference;
    private Preference contactPreference;
    private SwitchPreference trackingPreference;
    private SwitchPreference crashReportingPreference;
    private PreferenceGroup debugGroup;
    private ListPreference switchBackendPreference;

    public SettingsFragment(CrashReporter crashReporter, UsageTracker usageTracker, Database database, RestClient restClient) {
        this.crashReporter = crashReporter;
        this.usageTracker = usageTracker;
        this.database = database;
        this.restClient = restClient;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        accountGroup = findPreference(getString(R.string.pref_key_account));
        createAccountPreference = findPreference(getString(R.string.pref_key_account_create));
        changeEmailPreference = findPreference(getString(R.string.pref_key_account_change_email));
        changePasswordPreference = findPreference(getString(R.string.pref_key_account_change_password));
        logInPreference = findPreference(getString(R.string.pref_key_account_log_in));
        logOutPreference = findPreference(getString(R.string.pref_key_account_log_out));
        logInPreference.setOnPreferenceClickListener(this::onLogOutClick);
        deleteAccountPreference = findPreference(getString(R.string.pref_key_account_delete));
        aboutGroup = findPreference(getString(R.string.pref_key_about));
        licensesPreference = findPreference(getString(R.string.pref_key_about_licenses));
        privacyPreference = findPreference(getString(R.string.pref_key_about_privacy));
        imprintPreference = findPreference(getString(R.string.pref_key_about_imprint));
        contactPreference = findPreference(getString(R.string.pref_key_about_contact));
        trackingPreference = findPreference(getString(R.string.pref_key_about_tracking));
        trackingPreference.setOnPreferenceChangeListener(this::onTrackingChange);
        crashReportingPreference = findPreference(getString(R.string.pref_key_about_crash_reporting));
        buildPreference = findPreference(getString(R.string.pref_key_about_build));
        buildPreference.setOnPreferenceClickListener(this::onBuildClick);
        debugGroup = findPreference(getString(R.string.pref_key_debug));
        switchBackendPreference = findPreference(getString(R.string.pref_key_debug_switch_backend));
        switchBackendPreference.setOnPreferenceChangeListener(this::onSwitchBackendChange);
        updatePreferences();
    }

    private boolean onTrackingChange(Preference preference, Object newValue) {
        boolean enabled = (boolean) newValue;
        Toast.makeText(getContext(), enabled ? "Tracking enabled" : "Tracking disabled", Toast.LENGTH_LONG).show();
        usageTracker.setEnabled(enabled);
        return true;
    }

    private boolean onSwitchBackendChange(Preference preference, Object newValue) {
        String backend = (String) newValue;
        database.setBackend(backend);
        updatePreferences();
        return true;
    }

    private boolean onLogOutClick(Preference preference) {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireContext().startActivity(intent);
        Runtime.getRuntime().exit(0);
        return true;
    }

    private boolean onBuildClick(Preference preference) {
        Toast.makeText(getContext(), "Developer settings enabled", Toast.LENGTH_LONG).show();
        database.setDeveloperModeEnabled(true);
        updatePreferences();
        return true;
    }

    private void updatePreferences() {
        changeEmailPreference.setSummary(database.getEmail());
        trackingPreference.setChecked(usageTracker.getEnabled());
        createAccountPreference.setVisible(false);
        logInPreference.setVisible(false);
        String backend = database.getBackend();
        if (backend.equals(getString(R.string.backend_entry_values_testing))) {
            switchBackendPreference.setSummary("Testing");
        } else if (backend.equals(getString(R.string.backend_entry_values_staging))) {
            switchBackendPreference.setSummary("Staging");
        } else if (backend.equals(getString(R.string.backend_entry_values_production))) {
            switchBackendPreference.setSummary("Production");
        }
        debugGroup.setVisible(database.getDeveloperModeEnabled());
        String buildSummary =
                String.format(Locale.getDefault(), "%s/%d", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        buildPreference.setSummary(buildSummary);
    }
}
