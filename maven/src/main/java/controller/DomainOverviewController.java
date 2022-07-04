package controller;

import model.DataAccessObject;
import model.IRuleDomain;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TableRow;
import javafx.event.ActionEvent;

public class DomainOverviewController extends BaseController{
    @FXML
    private TableView<IRuleDomain> domainTable;
    @FXML
    private TableColumn<IRuleDomain,String> domName;
    @FXML
    private TableColumn<IRuleDomain,String> domDesc;

    public DomainOverviewController() {
        super();
    }

    private void startEdit(IRuleDomain dom) {
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage("Editiere Regelbereich", true));
        showDomainDetail(targetStage, dom);
    }

    private void startCreate() {
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage("Neuer Regelbereich", true));
        showDomainDetail(targetStage, null);
    }

    @FXML
    @Override
    void onCreateClicked(ActionEvent e) {
        startCreate();
    }

    @FXML
    @Override
    void onEditClicked(ActionEvent e) {
        startEdit(domainTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    @Override
    void onDeleteClicked(ActionEvent e) {
        IRuleDomain domItm = domainTable.getSelectionModel().getSelectedItem();
        askDelete(domItm);
    }

    @Override
    protected Stage getCurrentStage() {
        return (Stage)domainTable.getScene().getWindow();
    }

    public void initialize(){

        domName.setCellValueFactory(
            new PropertyValueFactory<IRuleDomain,String>("name")
        );

        domDesc.setCellValueFactory(
            new PropertyValueFactory<IRuleDomain,String>("description")
        );
        
        domainTable.setItems(DataAccessObject.getInstance().getAvailableDomains());

        // detect double click on row and get row
        domainTable.setRowFactory(tv -> {
            TableRow<IRuleDomain> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (! row.isEmpty()) {
                        IRuleDomain clickedDomain = row.getItem();
                        startEdit(clickedDomain);
                    } else {
                        startCreate();
                    }
                }
            });
            return row ;
        });

        // key events must be done on table and not row because row does not get key events
        domainTable.setOnKeyPressed(event -> {
            IRuleDomain selectedDomain = domainTable.getSelectionModel().getSelectedItem();
            if (selectedDomain != null) {
                if (event.getCode() == KeyCode.ENTER) {
                    startEdit(selectedDomain);
                }
                if (event.getCode() == KeyCode.DELETE) {
                    askDelete(selectedDomain);
                }
            } else {
                startCreate();
            }
        });
    }
}
