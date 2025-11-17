package rohita.example.bloodbank.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DonorDashboardController {

    @FXML private Label welcomeLabel;

    @FXML
    public void onViewProfile() {
        welcomeLabel.setText("Profile view coming soon...");
    }

    @FXML
    public void onToggleAvailability() {
        welcomeLabel.setText("Feature coming soon...");
    }

    @FXML
    public void onLogout() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();
    }
}
