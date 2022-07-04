package controller;

import model.DataAccessObject;
import model.ISource;
import model.SourceBook;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class BookSourceDetailController extends BaseController {
    // book source like all detail controlles validates input fields and does crud-operations with the help of the DAO

    private SourceBook editSource;

    @FXML
    private TextField name;

    @FXML
    private TextField author;

    @FXML
    private TextField isbn;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker year;

    @FXML
    private Button btnDel;

    private boolean validateIsbn() {
        if (isbn.getText().length()>0) {
            if (!DataAccessObject.getInstance().checkIsbnValid(isbn.getText())) {
                showError("Eingegebener ISBN ist ungültig.");
                return false;
            }
        }
        return true;
    }

    @FXML
    private void onIsbnChanged(ActionEvent e) {
        validateIsbn();
    }

    public BookSourceDetailController() {
        super();
    }

    public void setEditSource(ISource src) {
        if (src.getName().length() < 1)
        {
            return;
        }
        this.editSource = (SourceBook)src;

        name.setText(editSource.getName());
        author.setText(editSource.getAuthor());
        isbn.setText(editSource.getIsbn());
        description.setText(editSource.getDescription());
        year.setValue(editSource.getPublishDate());

        btnDel.setVisible(true);
        name.setDisable(true);
    }

    private boolean checkInputData() {
        if (name.getText() == null) {
            showError("Name benötigt.");
            return false;
        }
        if (year.getValue() == null) {
            showError("Erscheinungsjahr benötigt.");
            return false;
        }
        return validateIsbn();
    }

    private SourceBook getSourceFromFields() {
        return new SourceBook(name.getText(), description.getText(), author.getText(), year.getValue(), isbn.getText());
    }

    private boolean saveData() {
        if (!checkInputData()) {
            return false;
        }

        SourceBook newSource = getSourceFromFields();

        if (this.editSource == null) {
            DataAccessObject.getInstance().createSource(newSource);
        } else {
            newSource.setName(editSource.getName());
            DataAccessObject.getInstance().modifySource(newSource);
        }

        return true;
    }

    @FXML
    void onOkClicked(ActionEvent e) {
        if (saveData()) { // save successful we can leave page
            ((Stage)name.getScene().getWindow()).close();
        }
    }

    @FXML
    void onApplyClicked(ActionEvent e) {
        saveData();
    }

    @FXML @Override
    void onDeleteClicked(ActionEvent e) {
        if (askDelete(getSourceFromFields())) 
        {
            ((Stage)name.getScene().getWindow()).close();
        }
    }

    public void initialize(){
        if (editSource == null) {
            year.setValue(LocalDate.now());
        }
    }
}