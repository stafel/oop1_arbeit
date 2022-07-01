package controller;

import model.DataAccessObject;
import model.IReference;
import model.Reference;
import model.Source;
import model.RuleDomain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TableRow;
import javafx.event.ActionEvent;

public class RefOverController extends BaseController{
    private DataAccessObject dao;

    @FXML
    private TableView<IReference> refTable;
    @FXML
    private TableColumn<IReference,String> refName;
    @FXML
    private TableColumn<IReference,String> refBook;
    @FXML
    private TableColumn<IReference,String> refDomain;
    @FXML
    private TableColumn<IReference,String> refPage;

    public RefOverController() {
        super();
    }

    private void startEdit(IReference ref) {
        showRefDetail(generateSubstage("Editiere Referenz", true), ref);
    }

    private void startCreate() {
        //showRefDetail((Stage)refTable.getScene().getWindow());
        showRefDetail(generateSubstage("Neue Referenz", true), null);
    }

    private void askDelete(IReference ref) {
        if (askYesNo("Eintrag '" + ref.getName() + "' wirklich löschen?") == true) {
            dao.deleteReference(ref);
        }
    }

    @FXML
    @Override
    void onCreateClicked(ActionEvent e) {
        startCreate();
    }

    @FXML
    @Override
    void onEditClicked(ActionEvent e) {
        startEdit(refTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    @Override
    void onDeleteClicked(ActionEvent e) {
        IReference refItm = refTable.getSelectionModel().getSelectedItem();
        askDelete(refItm);
    }

    public void initialize(){

        // initialize DAO
        dao = DataAccessObject.getInstance();
        
        refName.setCellValueFactory(
            new PropertyValueFactory<IReference,String>("name")
        );

        refDomain.setCellValueFactory(
            new PropertyValueFactory<IReference,String>("domain")
        );

        refBook.setCellValueFactory(
            new PropertyValueFactory<IReference,String>("source")
        );

        refPage.setCellValueFactory(
            new PropertyValueFactory<IReference,String>("page")
        );
        
        refTable.setItems(dao.getAvailableReferences());

        // detect double click on row and get row
        refTable.setRowFactory(tv -> {
            TableRow<IReference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (! row.isEmpty()) {
                        IReference clickedRef = row.getItem();
                        startEdit(clickedRef);
                    } else {
                        startCreate();
                    }
                }
            });
            return row ;
        });

        // key events must be done on table and not row because row does not get key events
        refTable.setOnKeyPressed(event -> {
            IReference selectedRef = refTable.getSelectionModel().getSelectedItem();
            if (selectedRef != null) {
                if (event.getCode() == KeyCode.ENTER) {
                    startEdit(selectedRef);
                }
                if (event.getCode() == KeyCode.DELETE) {
                    askDelete(selectedRef);
                }
            } else {
                startCreate();
            }
        });
    }
}
