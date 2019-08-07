package info.mschmitt.shop.app;

import android.app.Application;
import android.content.Context;
import androidx.test.runner.AndroidJUnitRunner;

/**
 * @author Matthias Schmitt
 */
public class TestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }
}
