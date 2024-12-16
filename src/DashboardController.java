import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TableView<Student> tableDataMahasiswa;

    @FXML
    private TableColumn<Student, String> namaColumn;

    @FXML
    private TableColumn<Student, String> nimColumn;

    @FXML
    private TableColumn<Student, String> jurusanColumn;

    @FXML
    private TableColumn<Student, String> kelasColumn;

    @FXML
    private TableColumn<Student, String> angkatanColumn;

    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TextField tfNim, tfNama, tfKelas, tfAngkatan;

    @FXML
    private ChoiceBox<String> cbJurusan;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton lakilaki, perempuan;

    @FXML
    private CheckBox verifikasiData;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableDataMahasiswa.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cbJurusan.getItems().addAll("SISFO", "IF", "DKV", "MBTI");

        cbJurusan.setValue("SISFO");

        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));
        jurusanColumn.setCellValueFactory(new PropertyValueFactory<>("jurusan"));
        kelasColumn.setCellValueFactory(new PropertyValueFactory<>("kelas"));
        angkatanColumn.setCellValueFactory(new PropertyValueFactory<>("angkatan"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadTable();
    }

    @FXML
    private void handleGenderSelection(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        String selectedGender = selectedRadioButton.getText();
        lakilaki.setSelected(false);
        perempuan.setSelected(false);
        if (selectedGender.equals("Laki-Laki")) {
            lakilaki.setSelected(true);
        } else if (selectedGender.equals("Perempuan")) {
            perempuan.setSelected(true);
        }
    }

    public void loadTable() {
        studentList.clear(); // Clear the list before loading new data
        Connection conn = Database.connectStudentDB();
        if (conn != null) {
            try {
                String query = "SELECT * FROM mahasiswa";
                PreparedStatement stmnt = conn.prepareStatement(query);
                ResultSet rslt = stmnt.executeQuery();

                while (rslt.next()) {
                    studentList.add(new Student(
                            rslt.getString("nim"),
                            rslt.getString("nama"),
                            rslt.getString("kelas"),
                            rslt.getString("angkatan"),
                            rslt.getString("jurusan"),
                            rslt.getString("gender")));
                }
                tableDataMahasiswa.setItems(studentList);
            } catch (Exception e) {
                showError("Error loading data", e.getMessage());
            }
        }
    }

    public void addDataMhs() {
        if (verifikasiData.isSelected()) {
            Connection conn = Database.connectStudentDB();
            if (conn != null) {
                try {
                    String query = "INSERT INTO mahasiswa (nim, nama, kelas, angkatan, jurusan, gender) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmnt = conn.prepareStatement(query);
                    stmnt.setString(1, tfNim.getText());
                    stmnt.setString(2, tfNama.getText());
                    stmnt.setString(3, tfKelas.getText());
                    stmnt.setString(4, tfAngkatan.getText());
                    stmnt.setString(5, cbJurusan.getValue());
                    stmnt.setString(6, lakilaki.isSelected() ? "Laki-Laki" : "Perempuan");

                    stmnt.executeUpdate();
                    showInfo("Success", "Data added successfully!");

                    clearDataMhs();
                    loadTable();
                } catch (Exception e) {
                    showError("Error adding data", e.getMessage());
                }
            }
        } else {
            showWarning("Verification Required", "Please verify the data before adding.");
        }
    }

    public void editDataMhs() {
        Connection conn = Database.connectStudentDB();
        if (verifikasiData.isSelected()) {

            if (conn != null) {
                try {
                    String query = "UPDATE mahasiswa SET nama = ?, kelas = ?, angkatan = ?, jurusan = ?, gender = ? WHERE nim = ?";
                    PreparedStatement stmnt = conn.prepareStatement(query);
                    stmnt.setString(1, tfNama.getText());
                    stmnt.setString(2, tfKelas.getText());
                    stmnt.setString(3, tfAngkatan.getText());
                    stmnt.setString(4, cbJurusan.getValue());
                    stmnt.setString(5, lakilaki.isSelected() ? "Laki-Laki" : "Perempuan");
                    stmnt.setString(6, tfNim.getText());

                    stmnt.executeUpdate();
                    showInfo("Success", "Data updated successfully!");

                    clearDataMhs();
                    loadTable();
                } catch (Exception e) {
                    showError("Error updating data", e.getMessage());
                }
            }
        } else {
            showWarning("Verification Required", "Please verify the data before adding.");
        }
    }

    public void deleteDataMhs() {
        Connection conn = Database.connectStudentDB();
        if (conn != null) {
            try {
                String query = "DELETE FROM mahasiswa WHERE nim = ?";
                PreparedStatement stmnt = conn.prepareStatement(query);
                stmnt.setString(1, tfNim.getText());

                stmnt.executeUpdate();
                showInfo("Success", "Data deleted successfully!");

                clearDataMhs();
                loadTable();
            } catch (Exception e) {
                showError("Error deleting data", e.getMessage());
            }
        }
    }

    public void clearDataMhs() {
        tfNim.clear();
        tfNama.clear();
        tfKelas.clear();
        tfAngkatan.clear();
        cbJurusan.setValue(null);
        lakilaki.setSelected(false);
        perempuan.setSelected(false);
        verifikasiData.setSelected(false);
    }

    @FXML
    private void handleTableDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Detect double-click
            Student selectedItem = tableDataMahasiswa.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                System.out.println(selectedItem.getNim());
                tfNim.setText(selectedItem.getNim());
                tfNama.setText(selectedItem.getNama());
                tfKelas.setText(selectedItem.getKelas());
                tfAngkatan.setText(selectedItem.getAngkatan());

                cbJurusan.setValue(selectedItem.getJurusan());

                if (selectedItem.getGender().equals("Laki-Laki")) {
                    lakilaki.setSelected(true);
                } else if (selectedItem.getGender().equals("Perempuan")) {
                    perempuan.setSelected(true);
                }
            }
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
