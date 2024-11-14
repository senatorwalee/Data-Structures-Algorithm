package assignment3.Database;

import java.sql.*;

public class WorkHoursData {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        Statement selectStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver is loaded");

            // Establish the connection
            connection = DriverManager.getConnection(LogInInfo.getUrl(), LogInInfo.getUserName(),
                    LogInInfo.getPassword());
            System.out.println("Connection successful");

            // Disable auto-commit mode
            connection.setAutoCommit(false);

            // Insert data into WorkHours table
            String insertQuery = "INSERT INTO WorkHours (ID, Name, Role, Hours) VALUES (?, ?, ?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, 101); // Example ID
            insertStatement.setString(2, "John Doe"); // Example Name
            insertStatement.setString(3, "Developer"); // Example Role
            insertStatement.setInt(4, 40); // Example Hours
            insertStatement.executeUpdate();

            // Commit the transaction
            connection.commit();
            System.out.println("Data inserted and committed successfully.");

            // Query and display the inserted data
            String selectQuery = "SELECT * FROM WorkHours";
            selectStatement = connection.createStatement();
            resultSet = selectStatement.executeQuery(selectQuery);

            System.out.println("\nWorkHours Table Data:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("ID"));
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("Role: " + resultSet.getString("Role"));
                System.out.println("Hours: " + resultSet.getInt("Hours"));
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.out.println("Rolling back changes due to error.");
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close all resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (selectStatement != null)
                    selectStatement.close();
                if (insertStatement != null)
                    insertStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
