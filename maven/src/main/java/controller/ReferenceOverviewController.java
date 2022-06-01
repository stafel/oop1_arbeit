package controller;

import model.IReference;
import model.Reference;
import model.Source;
import model.RuleDomain;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ReferenceOverviewController {
    @FXML
    private ListView<IReference> references;

    public void initialize(){
        //references.setCellFactory

        references.getItems().add(new Reference(new Source(), new RuleDomain()));
    }
}
