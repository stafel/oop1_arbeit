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
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage("Editiere Referenz", true));
        showRefDetail(targetStage, ref);
    }

    private void startCreate() {
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage("Neue Referenz", true));
        showRefDetail(targetStage, null);
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

    @Override
    protected Stage getCurrentStage() {
        return (Stage)refTable.getScene().getWindow();
    }

    public void initialize(){
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
        
        refTable.setItems(DataAccessObject.getInstance().getAvailableReferences());

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
