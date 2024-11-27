package TelExpress_SA;

public class Utils {
    private static final String SALT = "telxpress2019";

    public static String hashTelefono(String telefono) {
        return String.valueOf((telefono + SALT).hashCode());
    }
}
