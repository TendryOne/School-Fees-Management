package Views;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Model.SchoolFees;
import Model.Student;
import controller.SchoolFeesController;
import controller.StudentController;
import controller.TheMainAppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TheMainAppViews implements Initializable {
    Student student;
    Integer index;
    Boolean UPDATE = false;
    SchoolFees SchoolFees;

    StudentController studentController = new StudentController(student);
    SchoolFeesController schoolFeesController = new SchoolFeesController(SchoolFees);

    private String login;
    @FXML
    private Label labelLogin, titleAddLabel, labelClassRank, labelFirstname, labelId, labelName;

    @FXML
    private TableColumn<Student, String> firstnameStudentCol;

    @FXML
    private TableColumn<Student, Integer> idStudentCol;

    @FXML
    private TableColumn<Student, String> nameStudentCol;

    @FXML
    private TableColumn<Student, String> rankStudentCol;

    @FXML
    private TableView<?> tableViewStudent;

    @FXML
    private AnchorPane schoolFeesForm;

    @FXML
    private Button linktoAddStudent, linktoStudentList, DeleteStudentButton, UpdateStudentButton, buttonAdd,
            schoolFeesStudentButton;

    @FXML
    private AnchorPane studentContainer;

    @FXML
    private AnchorPane addStudentContainer, schoolFeesContainer;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<String> comboList;

    @FXML
    private TableView<SchoolFees> TableViewSchoolFees;

    @FXML
    private TableColumn<SchoolFees, Integer> amountCol;

    @FXML
    private TableColumn<SchoolFees, Date> datePaymentCol;

    @FXML
    private TableColumn<SchoolFees, Integer> IdSchoolFeesCol;

    @FXML
    public void SetMainAppViews(String Login) throws IOException {
        Stage secondStage = new Stage();

        Color color = Color.web("#222f3e");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/TheMainApp.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/MainAppScene").toExternalForm());
        secondStage.setScene(scene);
        secondStage.setResizable(false);
        secondStage.setTitle(Login + "[Administrateur]");
        secondStage.getIcons().add(new Image("file:./icon/icon.png"));
        secondStage.show();

    }

    @FXML
    public void SetLabelLogin(String login) {
        labelLogin.setText(login);
    }

    @FXML
    void OpenStudentAdd(ActionEvent event) {
        studentContainer.setVisible(false);
        addStudentContainer.setVisible(true);
        schoolFeesContainer.setVisible(false);
        schoolFeesForm.setVisible(false);
        linktoAddStudent.getStyleClass().add("button-active");
        linktoStudentList.getStyleClass().remove("button-active");
        buttonAdd.setText("Ajouter");
        titleAddLabel.setText("Ajout d'Etudiant");
        tfName.setText("");
        tfFirstname.setText("");
        comboList.getSelectionModel().clearSelection();

    }

    @FXML
    void OpenStudentList(ActionEvent event) {
        studentContainer.setVisible(true);
        addStudentContainer.setVisible(false);
        schoolFeesContainer.setVisible(false);
        schoolFeesForm.setVisible(false);
        linktoAddStudent.getStyleClass().remove("button-active");
        linktoStudentList.getStyleClass().add("button-active");
        SchoolFeesList.clear();

    }

    ObservableList StudentList = FXCollections.observableArrayList();
    ObservableList comboObsList = FXCollections.observableArrayList("L1G1", "L1G2", "L2IDEV", "L2RSI", "L3IDEV",
            "L3RSI", "M1IDEV", "M1RSI", "M2IDEV", "M2RSI");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StudentList = studentController.GetStudent();
        idStudentCol.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        nameStudentCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstnameStudentCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        rankStudentCol.setCellValueFactory(new PropertyValueFactory<>("classRank"));
        comboList.setItems(comboObsList);
        ;

        tableViewStudent.setItems(StudentList);
    }

    public ObservableList<Student> RefreshList() {
        StudentList.clear();
        StudentList = studentController.GetStudent();
        return StudentList;
    }

    @FXML
    void AddStudent(ActionEvent event) {
        if (UPDATE == false) {
            studentController.addStudent(tfName.getText(), tfFirstname.getText(),
                    comboList.getSelectionModel().getSelectedItem());
            StudentList = RefreshList();
            tfName.setText("");
            tfFirstname.setText("");
            comboList.getSelectionModel().clearSelection();
            addStudentContainer.setVisible(false);
            linktoAddStudent.getStyleClass().remove("button-active");
            linktoStudentList.getStyleClass().add("button-active");
            studentContainer.setVisible(true);
            tableViewStudent.setItems(StudentList);
            SchoolFeesList.clear();
        } else {
            studentController.UpdateStudent(idStudentCol.getCellData(index), tfName.getText(), tfFirstname.getText(),
                    comboList.getSelectionModel().getSelectedItem());
            StudentList = RefreshList();
            tfName.setText("");
            tfFirstname.setText("");
            comboList.getSelectionModel().clearSelection();
            addStudentContainer.setVisible(false);
            linktoAddStudent.getStyleClass().remove("button-active");
            linktoStudentList.getStyleClass().add("button-active");
            studentContainer.setVisible(true);
            tableViewStudent.setItems(StudentList);

            UPDATE = false;
        }

    }

    @FXML
    void selectStudent(MouseEvent event) {
        index = tableViewStudent.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            DeleteStudentButton.setDisable(false);
            UpdateStudentButton.setDisable(false);
            schoolFeesStudentButton.setDisable(false);
        }

    }

    @FXML
    void UpdateStudent(ActionEvent event) {
        UPDATE = true;
        tfName.setText(nameStudentCol.getCellData(index).toString());
        tfFirstname.setText(firstnameStudentCol.getCellData(index).toString());
        comboList.getSelectionModel().select(rankStudentCol.getCellData(index));
        addStudentContainer.setVisible(true);
        studentContainer.setVisible(false);
        linktoStudentList.getStyleClass().remove("button-active");
        buttonAdd.setText("Modifier");
        titleAddLabel.setText("Modification d'un Etudiant");
    }

    @FXML
    void DeleteStudent(ActionEvent event) {
        studentController.DeleteStudent(idStudentCol.getCellData(index));
        StudentList = RefreshList();
        tableViewStudent.setItems(StudentList);
        DeleteStudentButton.setDisable(true);
        UpdateStudentButton.setDisable(true);
        schoolFeesStudentButton.setDisable(true);
    }

    ObservableList<SchoolFees> SchoolFeesList = FXCollections.observableArrayList();

    public ObservableList<SchoolFees> RefreshListFees(Integer idStudent) {
        SchoolFeesList.clear();
        SchoolFeesList = schoolFeesController.GetSchoolFeesByStudentId(idStudent);
        return SchoolFeesList;
    }

    @FXML
    void OpenSchoolFees(ActionEvent event) {

        studentContainer.setVisible(false);
        schoolFeesContainer.setVisible(true);
        labelId.setText(idStudentCol.getCellData(index).toString());
        labelFirstname.setText(firstnameStudentCol.getCellData(index).toString());
        labelName.setText(nameStudentCol.getCellData(index).toString());
        labelClassRank.setText(rankStudentCol.getCellData(index).toString());
        linktoStudentList.getStyleClass().remove("button-active");
        SchoolFeesList = schoolFeesController.GetSchoolFeesByStudentId(idStudentCol.getCellData(index));
        if (!SchoolFeesList.isEmpty()) {
            datePaymentCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            IdSchoolFeesCol.setCellValueFactory(new PropertyValueFactory<>("idSchoolFees"));

            TableViewSchoolFees.setItems(SchoolFeesList);
        }

    }

    @FXML
    void ModifySchoolFees(ActionEvent event) {

    }

    @FXML
    void DeleteSchoolFees(ActionEvent event) {

    }

    @FXML
    void AddNewSchoolFees(ActionEvent event) {
        schoolFeesForm.setVisible(true);
        schoolFeesContainer.setVisible(false);

    }

    @FXML
    void CloseSchoolFeesRegister(ActionEvent event) {
        schoolFeesForm.setVisible(false);
        schoolFeesContainer.setVisible(true);
    }

    @FXML

    void RegisterSchoolFees(ActionEvent event) {

    }

}
