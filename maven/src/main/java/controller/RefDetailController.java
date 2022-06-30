package controller;

import model.DataAccessObject;
import model.IRuleDomain;
import model.ISource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.collections.ObservableList;

public class RefDetailController extends BaseController {

    @FXML
    private TextField name;

    @FXML
    private ComboBox<ISource> book;

    @FXML
    private ComboBox<IRuleDomain> domain;

    @FXML
    private TextField page;

    private ObservableList<ISource> availableBooks;
    private ObservableList<IRuleDomain> availableDomains;

    public RefDetailController() {
        super();
    }

    public void initialize(){
        // converter to convert objects into string representations
        book.setConverter(
            new StringConverter<ISource>() {
                @Override
                public String toString(ISource object) {
                    return object.getName();
                }

                @Override
                public ISource fromString(String string) {
                    return null;
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
                    return null;
                }
            }
        );

        // initialize DAO
        DataAccessObject dao = DataAccessObject.getInstance();

        availableBooks = FXCollections.observableArrayList();
        availableDomains = FXCollections.observableArrayList();

        for (ISource book : dao.getAvailableSources()) {
            availableBooks.add(book);
        }

        for (IRuleDomain dom : dao.getAvailableDomains()) {
            availableDomains.add(dom);
        }

        book.setItems(availableBooks);
        domain.setItems(availableDomains);
    }
}