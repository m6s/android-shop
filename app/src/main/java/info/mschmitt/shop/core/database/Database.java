package info.mschmitt.shop.core.database;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

import java.io.*;

/**
 * @author Matthias Schmitt
 */
public class Database {
    private final Gson gson;
    private final Config config;
    private final File configFile;
    private final Scheduler workScheduler;

    public Database(File filesDir, Scheduler workScheduler) {
        this.workScheduler = workScheduler;
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
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
            config.backend = "testing";
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
        apply();
    }

    public boolean getDeveloperModeEnabled() {
        return config.developerMode;
    }

    public void setDeveloperModeEnabled(boolean enabled) {
        config.developerMode = enabled;
        apply();
    }

    public boolean getTrackingEnabled() {
        return config.tracking;
    }

    public void setTrackingEnabled(boolean enabled) {
        config.tracking = enabled;
        apply();
    }

    public String getToken() {
        return config.token;
    }

    public void setToken(String token) {
        config.token = token;
        apply();
    }

    private void apply() {
        Config clone = config.clone();
        Completable.fromAction(() -> {
            try (Writer writer = new FileWriter(configFile)) {
                gson.toJson(clone, writer);
            }
        }).subscribeOn(workScheduler).subscribe();
    }
}
