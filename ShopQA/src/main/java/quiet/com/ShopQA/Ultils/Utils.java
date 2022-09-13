package quiet.com.ShopQA.Ultils;

public class Utils {
    public static Integer parseIntegerWithDefault(String value, int defaultValue) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }
}
