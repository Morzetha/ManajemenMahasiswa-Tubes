import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginController {

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername;

    @FXML
    void LoginFunction(ActionEvent event) throws IOException{
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        Parent dashboardParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (username.equals("Mahasiswa2023") && password.equals("MHS2023")) {
            stage.setScene(dashboardScene);
            stage.show();
            
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Username atau password tidak valid!");
            alert.setContentText("Silahkan cek kembali username atau password anda!");
            alert.showAndWait();
        }
    }

}
