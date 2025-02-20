import java.sql.*;

public class SQLDatabaseConnection {
    private Connection connection;

    public SQLDatabaseConnection() throws SQLException {
        String connectionString =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=GridTest;"
                        + "user=Mirzaie;"
                        + "password=";
        connection = DriverManager.getConnection(connectionString);
    }

    public void Command(String sqlString) throws SQLException {
        Statement statement = connection.createStatement();
        String selectSql =  sqlString;
        Boolean result = statement.execute(selectSql);
        int changeCount = 0;
        while(true){
            if (result) {
                ResultSet resultSet = statement.getResultSet();
                System.out.println(resultSet);
            } else {
                int updateCount = statement.getUpdateCount();
                if (updateCount == -1) break;
                changeCount++;
            }
            result = statement.getMoreResults();
        }
    }

    public ResultSet Select(String sqlString) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlString);
        return resultSet;
    }
}
