package rohita.example.bloodbank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {

    public static boolean createRequest(int hospitalId, String bloodGroup, int units, String urgency) {
        String sql = "INSERT INTO blood_requests (hospital_id,blood_group,units,urgency) VALUES (?,?,?,?)";
        try (Connection c = JdbcUtil.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, hospitalId);
            ps.setString(2, bloodGroup);
            ps.setInt(3, units);
            ps.setString(4, urgency);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<BloodRequest> getOpenRequests() {
        String sql = "SELECT * FROM blood_requests WHERE status='open' ORDER BY created_at DESC";
        List<BloodRequest> list = new ArrayList<>();
        try (Connection c = JdbcUtil.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BloodRequest br = new BloodRequest();
                br.id = rs.getInt("id");
                br.hospital_id = rs.getInt("hospital_id");
                br.blood_group = rs.getString("blood_group");
                br.units = rs.getInt("units");
                br.urgency = rs.getString("urgency");
                br.status = rs.getString("status");
                list.add(br);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

