import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    // Use your actual DB info here
    static final String URL = "jdbc:mysql://localhost:3306/db_43zytdf8j";
    static final String USER = "db_43zytdf8j";
    static final String PASS = "g8mw*Vkls1Tpyup4";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL JDBC driver once before connecting
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                conn.setAutoCommit(false);

                // Optional: List tables to verify Product table exists
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet tables = meta.getTables(null, null, "%", new String[] {"TABLE"});
                System.out.println("Tables in the database:");
                while (tables.next()) {
                    System.out.println(tables.getString("TABLE_NAME"));
                }
                tables.close();

                while (true) {
                    System.out.println("\nProduct Management System");
                    System.out.println("1. Create Product");
                    System.out.println("2. Read Products");
                    System.out.println("3. Update Product");
                    System.out.println("4. Delete Product");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option: ");

                    int choice = sc.nextInt();
                    sc.nextLine();  // consume leftover newline

                    switch (choice) {
                        case 1:
                            System.out.print("Product Name: ");
                            String name = sc.nextLine();

                            System.out.print("Price: ");
                            double price = sc.nextDouble();

                            System.out.print("Quantity: ");
                            int qty = sc.nextInt();

                            String insert = "INSERT INTO Product(ProductName, Price, Quantity) VALUES (?, ?, ?)";
                            try (PreparedStatement pst = conn.prepareStatement(insert)) {
                                pst.setString(1, name);
                                pst.setDouble(2, price);
                                pst.setInt(3, qty);
                                pst.executeUpdate();
                                conn.commit();
                                System.out.println("Product added.");
                            } catch (SQLException e) {
                                conn.rollback();
                                e.printStackTrace();
                            }
                            break;

                        case 2:
                            String read = "SELECT * FROM Product";
                            try (Statement stmt = conn.createStatement();
                                 ResultSet rs = stmt.executeQuery(read)) {
                                System.out.println("ProductID\tProductName\tPrice\tQuantity");
                                while (rs.next()) {
                                    System.out.printf("%d\t\t%s\t\t%.2f\t%d\n",
                                            rs.getInt("ProductID"),
                                            rs.getString("ProductName"),
                                            rs.getDouble("Price"),
                                            rs.getInt("Quantity"));
                                }
                            }
                            break;

                        case 3:
                            System.out.print("Enter ProductID to update: ");
                            int idToUpdate = sc.nextInt();

                            System.out.print("New Price: ");
                            double newPrice = sc.nextDouble();

                            System.out.print("New Quantity: ");
                            int newQty = sc.nextInt();

                            String update = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
                            try (PreparedStatement pst = conn.prepareStatement(update)) {
                                pst.setDouble(1, newPrice);
                                pst.setInt(2, newQty);
                                pst.setInt(3, idToUpdate);
                                int rows = pst.executeUpdate();
                                if (rows > 0) {
                                    conn.commit();
                                    System.out.println("Product updated.");
                                } else {
                                    System.out.println("Product not found.");
                                }
                            } catch (SQLException e) {
                                conn.rollback();
                                e.printStackTrace();
                            }
                            break;

                        case 4:
                            System.out.print("Enter ProductID to delete: ");
                            int idToDelete = sc.nextInt();

                            String delete = "DELETE FROM Product WHERE ProductID = ?";
                            try (PreparedStatement pst = conn.prepareStatement(delete)) {
                                pst.setInt(1, idToDelete);
                                int rows = pst.executeUpdate();
                                if (rows > 0) {
                                    conn.commit();
                                    System.out.println("Product deleted.");
                                } else {
                                    System.out.println("Product not found.");
                                }
                            } catch (SQLException e) {
                                conn.rollback();
                                e.printStackTrace();
                            }
                            break;

                        case 5:
                            conn.close();
                            sc.close();
                            System.out.println("Goodbye!");
                            System.exit(0);

                        default:
                            System.out.println("Invalid option, try again.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
