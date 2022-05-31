package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputVorname;
    @FXML
    private Button v1Bearbeiten;
    @FXML
    private Button v2Bearbeiten;
    @FXML 
    private Button saveBtn;
    @FXML
    private Label v1Name;
    @FXML
    private Label v1Vorname;
    @FXML
    private Label v2Name;
    @FXML
    private Label v2Vorname;

    private Label currentEditName;
    private Label currentEditVorname;

    private void moveDataToEdit(Label name, Label vorname) {
        inputName.setText(name.getText());
        inputVorname.setText(vorname.getText());
        currentEditName = name;
        currentEditVorname = vorname;

        inputName.setDisable(false);
        inputVorname.setDisable(false);
        saveBtn.setDisable(false);
    }

    private void moveEditBackToLabel() {
        if (currentEditName != null) {
            currentEditName.setText(inputName.getText());
            currentEditVorname.setText(inputVorname.getText());
        }
    }

    public void load(javafx.event.ActionEvent e) {
        if (e.getSource() == v1Bearbeiten) {
            moveDataToEdit(v1Name, v1Vorname);
        } else {
            moveDataToEdit(v2Name, v2Vorname);
        }
    }

    public void save(javafx.event.ActionEvent e) {
        moveEditBackToLabel();
    }
}
