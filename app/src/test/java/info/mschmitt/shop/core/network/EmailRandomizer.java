package info.mschmitt.shop.core.network;

import java.util.Random;

/**
 * @author Matthias Schmitt
 */
public class EmailRandomizer {
    private static final String SALT = "abcdefghijklmnopqrstuvwxyz";

    public static String createEmail(String prefix, String domain) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = (int) (random.nextFloat() * SALT.length());
            sb.append(SALT.charAt(index));
        }
        String suffix = sb.toString();
        return String.format("%s+%s@%s", prefix, suffix, domain);
    }
}
