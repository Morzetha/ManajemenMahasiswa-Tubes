import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class DashboardController {

     @FXML
    private TableView<Student> tableDataMahasiswa;

    @FXML
    private TextField tfNim, tfNama, tfKelas, tfAngkatan;

    @FXML
    private ChoiceBox<String> cbJurusan;

    @FXML
    private RadioButton lakilaki, perempuan;

    @FXML
    private CheckBox verifikasiData;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    public void loadTable() {
        Connection conn = Student.connectDB();
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
                        rslt.getString("gender")

                    ));
                    
                }
                tableDataMahasiswa.setItems(studentList);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addDataMhs() {
        if (verifikasiData.isSelected()) {
            Connection conn = Student.connectDB();
            if (conn != null) {
                try {
                    String query = "INSERT INTO mahasiswa (nim,nama,kelas,angkatan,jurusan,gender) VALUES (?,?,?,?,?,?)";
                    PreparedStatement stmnt = conn.prepareStatement(query);
                    stmnt.setString(1, tfNim.getText());
                    stmnt.setString(2, tfNama.getText());
                    stmnt.setString(3, tfKelas.getText());
                    stmnt.setString(4, tfAngkatan.getText());
                    stmnt.setString(5, cbJurusan.getValue()); // ini soalnya spinner (dropdown) jd pake value ya teman teman mksi lof smgt
                    stmnt.setString(6, lakilaki.isSelected() ? "Laki-Laki" : "Perempuan"); // ini ada tickbox jd pake selected

                    stmnt.executeUpdate();
                    loadTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void editDataMhs() {
        Connection conn = Student.connectDB();
        if (conn != null) {
            try { 
                String query = "UPDATE mahasiswa SET nama = ?, angkatan = ?, jurusan = ?, gender = ? WHERE nim = ?";
                PreparedStatement stmnt = conn.prepareStatement(query);
                stmnt.setString(1, tfNim.getText());
                stmnt.setString(2, tfNama.getText());
                stmnt.setString(3, tfKelas.getText());
                stmnt.setString(4, tfAngkatan.getText());
                stmnt.setString(5, cbJurusan.getValue()); // ini soalnya spinner (dropdown) jd pake value ya teman teman mksi lof smgt
                stmnt.setString(6, lakilaki.isSelected() ? "Laki-Laki" : "Perempuan"); // ini ada tickbox jd pake selected
                
                stmnt.executeUpdate();
                loadTable();
            }catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDataMhs() {
        Connection conn = Student.connectDB();
        if (conn != null) {
            try { 
                String query = "DELETE FROM mahasiswa WHERE nim = ?"; // ini di get by nim ya mksi
                PreparedStatement stmnt = conn.prepareStatement(query);
                stmnt.setString(1, tfNim.getText());

                stmnt.executeUpdate();
                loadTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearDataMhs() {
        tfNim.clear();
        tfNama.clear();
        tfKelas.clear();
        tfAngkatan.clear();
        cbJurusan.setValue(null); // ini checkbox makanya clearnya, setvalue nya di kosongin
        lakilaki.setSelected(false);
        perempuan.setSelected(false);
        verifikasiData.setSelected(false);


    }
}
