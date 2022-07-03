package controller;

import model.DataAccessObject;
import model.IReference;
import model.IRuleDomain;
import model.ISource;
import model.RuleDomain;
import model.Source;
import model.SourceBook;

import model.Reference;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class BookSourceDetailController extends BaseController {

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

    @FXML
    private void onIsbnChanged(ActionEvent e) {
        System.out.println(e);
    }

    public BookSourceDetailController() {
        super();
    }

    public void setEditSource(ISource src) {
        this.editSource = (SourceBook)src;

        name.setText(editSource.getName());
        author.setText(editSource.getAuthor());
        isbn.setText(editSource.getIsbn());
        description.setText(editSource.getDescription());
        year.setValue(editSource.getPublishDate());

        btnDel.setVisible(true);
    }

    private boolean checkInputData() {
        if (name.getText() == null) {
            showError("Name benötigt.");
            return false;
        }
        return true;
    }

    private boolean saveData() {
        if (!checkInputData()) {
            return false;
        }

        System.out.println("save not implemented");

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
        if (askDelete((ISource)new SourceBook(name.getText(), "", "", LocalDate.of(2016, 1, 1), ""))) 
        {
            ((Stage)name.getScene().getWindow()).close();
        }
    }

    public void initialize(){
        
    }
}