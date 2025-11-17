package rohita.example.bloodbank.frontend.controllers;

import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rohita.example.bloodbank.frontend.HttpUtil;

public class HospitalDashboardController {

    @FXML private TextField searchBloodGroup;
    @FXML private ListView<String> donorList;

    private final String base = "http://localhost:8080";
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onSearch() {
        String bg = searchBloodGroup.getText().trim();
        if (bg.isEmpty()) {
            donorList.getItems().clear();
            donorList.getItems().add("Enter a blood group.");
            return;
        }

        new Thread(() -> {
            try {
                String encoded = java.net.URLEncoder.encode(bg, java.nio.charset.StandardCharsets.UTF_8);
                HttpResponse<String> resp =
                    HttpUtil.get(base + "/api/hospital/search?blood_group=" + encoded);

                String body = resp.body();
                Platform.runLater(() -> {
                    try {
                        // Parse JSON array response into list of donors
                        java.util.List<java.util.Map<String, Object>> donors = 
                            mapper.readValue(body, new TypeReference<java.util.List<java.util.Map<String, Object>>>(){});
                        
                        donorList.getItems().clear();
                        
                        if (donors.isEmpty()) {
                            donorList.getItems().add("No donors available with blood type: " + bg);
                        } else {
                            for (java.util.Map<String, Object> donor : donors) {
                                String displayText = String.format(
                                    "Name: %s | Email: %s | Phone: %s | Hb: %s",
                                    donor.get("name"),
                                    donor.get("email"),
                                    donor.get("phone"),
                                    donor.get("hemoglobin")
                                );
                                donorList.getItems().add(displayText);
                            }
                        }
                    } catch (Exception parseEx) {
                        donorList.getItems().clear();
                        donorList.getItems().add("Error parsing response: " + parseEx.getMessage());
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> donorList.getItems().setAll("Error: " + e.getMessage()));
            }
        }).start();
    }

    @FXML
    public void onLogout() {
        Stage stage = (Stage) donorList.getScene().getWindow();
        stage.close();
    }
    
}
