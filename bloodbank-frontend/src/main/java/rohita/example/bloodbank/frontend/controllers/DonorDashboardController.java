package rohita.example.bloodbank.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DonorDashboardController {

    @FXML private Label status;

    @FXML
    public void onViewProfile() {
        status.setText("Profile view coming soon...");
    }

    @FXML
    public void onToggleAvailability() {
        status.setText("Feature coming soon...");
    }

    @FXML
    public void onLogout() {
        Stage stage = (Stage) status.getScene().getWindow();
        stage.close();
    }
}
