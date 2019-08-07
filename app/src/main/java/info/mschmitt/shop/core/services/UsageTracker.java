package info.mschmitt.shop.core.services;

import info.mschmitt.shop.core.database.Database;

/**
 * @author Matthias Schmitt
 */
public class UsageTracker {
    private final Database database;

    public UsageTracker(Database database) {
        this.database = database;
    }

    public void trackArticleListScreen() {
    }

    public void setEnabled(boolean enabled) {
        database.setTrackingEnabled(enabled);
    }

    public boolean getEnabled() {
        return database.getTrackingEnabled();
    }

    public void trackException(Throwable throwable) {
    }
}
