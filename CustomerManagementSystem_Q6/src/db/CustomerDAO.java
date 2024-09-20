package db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public List<String> getCustomers() {
        List<String> customers = new ArrayList<>();
        String sql = "SELECT CustomerID, ShortName FROM Customers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(rs.getInt("CustomerID") + ": " + rs.getString("ShortName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void addCustomer(String shortName, String fullName, String address1, String address2, String address3, String city, String postalCode) {
        String sql = "INSERT INTO Customers (ShortName, FullName, Address1, Address2, Address3, City, PostalCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, shortName);
            stmt.setString(2, fullName);
            stmt.setString(3, address1);
            stmt.setString(4, address2);
            stmt.setString(5, address3);
            stmt.setString(6, city);
            stmt.setString(7, postalCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(int id, String shortName, String fullName, String address1, String address2, String address3, String city, String postalCode) {
        String sql = "UPDATE Customers SET ShortName=?, FullName=?, Address1=?, Address2=?, Address3=?, City=?, PostalCode=? WHERE CustomerID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, shortName);
            stmt.setString(2, fullName);
            stmt.setString(3, address1);
            stmt.setString(4, address2);
            stmt.setString(5, address3);
            stmt.setString(6, city);
            stmt.setString(7, postalCode);
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        String sql = "DELETE FROM Customers WHERE CustomerID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCustomerById(int id) {
        String sql = "SELECT * FROM Customers WHERE CustomerID=?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
