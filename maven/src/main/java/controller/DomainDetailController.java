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
import javafx.css.Rule;
import javafx.event.ActionEvent;

public class DomainDetailController extends BaseController {

    private RuleDomain editDomain;

    @FXML
    private TextField name;

    @FXML
    private TextArea description;

    @FXML
    private Button btnDel;

    @FXML
    private void onIsbnChanged(ActionEvent e) {
        System.out.println(e);
    }

    public DomainDetailController() {
        super();
    }

    public void setEditDomain(IRuleDomain dom) {
        this.editDomain = (RuleDomain)dom;

        name.setText(editDomain.getName());
        description.setText(editDomain.getDescription());

        btnDel.setVisible(true);
        name.setDisable(true);
    }

    private boolean checkInputData() {
        if (name.getText() == null) {
            showError("Name benötigt.");
            return false;
        }
        return true;
    }

    private RuleDomain getDomainFromFields() {
        return new RuleDomain(name.getText(), description.getText());
    }

    private boolean saveData() {
        if (!checkInputData()) {
            return false;
        }

        RuleDomain newDomain = getDomainFromFields();

        if (this.editDomain == null) {
            DataAccessObject.getInstance().createDomain(newDomain);
        } else {
            newDomain.setName(editDomain.getName());
            DataAccessObject.getInstance().modifyDomain(newDomain);
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
        if (askDelete(getDomainFromFields())) 
        {
            ((Stage)name.getScene().getWindow()).close();
        }
    }

    public void initialize(){
        
    }
}