package info.mschmitt.shop.core.database;

/**
 * @author Matthias Schmitt
 */
public class Config implements Cloneable {
    public String email;
    public boolean developerMode;
    public String backend;
    public boolean tracking;
    public String token;

    @Override
    public Config clone() {
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
