package info.mschmitt.shop.app;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Matthias Schmitt
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {
    private static final boolean LONG_DELAYS = false;
    @Rule public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Can't use {@link Before} annotation, because {@link android.app.Application#onCreate()} has already been called
     * by that time.
     */
    public MainTest() {
        when(TestApplication.dataStore.getBackend()).thenReturn("testing");
        when(TestApplication.dataStore.getEmail()).thenReturn("m6stestlab@gmail.com");
    }

    @Test
    public void changeAllSettings() {
        pauseBegin();
//        onView(withText("Settings")).perform(click());
        onView(withText("Settings")).perform(RealisticViewActions.touchDownAndUp(2, 0, 10, 10));
        pause();
        // https://stackoverflow.com/a/52144554
        onView(withId(R.id.recycler_view)).perform(actionOnItem(hasDescendant(withText("Change email")), scrollTo()));
        onView(withText("m6stestlab@gmail.com")).check(matches(isDisplayed()));
        assertThat(TestApplication.dataStore.getTrackingEnabled()).isFalse();
        onView(withId(R.id.recycler_view)).perform(actionOnItem(hasDescendant(withText("Tracking")), click()));
        assertThat(TestApplication.dataStore.getTrackingEnabled()).isTrue();
        pause();
        onView(withId(R.id.recycler_view)).perform(actionOnItem(hasDescendant(withText("Build info")), click()));
        pause();
        onView(withId(R.id.recycler_view)).perform(actionOnItem(hasDescendant(withText("Switch backend")), click()));
        onView(withText("Staging")).perform(click());
        pauseEnd();
    }

    @Test
    public void browseArticles() {
        pauseBegin();
        onView(withText("Article list")).perform(click());
        pause(TimeUnit.SECONDS.toMillis(3), true);
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, scrollTo()));
        onView(withText("Article A")).perform(click());
        pause(TimeUnit.SECONDS.toMillis(1), true);
        onView(withText("Article A")).check(matches(isDisplayed()));
        pauseEnd();
    }

    private void pauseBegin() {
        if (!LONG_DELAYS) {
            return;
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        } catch (InterruptedException ignored) {
        }
    }

    private void pause() {
        pause(TimeUnit.SECONDS.toMillis(1), false);
    }

    private void pause(long millis, boolean force) {
        if (!LONG_DELAYS && !force) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private void pauseEnd() {
        if (!LONG_DELAYS) {
            return;
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(4));
        } catch (InterruptedException ignored) {
        }
    }
}
