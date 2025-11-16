package rohita.example.bloodbank.frontend.controllers;

import java.net.http.HttpResponse;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rohita.example.bloodbank.frontend.HttpUtil;

public class RegisterController {

    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private TextField phone;
    @FXML private TextField bloodGroup;
    @FXML private TextField hemoglobin;
    @FXML private CheckBox isAvailable;
    @FXML private Label status;

    private final String base = "http://localhost:8080";

    @FXML
    public void onRegister() {
        status.setText("");

        try {
            String json = String.format(
                "{"
                    + "\"name\":\"%s\","
                    + "\"email\":\"%s\","
                    + "\"password\":\"%s\","
                    + "\"phone\":\"%s\","
                    + "\"blood_group\":\"%s\","
                    + "\"hemoglobin\":%s,"
                    + "\"is_available\":%s"
                + "}",
                escape(name.getText()),
                escape(email.getText()),
                escape(password.getText()),
                escape(phone.getText()),
                escape(bloodGroup.getText()),
                hemoglobin.getText(),
                isAvailable.isSelected()
            );

            new Thread(() -> {
                try {
                    HttpResponse<String> resp = HttpUtil.postJson(base + "/api/donor/register", json);
                    if (resp.statusCode() == 200) {
                        update("Registered successfully!", false);
                    } else {
                        update("Register failed: " + resp.body(), true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    update("Error: " + e.getMessage(), true);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            status.setText("Invalid input format");
        }
    }

    @FXML
    public void onCancel() {
        Stage s = (Stage) name.getScene().getWindow();
        s.close();
    }

    private void update(String msg, boolean err) {
        javafx.application.Platform.runLater(() -> {
            status.setText(msg);
            status.setStyle(err ? "-fx-text-fill:red;" : "-fx-text-fill:green;");
        });
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
