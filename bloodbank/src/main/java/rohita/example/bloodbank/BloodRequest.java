package rohita.example.bloodbank;

import java.sql.Timestamp;

public class BloodRequest {

    public int id;
    public int hospital_id;
    public String blood_group;
    public int units;
    public String urgency;   // "normal" or "emergency"
    public String status;    // "open", "fulfilled", "cancelled"
    public Timestamp created_at;

}
