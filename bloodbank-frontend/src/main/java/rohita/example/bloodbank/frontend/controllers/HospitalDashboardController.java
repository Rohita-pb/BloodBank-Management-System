package rohita.example.bloodbank.frontend.controllers;

import java.net.http.HttpResponse;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rohita.example.bloodbank.frontend.HttpUtil;

public class HospitalDashboardController {

    @FXML private TextField searchField;
    @FXML private TextArea results;

    private final String base = "http://localhost:8080";

    @FXML
    public void onSearch() {
        String bg = searchField.getText().trim();
        if (bg.isEmpty()) {
            results.setText("Enter a blood group.");
            return;
        }

        new Thread(() -> {
            try {
                HttpResponse<String> resp =
                        HttpUtil.get(base + "/api/hospital/search?blood_group=" + bg);

                results.setText(resp.body());
            } catch (Exception e) {
                results.setText("Error: " + e.getMessage());
            }
        }).start();
    }

    @FXML
    public void onLogout() {
        Stage stage = (Stage) results.getScene().getWindow();
        stage.close();
    }
}
