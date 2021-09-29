import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "search_engine";
    private static String dbUser = "root";
    private static String dbPass = "1974287040998Au!";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS page");
                connection.createStatement().execute("CREATE TABLE page(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "path TINYTEXT NOT NULL, " +
                        "code INT NOT NULL, " +
                        "content MEDIUMTEXT NOT NULL, " +
                        "PRIMARY KEY(id), KEY(path(50)))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert(String path, int code, String content) throws SQLException {
        String sql = "INSERT INTO page(path, code, content) " +
                "VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, path);
        preparedStatement.setInt(2, code);
        preparedStatement.setString(3, content);
        preparedStatement.executeUpdate();
    }
}
