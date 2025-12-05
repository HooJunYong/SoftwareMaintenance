package Assignment.Model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDAO {

    // Insert new order (header + items)
    public static boolean insert(Order order) {
        String orderSQL = "INSERT INTO `order` (orderID, invoiceNo, memberID, cashierID, orderDate, subtotal, discountAmount, taxAmount, grandTotal, paymentMethod) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String itemSQL = "INSERT INTO orderItem (orderID, productID, quantity) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBTest.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert order header
            try (PreparedStatement stmt = conn.prepareStatement(orderSQL)) {
                stmt.setString(1, order.getOrderID());
                stmt.setString(2, order.getInvoiceNo());
                stmt.setString(3, order.getMemberID());
                stmt.setString(4, order.getCashierID());
                stmt.setTimestamp(5, Timestamp.valueOf(order.getOrderDate()));
                stmt.setDouble(6, order.getSubtotal());
                stmt.setDouble(7, order.getDiscountAmount());
                stmt.setDouble(8, order.getTaxAmount());
                stmt.setDouble(9, order.getGrandTotal());
                stmt.setString(10, order.getPaymentMethod());
                stmt.executeUpdate();
            }

            // Insert order items
            try (PreparedStatement stmt = conn.prepareStatement(itemSQL)) {
                for (OrderItem item : order.getOrderItems()) {
                    stmt.setString(1, order.getOrderID());
                    stmt.setString(2, item.getProductID());
                    stmt.setInt(3, item.getQuantity());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit(); // Commit transaction
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Find order by ID
    public static Order findById(String orderID) {
        String orderSQL = "SELECT * FROM `order` WHERE orderID = ?";
        String itemSQL = "SELECT * FROM orderItem WHERE orderID = ?";
        Order order = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderSQL)) {

            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderID(rs.getString("orderID"));
                order.setInvoiceNo(rs.getString("invoiceNo"));
                order.setMemberID(rs.getString("memberID"));
                order.setCashierID(rs.getString("cashierID"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setDiscountAmount(rs.getDouble("discountAmount"));
                order.setTaxAmount(rs.getDouble("taxAmount"));
                order.setGrandTotal(rs.getDouble("grandTotal"));
                order.setPaymentMethod(rs.getString("paymentMethod"));

                // Load order items
                try (PreparedStatement itemStmt = conn.prepareStatement(itemSQL)) {
                    itemStmt.setString(1, orderID);
                    ResultSet itemRs = itemStmt.executeQuery();

                    while (itemRs.next()) {
                        OrderItem item = new OrderItem();
                        item.setOrderItemID(itemRs.getInt("orderItemID"));
                        item.setOrderID(itemRs.getString("orderID"));
                        item.setProductID(itemRs.getString("productID"));
                        order.addOrderItem(item);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    // Read all orders
    public static ArrayList<Order> readAll() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` ORDER BY orderDate DESC";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getString("orderID"));
                order.setInvoiceNo(rs.getString("invoiceNo"));
                order.setMemberID(rs.getString("memberID"));
                order.setCashierID(rs.getString("cashierID"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setDiscountAmount(rs.getDouble("discountAmount"));
                order.setTaxAmount(rs.getDouble("taxAmount"));
                order.setGrandTotal(rs.getDouble("grandTotal"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Get orders by member ID
    public static ArrayList<Order> findByMemberID(String memberID) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE memberID = ? ORDER BY orderDate DESC";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, memberID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getString("orderID"));
                order.setInvoiceNo(rs.getString("invoiceNo"));
                order.setMemberID(rs.getString("memberID"));
                order.setCashierID(rs.getString("cashierID"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setDiscountAmount(rs.getDouble("discountAmount"));
                order.setTaxAmount(rs.getDouble("taxAmount"));
                order.setGrandTotal(rs.getDouble("grandTotal"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Get orders by date range
    public static ArrayList<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE orderDate BETWEEN ? AND ? ORDER BY orderDate DESC";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getString("orderID"));
                order.setInvoiceNo(rs.getString("invoiceNo"));
                order.setMemberID(rs.getString("memberID"));
                order.setCashierID(rs.getString("cashierID"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setDiscountAmount(rs.getDouble("discountAmount"));
                order.setTaxAmount(rs.getDouble("taxAmount"));
                order.setGrandTotal(rs.getDouble("grandTotal"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Get next order ID (format: ORD001, ORD002, etc.)
    public static String getNextID() {
        String sql = "SELECT orderID FROM `order` ORDER BY orderID DESC LIMIT 1";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("orderID");
                int num = Integer.parseInt(lastID.substring(3)) + 1;
                return String.format("ORD%03d", num);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ORD001"; // First order
    }

    // Get total order count
    public static int getCount() {
        String sql = "SELECT COUNT(*) FROM `order`";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Get total sales amount
    public static double getTotalSales() {
        String sql = "SELECT SUM(grandTotal) FROM `order`";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}