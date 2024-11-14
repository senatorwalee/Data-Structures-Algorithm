package assignment3.Database;

public class LogInInfo {
    private static final String URL = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    private static final String USER_NAME = "N01301373";
    private static final String PASSWORD = "oracle";

    public static String getUrl() {
        return URL;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
