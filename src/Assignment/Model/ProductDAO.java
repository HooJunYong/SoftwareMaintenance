package Assignment.Model;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {

    // 1. READ ALL PRODUCTS
    public static ArrayList<Product> readAll() {
        ArrayList<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Retrieve data from DB columns
                String id = rs.getString("productID");
                String name = rs.getString("productName");
                Double price = rs.getDouble("price");
                Integer quantity = rs.getInt("quantity");
                Integer warranty = rs.getInt("warrantyYear");
                
                Product product = new Product(id, name, price, quantity, warranty);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading products from database.");
        }

        return productList;
    }

    // 2. FIND PRODUCT BY ID
    public static Product findById(String id) {
        String sql = "SELECT * FROM product WHERE productID = ?";
        Product product = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("productName");
                Double price = rs.getDouble("price");
                Integer quantity = rs.getInt("quantity");
                Integer warranty = rs.getInt("warrantyYear");

                product = new Product(name, price, quantity, warranty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    // 3. INSERT NEW PRODUCT
    public static boolean insert(Product product) {
        String sql = "INSERT INTO product (productID, productName, price, quantity, warrantyYear) VALUES (?, ?, ?, ?, ?)";
        
        // Generate the next ID (e.g., P005)
        String nextID = getNextID();
        
        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nextID);
            stmt.setString(2, product.getProductName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getWarrantyYear());

            int rows = stmt.executeUpdate();
            if(rows > 0) {
                // Update the ID in the object so the UI shows the correct new ID
                product.setProductID(nextID); 
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. UPDATE PRODUCT (For Modify Page or Stock Update)
    public static boolean update(Product product) {
        String sql = "UPDATE product SET productName = ?, price = ?, quantity = ?, warrantyYear = ? WHERE productID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getWarrantyYear());
            stmt.setString(5, product.getProductID());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. UPDATE PRODUCT (For Stock Update when add to cart)
    public static boolean updateStock(Product product) {
        String sql = "UPDATE product SET quantity = ? WHERE productID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getQuantity());
            stmt.setString(2, product.getProductID());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. DELETE PRODUCT
    public static boolean delete(String productID) {
        String sql = "DELETE FROM product WHERE productID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productID);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. GENERATE NEXT ID (P001, P002...)
    public static String getNextID() {
        String sql = "SELECT productID FROM product ORDER BY productID DESC LIMIT 1";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("productID");
                // Extract number after 'P'
                int num = Integer.parseInt(lastId.substring(1)); 
                return "P" + String.format("%03d", num + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "P001"; // Default if table is empty
    }
}