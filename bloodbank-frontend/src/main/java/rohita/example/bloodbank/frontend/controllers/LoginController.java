package rohita.example.bloodbank.frontend.controllers;

import java.net.http.HttpResponse;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rohita.example.bloodbank.frontend.HttpUtil;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    // change base if your backend port differs
    private final String base = "http://localhost:8080";

    @FXML
    public void onDonorLogin() { login("/api/donor/login"); }

    @FXML
    public void onHospitalLogin() { login("/api/hospital/login"); }

    private void login(String path) {
        statusLabel.setText("");
        String email = emailField.getText().trim();
        String pwd = passwordField.getText().trim();
        if (email.isEmpty() || pwd.isEmpty()) {
            statusLabel.setText("Enter email and password");
            return;
        }
        String json = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", escape(email), escape(pwd));

        new Thread(() -> {
            try {
                HttpResponse<String> resp = HttpUtil.postJson(base + path, json);
                if (resp.statusCode() == 200) {
                    if (path.contains("donor")) {
    openDashboard("/fxml/donor_dashboard.fxml", "Donor Dashboard");
} else {
    openDashboard("/fxml/hospital_dashboard.fxml", "Hospital Dashboard");
}

                } else {
                    updateStatus("Login failed: " + resp.statusCode() + " " + resp.body(), true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                updateStatus("Error: " + e.getMessage(), true);
            }
        }).start();
    }

    @FXML
    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Donor Register");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Cannot open register");
        }
    }

    private void updateStatus(String msg, boolean error) {
        javafx.application.Platform.runLater(() -> {
            statusLabel.setText(msg);
            statusLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        });
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
    private void openDashboard(String fxml, String title) {
    try {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxml));
        javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        updateStatus("Error opening dashboard", true);
    }
}
@FXML
public void openHospitalRegister() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hospital_register.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Hospital Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        statusLabel.setText("Cannot open hospital register page");
    }
}

}

