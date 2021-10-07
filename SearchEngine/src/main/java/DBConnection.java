import java.sql.*;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String dbPass = "1974287040998Au!";
                String dbUser = "root";
                String dbName = "search_engine";
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                createTablePage(connection);
                createTableField(connection);
                createTableLemma(connection);
                createTableIndex(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void createTableField(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS field");
        connection.createStatement().execute("CREATE TABLE field(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "selector VARCHAR(255) NOT NULL, " +
                "weight FLOAT NOT NULL)");
    }

    public static void createTableLemma(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS lemma");
        connection.createStatement().execute("CREATE TABLE lemma(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "lemma VARCHAR(255) NOT NULL, " +
                "frequency INT NOT NULL)");
    }

    public static void createTableIndex(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS indexes");
        connection.createStatement().execute("CREATE TABLE indexes(" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "page_id INT NOT NULL, " +
                "lemma_id INT NOT NULL, " +
                "rank FLOAT NOT NULL, " +
                "CONSTRAINT index_fk1 FOREIGN KEY (page_id) REFERENCES page (id) ON DELETE CASCADE, " +
                "CONSTRAINT index_fk2 FOREIGN KEY (lemma_id) REFERENCES lemma (id) ON DELETE CASCADE)");
    }

    public static void createTablePage(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS page");
        connection.createStatement().execute("CREATE TABLE page(" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "path TINYTEXT NOT NULL, " +
                "code INT NOT NULL, " +
                "content MEDIUMTEXT NOT NULL, " +
                "PRIMARY KEY(id), KEY(path(50)))");
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
