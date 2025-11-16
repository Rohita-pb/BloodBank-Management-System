package rohita.example.bloodbank;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donor")
@CrossOrigin("*")
public class DonorController {

    // Register donor
    @PostMapping("/register")
    public String register(@RequestBody Donor d) {
        boolean ok = DonorDAO.registerDonor(d);
        return ok ? "registered" : "error";
    }

    // Login donor
    @PostMapping("/login")
    public Donor login(@RequestBody LoginRequest req) {
        return DonorDAO.findByEmailAndPassword(req.email, req.password);
    }
}
