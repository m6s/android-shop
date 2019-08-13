package info.mschmitt.shop.core.util;

import java.io.Serializable;

/**
 * See
 * <a href="https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150">blog</a>
 *
 * @author Matthias Schmitt
 */
public class HandleableEvent<T> implements Serializable {
    public final T content;
    public boolean handled;

    public HandleableEvent(T content) {
        this.content = content;
    }
}
