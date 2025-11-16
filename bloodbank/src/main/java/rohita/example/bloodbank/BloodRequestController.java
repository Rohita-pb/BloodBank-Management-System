package rohita.example.bloodbank;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/request")
@CrossOrigin("*")
public class BloodRequestController {

    // Create a request from hospital
    @PostMapping("/create/{hospitalId}")
    public String createRequest(@PathVariable int hospitalId, @RequestBody RequestDTO dto) {

        boolean ok = BloodRequestDAO.createRequest(
                hospitalId,
                dto.blood_group,
                dto.units,
                dto.urgency
        );

        return ok ? "request_created" : "error";
    }

    // Get all open requests
    @GetMapping("/open")
    public List<BloodRequest> openRequests() {
        return BloodRequestDAO.getOpenRequests();
    }

    // Match donors (optional feature)
    @GetMapping("/match-donors")
    public List<Donor> matchDonors(@RequestParam String bloodGroup, @RequestParam double minHb) {
        return DonorDAO.findEligibleDonors(bloodGroup, minHb);
    }
}

