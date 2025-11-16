package rohita.example.bloodbank;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
@CrossOrigin("*")
public class HospitalController {

    // Register hospital
    @PostMapping("/register")
    public String registerHospital(@RequestBody Hospital h) {
        boolean ok = HospitalDAO.registerHospital(h);
        return ok ? "hospital_registered" : "error";
    }

    // Login hospital
    @PostMapping("/login")
    public Hospital login(@RequestBody LoginRequest req) {
        return HospitalDAO.loginHospital(req.email, req.password);
    }

    // Get hospital by id
    @GetMapping("/{id}")
    public Hospital getHospital(@PathVariable int id) {
        return HospitalDAO.getHospitalById(id);
    }
}

