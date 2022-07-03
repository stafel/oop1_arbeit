package controller;

import model.DataAccessObject;
import model.IReference;
import model.IRuleDomain;
import model.ISource;
import model.RuleDomain;
import model.Source;
import model.SourceBook;
import model.SourceWeb;
import model.Reference;

import java.net.URL;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
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

public class WebSourceDetailController extends BaseController {

    private SourceWeb editSource;

    @FXML
    private TextField name;

    @FXML
    private TextField author;

    @FXML
    private TextField website;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker lastVisit;

    @FXML
    private Button btnDel;

    private boolean validateWebsite() {
        if (website.getText().length()<1) {
            return false;
        }
        String url = website.getText().trim();
        if (!url.startsWith("http")) {
            url = "https://" + url; // easy fix for forgotten protocol
        }
        try {
            URL testUrl = new URL(url);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public WebSourceDetailController() {
        super();
    }

    public void setEditSource(ISource src) {
        if (src.getName().length() < 1)
        {
            return;
        }

        this.editSource = (SourceWeb)src;

        name.setText(editSource.getName());
        author.setText(editSource.getAuthor());
        website.setText(editSource.getWebsite());
        description.setText(editSource.getDescription());
        lastVisit.setValue(editSource.getPublishDate());

        btnDel.setVisible(true);
        name.setDisable(true);
    }

    private boolean checkInputData() {
        if (name.getText() == null) {
            showError("Name benötigt.");
            return false;
        }
        if (lastVisit.getValue() == null) {
            showError("Letztes Aufrufdatum benötigt.");
            return false;
        }
        if (!validateWebsite()) {
            showError("Website URL nicht gültig.");
            return false;
        }
        return true;
    }

    private SourceWeb getSourceFromFields() {
        return new SourceWeb(name.getText(), description.getText(), author.getText(), lastVisit.getValue(), website.getText());
    }

    private boolean saveData() {
        if (!checkInputData()) {
            return false;
        }

        SourceWeb newSource = getSourceFromFields();

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
            lastVisit.setValue(LocalDate.now());
        }
    }
}