import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Student";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static Connection connectStudentDB(){
        try {
            return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent rootParent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(rootParent);

        primaryStage.setTitle("Sistem Manajemen Mahasiswa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
