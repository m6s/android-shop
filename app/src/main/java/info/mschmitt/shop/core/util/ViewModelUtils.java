package info.mschmitt.shop.core.util;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author Matthias Schmitt
 */
public class ViewModelUtils {
    public static <T extends ViewModel> T provide(Fragment fragment, Class<T> tClass, ViewModelFactory<T> factory) {
        return ViewModelProviders.of(fragment, new AbstractSavedStateViewModelFactory(fragment, null) {
            @NonNull
            @Override
            protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass,
                                                     @NonNull SavedStateHandle handle) {
                return (T) factory.create(handle, fragment.getActivity().getApplication());
            }
        }).get(tClass);
    }

    public interface ViewModelFactory<T extends ViewModel> {
        T create(SavedStateHandle handle, Application application);
    }
}
