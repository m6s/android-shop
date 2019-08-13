package info.mschmitt.shop.app;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.MotionEvents;
import androidx.test.espresso.matcher.ViewMatchers;
import org.hamcrest.Matcher;

import java.util.ArrayList;

/**
 * https://stackoverflow.com/a/42349267
 */
public class RealisticViewActions {
    public static ViewAction touchDownAndUp(int count, long millisDelay, float x, float y) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Send touch events.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                // Get view absolute position
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                // Offset coordinates by view position
                float[] coordinates = new float[]{x + location[0], y + location[1]};
                float[] precision = new float[]{1f, 1f};
                ArrayList<MotionEvent> events = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    // Send down event, pause, and send up
                    MotionEvent down = MotionEvents.obtainDownEvent(coordinates, precision);
                    events.add(down);
                    MotionEvent upEvent = MotionEvents.obtainUpEvent(down, coordinates);
                    events.add(upEvent);
                }
                try {
                    uiController.injectMotionEventSequence(events);
                } catch (InjectEventSecurityException ignored) {
                    Log.d("", "perform: ");
                }
            }
        };
    }
}
