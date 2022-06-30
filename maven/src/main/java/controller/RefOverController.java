package controller;

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
import javafx.event.EventHandler;
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


    private ObservableList<IReference> observableReferences;

    public RefOverController() {
        super();
    }

    public void initialize(){

        observableReferences = FXCollections.observableArrayList(
            new Reference("Test", new Source("GURPS"), new RuleDomain("Repairing"), "1 - 4")
        );
        
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
        
        refTable.setItems(observableReferences);

        // detect double click on row and get row
        refTable.setRowFactory(tv -> {
            TableRow<IReference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
                     && event.getClickCount() == 2) {
        
                    IReference clickedRow = row.getItem();
                    System.out.println(clickedRow.getName());
                }
            });
            return row ;
        });
    }
}
