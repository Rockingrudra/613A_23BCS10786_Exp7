import java.sql.*;

public class EmployeeFetcher {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_43zytdf8j"; // your DB name
        String user = "db_43zytdf8j"; // your DB username
        String password = "g8mw*Vkls1Tpyup4"; // your DB password

        try {
            // 1. Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);

            // 3. Print all tables in the database
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "%", new String[] {"TABLE"});
            System.out.println("Tables in the database:");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
            tables.close();

            // 4. Use correct table name here
            String query = "SELECT * FROM Employee"; // <-- replace "Employee" if needed

            // 5. Create statement and execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // 6. Display results
            System.out.println("EmpID\tName\tSalary");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(id + "\t" + name + "\t" + salary);
            }

            // 7. Close connections
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
