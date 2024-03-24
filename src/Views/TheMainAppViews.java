package Views;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Optional;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    int indexSchoolFees;
    Boolean UPDATE = false;
    Boolean UPDATEFEES = false;
    SchoolFees SchoolFees;

    StudentController studentController = new StudentController(student);
    SchoolFeesController schoolFeesController = new SchoolFeesController(SchoolFees);

    private String login;
    @FXML
    private Label labelLogin, titleAddLabel, labelClassRank, labelFirstname, labelId, labelName, lbNameStudent,
            lbFirstnameStudent, lbClassRankStudent, lbIdStudent, labelTitleFees;

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
            schoolFeesStudentButton, buttonModifySchoolfees, buttonDeleteSchoolFees;

    @FXML
    private AnchorPane studentContainer;

    @FXML
    private AnchorPane addStudentContainer, schoolFeesContainer;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfName, tfAmount;

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

    void showAlertAndWait(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait(); // Cette ligne est bloquante
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText(
                "Vous allez perdre les donnees de frais de scolarite de cet etudiant , Voulez vous continuer la suppression ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            StudentList = RefreshList();
            tableViewStudent.setItems(StudentList);
            DeleteStudentButton.setDisable(true);
            UpdateStudentButton.setDisable(true);
            schoolFeesStudentButton.setDisable(true);
        } else {
            return;
        }

    }

    ObservableList<SchoolFees> SchoolFeesList = FXCollections.observableArrayList();

    public ObservableList<SchoolFees> RefreshListFees(Integer idStudent) {
        SchoolFeesList.clear();
        SchoolFeesList = schoolFeesController.GetSchoolFeesByStudentId(idStudent);
        if (!SchoolFeesList.isEmpty()) {
            datePaymentCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            IdSchoolFeesCol.setCellValueFactory(new PropertyValueFactory<>("idSchoolFees"));

            TableViewSchoolFees.setItems(SchoolFeesList);
        }
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
        UPDATEFEES = true;
        tfAmount.setText(amountCol.getCellData(indexSchoolFees).toString());

        schoolFeesForm.setVisible(true);
        schoolFeesContainer.setVisible(false);
        lbClassRankStudent.setText(labelClassRank.getText());
        lbFirstnameStudent.setText(labelFirstname.getText());
        lbNameStudent.setText(labelName.getText());
        lbIdStudent.setText(labelId.getText());
        labelTitleFees.setText("MODIFICATION DES FRAIS");
    }

    @FXML
    void DeleteSchoolFees(ActionEvent event) {
        schoolFeesController.DeleteSchoolFees(IdSchoolFeesCol.getCellData(indexSchoolFees));
        RefreshListFees(idStudentCol.getCellData(index));
        buttonDeleteSchoolFees.setDisable(true);
        buttonModifySchoolfees.setDisable(true);
    }

    @FXML
    void AddNewSchoolFees(ActionEvent event) {
        schoolFeesForm.setVisible(true);
        schoolFeesContainer.setVisible(false);
        lbClassRankStudent.setText(labelClassRank.getText());
        lbFirstnameStudent.setText(labelFirstname.getText());
        lbNameStudent.setText(labelName.getText());
        lbIdStudent.setText(labelId.getText());

    }

    @FXML
    void CloseSchoolFeesRegister(ActionEvent event) {
        schoolFeesForm.setVisible(false);
        schoolFeesContainer.setVisible(true);

    }

    @FXML

    void RegisterSchoolFees(ActionEvent event) {

        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);

        if (tfAmount.getText().matches("\\d+")) {
            int amount = Integer.parseInt(tfAmount.getText());
            int idStudent = Integer.parseInt(lbIdStudent.getText());
            if (UPDATEFEES == false) {

                schoolFeesController.RegisterSchoolFees(amount, date, idStudent);

                SchoolFeesList = RefreshListFees(idStudent);
                TableViewSchoolFees.setItems(SchoolFeesList);

                String message = "Les frais de scolarite de " + lbNameStudent.getText() + " "
                        + lbFirstnameStudent.getText()
                        + " ont ete enregistre avec succes";

                showAlert(AlertType.INFORMATION, "Frais de scolarite", message);
                schoolFeesForm.setVisible(false);
                schoolFeesContainer.setVisible(true);
                tfAmount.setText("");

            } else {
                System.out.println();
                schoolFeesController.UpdateSchoolFees(amount, IdSchoolFeesCol.getCellData(indexSchoolFees));

                SchoolFeesList = RefreshListFees(idStudent);
                TableViewSchoolFees.setItems(SchoolFeesList);

                String message = "Les frais de scolarite de " + lbNameStudent.getText() + " "
                        + lbFirstnameStudent.getText()
                        + " ont ete modifie avec succes";

                showAlert(AlertType.INFORMATION, "Frais de scolarite", message);
                schoolFeesForm.setVisible(false);
                schoolFeesContainer.setVisible(true);
                tfAmount.setText("");
                UPDATEFEES = false;
            }

            buttonDeleteSchoolFees.setDisable(true);
            buttonModifySchoolfees.setDisable(true);

        } else {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez entrer un montant exacte !!");
        }

    }

    @FXML
    void SelectSchoolFees(MouseEvent event) {
        indexSchoolFees = TableViewSchoolFees.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            buttonModifySchoolfees.setDisable(false);
            buttonDeleteSchoolFees.setDisable(false);
        }

    }

    public static void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
