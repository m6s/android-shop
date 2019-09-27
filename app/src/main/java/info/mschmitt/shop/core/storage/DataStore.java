package info.mschmitt.shop.core.storage;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * @author Matthias Schmitt
 */
public class DataStore {
    private final Gson gson = new Gson();
    private final Config config;
    private final File configFile;
    private final Scheduler workScheduler;
    private Config.ServerProfile serverProfile;

    public DataStore(File filesDir, Scheduler workScheduler) {
        this.workScheduler = workScheduler;
        File versionDir = new File(filesDir, "1");
        //noinspection ResultOfMethodCallIgnored
        versionDir.mkdirs();
        configFile = new File(versionDir, "config.json");
        if (configFile.exists()) {
            try (Reader reader = new FileReader(configFile)) {
                config = gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            config = new Config();
            config.backend = Config.BACKEND_TESTING;
            config.tracking = true;
            config.backend = "testing";
        }
        updateServerProfile();
    }

    private void updateServerProfile() {
        switch (config.backend) {
            case Config.BACKEND_EMULATOR_HOST:
                serverProfile = config.emulatorHostProfile;
                break;
            case Config.BACKEND_TESTING:
                serverProfile = config.testingProfile;
                break;
            case Config.BACKEND_STAGING:
                serverProfile = config.stagingProfile;
                break;
            case Config.BACKEND_PRODUCTION:
                serverProfile = config.productionProfile;
                break;
            default:
                throw new RuntimeException();
        }
    }

    public String getEmail() {
        return config.email;
    }

    public String getBackend() {
        return config.backend;
    }

    public void setBackend(String backend) {
        config.backend = backend;
        commit().subscribe();
    }

    public boolean getDeveloperModeEnabled() {
        return config.developerMode;
    }

    public void setDeveloperModeEnabled(boolean enabled) {
        config.developerMode = enabled;
        commit().subscribe();
    }

    public boolean getTrackingEnabled() {
        return config.tracking;
    }

    public void setTrackingEnabled(boolean enabled) {
        config.tracking = enabled;
        commit().subscribe();
    }

    public String getRefreshToken() {
        return serverProfile.refreshToken;
    }

    public void setRefreshToken(String token) {
        serverProfile.refreshToken = token;
        commit().subscribe();
    }

    public String getAccessToken() {
        return serverProfile.accessToken;
    }

    public void setAccessToken(String token) {
        serverProfile.accessToken = token;
        commit().subscribe();
    }

    public Completable commit() {
        Config clone = config.clone();
        return Completable.fromAction(() -> {
            try (Writer writer = new FileWriter(configFile)) {
                gson.toJson(clone, writer);
            }
        }).subscribeOn(workScheduler);
    }
}
