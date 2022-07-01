package controller;

import model.DataAccessObject;
import model.IReference;
import model.IRuleDomain;
import model.ISource;
import model.RuleDomain;
import model.Source;

import model.Reference;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class RefDetailController extends BaseController {
    private IReference editReference;

    @FXML
    private CheckBox editFlag;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<String> book;

    @FXML
    private ComboBox<String> domain;

    @FXML
    private TextField page;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnApply;

    private ObservableList<String> availableBooks;
    private ObservableList<String> availableDomains;

    public RefDetailController() {
        super();
    }

    private void setFieldsFromEditRef() {
        name.setText(editReference.getName());
        book.setValue(editReference.getSource());
        domain.setValue(editReference.getDomain());
        page.setText(editReference.getPage());
    }

    public void setEditReference(IReference ref) {
        //btnApply.setVisible(false); // hide apply button
        if (ref == null) {
            editFlag.setSelected(false);

            // hide delete because there is nothing to delete yet
            btnDel.setVisible(false);
        } else {
            editReference = ref;
            editFlag.setSelected(true);
            setFieldsFromEditRef();

            // disable editing of the name because this is our primary key
            name.setEditable(false);
            name.setDisable(true);
        }
    }

    private boolean checkInputData() {
        if (editFlag.isSelected()) {
            // we edit it the name can not change
        } else {
            if (!DataAccessObject.getInstance().ReferenceNameValid(name.getText())) {
                showError("Name existiert bereits.\nBitte anderen Namen wählen.");
                return false;
            }
        }
        if (book.getValue() == null) {
            showError("Buchreferenz benötigt.");
            return false;
        }
        if (domain.getValue() == null) {
            showError("Regelbereich benötigt.");
            return false;
        }
        if (page.getText().length() < 1) {
            showError("Seitenangabe benötigt.");
            return false;
        }
        return true;
    }

    private boolean saveData() {
        if (!checkInputData()) {
            return false;
        }

        if (editFlag.isSelected()) {
            DataAccessObject.getInstance().modifyReference(new Reference(name.getText(), DataAccessObject.getInstance().getSource(book.getValue()), DataAccessObject.getInstance().getDomain(domain.getValue()), page.getText()));
        } else {
            DataAccessObject.getInstance().createReference(new Reference(name.getText(), DataAccessObject.getInstance().getSource(book.getValue()), DataAccessObject.getInstance().getDomain(domain.getValue()), page.getText()));
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
        if (askDelete(new Reference(name.getText(), DataAccessObject.getInstance().getSource(book.getValue()), DataAccessObject.getInstance().getDomain(domain.getValue()), page.getText()))) 
        {
            ((Stage)name.getScene().getWindow()).close();
        }
    }

    public void initialize(){
        /* // converter fails?
        // converter to convert objects into string representations
        book.setConverter(
            new StringConverter<ISource>() {
                @Override
                public String toString(ISource object) {
                    return object.getName();
                }

                @Override
                public ISource fromString(String string) {
                    return new Source(string);
                }
            }
        );
        
        // converter to convert objects into string representations
        domain.setConverter(
            new StringConverter<IRuleDomain>() {
                @Override
                public String toString(IRuleDomain object) {
                    return object.getName();
                }

                @Override
                public IRuleDomain fromString(String string) {
                    return new RuleDomain(string);
                }
            }
        );
        */

        availableBooks = FXCollections.observableArrayList();
        availableDomains = FXCollections.observableArrayList();
        
        for (ISource book : DataAccessObject.getInstance().getAvailableSources()) {
            availableBooks.add(book.getName());
        }

        for (IRuleDomain dom : DataAccessObject.getInstance().getAvailableDomains()) {
            availableDomains.add(dom.getName());
        }

        book.setItems(availableBooks);
        domain.setItems(availableDomains);
    }
}