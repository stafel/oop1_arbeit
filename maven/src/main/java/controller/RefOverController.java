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

public class RefOverController{
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

    public void initialize(){

        System.out.println(this.refTable);

        observableReferences = FXCollections.observableArrayList(
            new Reference("Test", new Source("GURPS"), new RuleDomain("Repairing"), "1 - 4")
        );

        System.out.println(this.observableReferences);

        //references.getColumns()

        
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
    }
}
