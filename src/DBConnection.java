import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Bağlantı bilgileri ortam değişkenlerinden okunur; tanımlı değilse yerel varsayılanlar kullanılır.
    // Böylece şifre kaynak koda yazılmaz.
    private static final String URL  = env("DB_URL", "jdbc:postgresql://localhost:5432/kutuphaneSistemi");
    private static final String USER = env("DB_USER", "postgres");
    private static final String PASS = env("DB_PASSWORD", "postgres");

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isEmpty()) ? fallback : value;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
