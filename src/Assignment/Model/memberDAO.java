package Assignment.Model;

import java.sql.*;
import java.util.ArrayList;

public class MemberDAO {

    // Read all members from database
    public static ArrayList<Member> readAll() {
        ArrayList<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("memberID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");

                Member member = new Member(id, name, email, phoneNo);
                members.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return members;
    }

    // Find member by ID
    public static Member findById(String id) {
        String sql = "SELECT * FROM member WHERE memberID = ?";
        Member member = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");

                member = new Member(id, name, email, phoneNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    // Find member by email
    public static Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        Member member = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("memberID");
                String name = rs.getString("name");
                String phoneNo = rs.getString("phoneNumber");

                member = new Member(id, name, email, phoneNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    // Find member by phone number
    public static Member findByPhone(String phoneNo) {
        String sql = "SELECT * FROM member WHERE phoneNumber = ?";
        Member member = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("memberID");
                String name = rs.getString("name");
                String email = rs.getString("email");

                member = new Member(id, name, email, phoneNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    // Search members by name (partial match)
    public static ArrayList<Member> searchByName(String searchName) {
        ArrayList<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE name LIKE ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("memberID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");

                Member member = new Member(id, name, email, phoneNo);
                members.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return members;
    }

    // Validate member ID exists
    public static boolean validateMemberID(String id) {
        String sql = "SELECT memberID FROM member WHERE memberID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Returns true if member exists
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert new member
    public static boolean insert(Member member) {
        String sql = "INSERT INTO member (memberID, name, email, phoneNumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getID());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPhoneNo());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update member
    public static boolean update(Member member) {
        String sql = "UPDATE member SET name = ?, email = ?, phoneNumber = ? WHERE memberID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhoneNo());
            stmt.setString(4, member.getID());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete member
    public static boolean delete(String id) {
        String sql = "DELETE FROM member WHERE memberID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get next member ID (auto-increment format: M001, M002, etc.)
    public static String getNextID() {
        String sql = "SELECT memberID FROM member ORDER BY memberID DESC LIMIT 1";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("memberID");
                // Extract number from ID (e.g., "M001" -> 1)
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                return "M" + String.format("%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "M001"; // Default first ID
    }

    // Check if email already exists
    public static boolean emailExists(String email) {
        String sql = "SELECT memberID FROM member WHERE email = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if phone number already exists
    public static boolean phoneExists(String phoneNo) {
        String sql = "SELECT memberID FROM member WHERE phoneNumber = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNo);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get total member count
    public static int getCount() {
        String sql = "SELECT COUNT(*) AS total FROM member";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}