package info.mschmitt.shop.app;

import androidx.multidex.MultiDexApplication;

/**
 * @author Matthias Schmitt
 */
public class ShopApplication extends MultiDexApplication {
    public static ShopApplication instance;
    private ShopModule module;

    public ShopModule getModule() {
        if (module == null) {
            module = ShopModule.create(this);
        }
        return module;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getModule().inject(this);
    }

    public void setDependencies() {
    }
}
