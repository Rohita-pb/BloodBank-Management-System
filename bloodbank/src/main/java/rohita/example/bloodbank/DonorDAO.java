package rohita.example.bloodbank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    public static boolean registerDonor(Donor d) {
        String sql = "INSERT INTO donors (name,email,password,phone,blood_group,hemoglobin,is_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = JdbcUtil.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.name);
            ps.setString(2, d.email);
            ps.setString(3, d.password);
            ps.setString(4, d.phone);
            ps.setString(5, d.blood_group);
            ps.setDouble(6, d.hemoglobin);
            ps.setBoolean(7, d.is_available);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Donor findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM donors WHERE email=? AND password=?";
        try (Connection c = JdbcUtil.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Donor d = new Donor();
                d.id = rs.getInt("id");
                d.name = rs.getString("name");
                d.email = rs.getString("email");
                d.blood_group = rs.getString("blood_group");
                d.hemoglobin = rs.getDouble("hemoglobin");
                d.is_available = rs.getBoolean("is_available");
                return d;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Find donors matching a blood group and minimum hemoglobin
    public static List<Donor> findEligibleDonors(String bloodGroup, double minHb) {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group=? AND hemoglobin>=? AND is_available=1";
        try (Connection c = JdbcUtil.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ps.setDouble(2, minHb);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Donor d = new Donor();
                d.id = rs.getInt("id");
                d.name = rs.getString("name");
                d.email = rs.getString("email");
                d.blood_group = rs.getString("blood_group");
                d.hemoglobin = rs.getDouble("hemoglobin");
                d.is_available = rs.getBoolean("is_available");
                list.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

