package controller;

import model.DataAccessObject;
import model.ISource;
import model.SourceBook;
import model.SourceWeb;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TableRow;
import javafx.event.ActionEvent;

public class SourceOverviewController extends BaseController{
    @FXML
    private TableView<ISource> sourceTable;
    @FXML
    private TableColumn<ISource,String> srcName;
    @FXML
    private TableColumn<ISource,String> srcDesc;
    @FXML
    private TableColumn<ISource,String> srcAuthor;
    @FXML
    private TableColumn<ISource,String> srcYear;

    public SourceOverviewController() {
        super();
    }

    private String getTitleForSource(ISource src) {
        if (src instanceof SourceBook) {
            return "Buchsource";
        }
        if (src instanceof SourceWeb) {
            return "Websource";
        }
        return "???";
    }

    private void startEdit(ISource src) {
        String stageTitle = "Editiere " + getTitleForSource(src);
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage(stageTitle, true));
        showSourceDetail(targetStage, src);
    }

    private void startCreate(ISource src) {
        String stageTitle = "Neue  " + getTitleForSource(src);
        Stage targetStage = selectEditStage(getCurrentStage(), generateSubstage(stageTitle, true));
        showSourceDetail(targetStage, src);
    }

    @FXML
    @Override
    void onCreateClicked(ActionEvent e) {
        startCreate(new SourceBook());
    }

    @FXML
    void onCreateWebClicked(ActionEvent e) {
        startCreate(new SourceWeb());
    }

    @FXML
    @Override
    void onEditClicked(ActionEvent e) {
        startEdit(sourceTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    @Override
    void onDeleteClicked(ActionEvent e) {
        ISource srcItm = sourceTable.getSelectionModel().getSelectedItem();
        askDelete(srcItm);
    }

    @Override
    protected Stage getCurrentStage() {
        return (Stage)sourceTable.getScene().getWindow();
    }

    public void initialize(){

        srcName.setCellValueFactory(
            new PropertyValueFactory<ISource,String>("name")
        );

        srcDesc.setCellValueFactory(
            new PropertyValueFactory<ISource,String>("description")
        );

        srcAuthor.setCellValueFactory(
            new PropertyValueFactory<ISource,String>("author")
        );

        srcYear.setCellValueFactory(
            new PropertyValueFactory<ISource,String>("year")
        );
        
        sourceTable.setItems(DataAccessObject.getInstance().getAvailableSources());

        // detect double click on row and get row
        sourceTable.setRowFactory(tv -> {
            TableRow<ISource> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (! row.isEmpty()) {
                        ISource clickedSource = row.getItem();
                        startEdit(clickedSource);
                    } else {
                        startCreate(new SourceBook());
                    }
                }
            });
            return row ;
        });

        // key events must be done on table and not row because row does not get key events
        sourceTable.setOnKeyPressed(event -> {
            ISource selectedSrc = sourceTable.getSelectionModel().getSelectedItem();
            if (selectedSrc != null) {
                if (event.getCode() == KeyCode.ENTER) {
                    startEdit(selectedSrc);
                }
                if (event.getCode() == KeyCode.DELETE) {
                    askDelete(selectedSrc);
                }
            } else {
                startCreate(new SourceBook());
            }
        });
    }
}
