package info.mschmitt.shop.app;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author Matthias Schmitt
 */
public class SplashFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToHomeFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }
}
