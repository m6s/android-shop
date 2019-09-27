package info.mschmitt.shop.core;

import info.mschmitt.shop.core.storage.DataStore;

/**
 * @author Matthias Schmitt
 */
public class UsageTracker {
    private final DataStore dataStore;

    public UsageTracker(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void trackArticleListScreen() {
    }

    public boolean getEnabled() {
        return dataStore.getTrackingEnabled();
    }

    public void setEnabled(boolean enabled) {
        dataStore.setTrackingEnabled(enabled);
    }
}
