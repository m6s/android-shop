package info.mschmitt.shop.core.storage;

/**
 * @author Matthias Schmitt
 */
public class Config implements Cloneable {
    public static final String BACKEND_EMULATOR_HOST = "emulatorHost";
    public static final String BACKEND_TESTING = "testing";
    public static final String BACKEND_STAGING = "staging";
    public static final String BACKEND_PRODUCTION = "production";
    public String email;
    public boolean developerMode;
    public String backend;
    public boolean tracking;
    public ServerProfile emulatorHostProfile = new ServerProfile();
    public ServerProfile testingProfile = new ServerProfile();
    public ServerProfile stagingProfile = new ServerProfile();
    public ServerProfile productionProfile = new ServerProfile();

    @Override
    public Config clone() {
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ServerProfile implements Cloneable {
        public String accessToken;
        public String refreshToken;
    }
}
