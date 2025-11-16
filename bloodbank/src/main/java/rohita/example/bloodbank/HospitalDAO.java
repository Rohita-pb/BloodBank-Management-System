package rohita.example.bloodbank;

import java.sql.*;

public class HospitalDAO {

    // Register a hospital
    public static boolean registerHospital(Hospital h) {
        String sql = "INSERT INTO hospitals (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = JdbcUtil.getConn();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, h.name);
            ps.setString(2, h.email);
            ps.setString(3, h.password);
            ps.setString(4, h.phone);
            ps.setString(5, h.address);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Login (email + password)
    public static Hospital loginHospital(String email, String password) {
        String sql = "SELECT * FROM hospitals WHERE email = ? AND password = ?";

        try (Connection c = JdbcUtil.getConn();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Hospital h = new Hospital();
                h.id = rs.getInt("id");
                h.name = rs.getString("name");
                h.email = rs.getString("email");
                h.phone = rs.getString("phone");
                h.address = rs.getString("address");
                return h;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Get hospital by ID (useful when creating requests)
    public static Hospital getHospitalById(int id) {
        String sql = "SELECT * FROM hospitals WHERE id = ?";

        try (Connection c = JdbcUtil.getConn();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Hospital h = new Hospital();
                h.id = rs.getInt("id");
                h.name = rs.getString("name");
                h.email = rs.getString("email");
                h.phone = rs.getString("phone");
                h.address = rs.getString("address");
                return h;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

