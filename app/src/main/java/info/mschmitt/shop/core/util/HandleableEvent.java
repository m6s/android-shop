package info.mschmitt.shop.core.util;

import java.io.Serializable;

/**
 * See
 * <a href="https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150">blog</a>
 *
 * @author Matthias Schmitt
 */
public class HandleableEvent<T> implements Serializable {
    private final T content;
    private boolean handled = false;

    public HandleableEvent(T content) {
        this.content = content;
    }

    public boolean isHandled() {
        return handled;
    }

    public T getContentIfNotHandled() {
        if (handled) {
            return null;
        } else {
            handled = true;
            return content;
        }
    }

    public T peekContent() {
        return content;
    }
}
