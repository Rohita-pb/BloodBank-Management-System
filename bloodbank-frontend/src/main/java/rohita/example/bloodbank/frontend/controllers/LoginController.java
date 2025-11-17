package rohita.example.bloodbank.frontend.controllers;

import java.net.http.HttpResponse;

import javafx.application.Platform;
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

    private final String base = "http://localhost:8080";

    // ------------------- LOGIN HANDLERS ----------------------

    @FXML
    public void onDonorLogin() {
        login("/api/donor/login", true);
    }

    @FXML
    public void onHospitalLogin() {
        login("/api/hospital/login", false);
    }

    private void login(String path, boolean isDonor) {
        statusLabel.setText("");

        String email = emailField.getText().trim();
        String pwd   = passwordField.getText().trim();

        if (email.isEmpty() || pwd.isEmpty()) {
            statusLabel.setText("Enter email and password");
            return;
        }

        String json = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                escape(email),
                escape(pwd)
        );

        new Thread(() -> {
            try {
                HttpResponse<String> resp =
                        HttpUtil.postJson(base + path, json);

                if (resp.statusCode() == 200) {
                    updateStatus("Login successful!", false);

                    // MUST run UI updates on FX thread
                    Platform.runLater(() -> {
                        if (isDonor)
                            openDashboard("/fxml/donor_dashboard.fxml", "Donor Dashboard");
                        else
                            openDashboard("/fxml/hospital_dashboard.fxml", "Hospital Dashboard");
                    });
                } else {
                    updateStatus("Login failed: " + resp.body(), true);
                }

            } catch (Exception e) {
                e.printStackTrace();
                updateStatus("Error: " + e.getMessage(), true);
            }
        }).start();
    }

    // ------------------- DASHBOARD LOADING ----------------------

    private void openDashboard(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Close login window
            Stage current = (Stage) emailField.getScene().getWindow();
            current.close();

        } catch (Exception e) {
            e.printStackTrace();
            updateStatus("Error opening dashboard", true);
        }
    }

    // ------------------- REGISTER PAGE OPEN ----------------------

    @FXML
    public void openRegister() {
        openNewWindow("/fxml/register.fxml", "Donor Register");
    }

    @FXML
    public void openHospitalRegister() {
        openNewWindow("/fxml/hospital_register.fxml", "Hospital Register");
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Cannot open page: " + title);
        }
    }

    // ------------------- UTILITIES ----------------------

    private void updateStatus(String msg, boolean error) {
        Platform.runLater(() -> {
            statusLabel.setText(msg);
            statusLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        });
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
