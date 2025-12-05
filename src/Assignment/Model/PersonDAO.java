package Assignment.Model;


import java.sql.*;
import java.util.ArrayList;
import Assignment.Model.Person;
import Assignment.Model.Staff;
import Assignment.Model.Admin;
import Assignment.Model.Member;


public class PersonDAO {

    // Read all persons from database
    public static ArrayList<Person> readAll() {
        ArrayList<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("userID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");
                String password = rs.getString("password");
                String role = rs.getString("role");

                Person person = null;
                if ("staff".equalsIgnoreCase(role)) {
                    person = new Staff(id, password, name, email, phoneNo);
                } else if ("admin".equalsIgnoreCase(role)) {
                    person = new Admin(id, password, name, email, phoneNo);
                }

                if (person != null) {
                    persons.add(person);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return persons;
    }

    // Find person by ID
    public static Person findById(String id) {
        String sql = "SELECT * FROM person WHERE userID = ?";
        Person person = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");
                String password = rs.getString("password");
                String role = rs.getString("role");

                if ("Staff".equalsIgnoreCase(role)) {
                    person = new Staff(id, password, name, email, phoneNo);
                } else if ("Admin".equalsIgnoreCase(role)) {
                    person = new Admin(id, password, name, email, phoneNo);
                } else if ("Member".equalsIgnoreCase(role)) {
                    person = new Member(id, name, email, phoneNo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }

    public static Person authenticate(String id, String password) {
        String sql = "SELECT * FROM person WHERE userID = ? AND password = ?";
        Person person = null;

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNo = rs.getString("phoneNumber");
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");

                if (dbPassword != null && dbPassword.equals(password)) {
                    if ("staff".equalsIgnoreCase(role)) {
                        person = new Staff(id, dbPassword, name, email, phoneNo);
                    } else if ("admin".equalsIgnoreCase(role)) {
                        person = new Admin(id, dbPassword, name, email, phoneNo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }

    // Insert new person
    public static boolean insert(Person person, String role, String password) {
        String sql = "INSERT INTO person (userID, name, email, phoneNumber, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getID());
            stmt.setString(2, person.getName());
            stmt.setString(3, person.getEmail());
            stmt.setString(4, person.getPhoneNo());
            stmt.setString(5, password);
            stmt.setString(6, role);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update person
    public static boolean update(Person person) {
        String sql = "UPDATE person SET name = ?, email = ?, phoneNumber = ? WHERE userID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getName());
            stmt.setString(2, person.getEmail());
            stmt.setString(3, person.getPhoneNo());
            stmt.setString(4, person.getID());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update password
    public static boolean updatePassword(String id, String newPassword) {
        String sql = "UPDATE person SET password = ? WHERE userID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, id);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete person
    public static boolean delete(String id) {
        String sql = "DELETE FROM person WHERE userID = ?";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get next ID for Staff or Admin
    public static String getNextID(String role) {
        String prefix = role.equalsIgnoreCase("Staff") ? "S" : "A";
        String sql = "SELECT userID FROM person WHERE role = ? ORDER BY userID DESC LIMIT 1";

        try (Connection conn = DBTest.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lastId = rs.getString("id");
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                return prefix + String.format("%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefix + "001";
    }
}