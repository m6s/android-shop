package info.mschmitt.shop.core.network;

import okhttp3.OkHttpClient;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Matthias Schmitt
 */
public class LoggingUtils {
    public static Logger prettyPrintLogger() {
        Logger logger = Logger.getLogger(OkHttpClient.class.getName());
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord lr) {
                return lr.getMessage() + "\n";
            }
        });
        logger.addHandler(handler);
        return logger;
    }
}
