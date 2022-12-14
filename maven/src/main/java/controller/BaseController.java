package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DataAccessObject;
import model.IReference;
import model.IRuleDomain;
import model.ISource;
import model.SourceBook;
import model.SourceWeb;
import javafx.event.ActionEvent;

public class BaseController {
    // base controller implements reused buttons like aboutMe, exit and the menu bar functions

    @FXML
    private CheckMenuItem detailInMain;

    @FXML
    void onBackClicked(ActionEvent e) {
        Button btn = (Button)e.getSource();
        ((Stage)btn.getScene().getWindow()).close();
    }

    @FXML
    void onAboutMeClicked(ActionEvent e) {
        showAboutMe();
    }

    @FXML
    void onExitClicked(ActionEvent e) {
        javafx.application.Platform.exit(); // this closes all open windows of this program, not only this stage
    }

    @FXML
    void onCreateClicked(ActionEvent e) {
        System.out.println("not implemented yet");
    }

    @FXML
    void onEditClicked(ActionEvent e) {
        System.out.println("not implemented yet");
    }

    @FXML
    void onDeleteClicked(ActionEvent e) {
        System.out.println("not implemented yet");
    }

    // view tab changes: references, domains and sources
    @FXML
    void onChangeRefView(ActionEvent e) {
        showReferenceOverview(getCurrentStage());
    }

    @FXML
    void onChangeDomainView(ActionEvent e) {
        showDomainOverview(getCurrentStage());
    }

    @FXML
    void onChangeSourceView(ActionEvent e) {
        showSourceOverview(getCurrentStage());
    }

    protected Stage getCurrentStage() {
        // can not reliably set the stage so instead of a protected attribute with getter and setter we will overwrite and return just in time
        return null;
    }

    protected void showError(String errorMsg) {
        // just show an error alert with errorMsg
        Alert alrt = new Alert(AlertType.ERROR);
        alrt.setContentText(errorMsg);
        alrt.show();
    }

    protected boolean askYesNo(String question) {
        // simplified alert returns true if yes was chosen, all other options are false

        Alert alrt = new Alert(AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alrt.showAndWait();

        if (result.get() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean askDelete(IReference ref) {
        // first ask yes/no before deleting
        if (askYesNo("Eintrag '" + ref.getName() + "' wirklich l??schen?") == true) {
            DataAccessObject.getInstance().deleteReference(ref);
            return true;
        }
        return false;
    }

    protected boolean askDelete(ISource src) {
        // check if source is still used then ask yes/no before deleting
        if (DataAccessObject.getInstance().getReferencesForSource(src).size() > 0) {
            showError("Buch '" + src.getName() + "' wird referenziert. Zuerst Referenzen l??schen.");
            return false;
        }
        if (askYesNo("Eintrag '" + src.getName() + "' wirklich l??schen?") == true) {
            DataAccessObject.getInstance().deleteSource(src);
            return true;
        }
        return false;
    }

    protected boolean askDelete(IRuleDomain dom) {
        // check if domain is still used then ask yes/no before deleting
        if (DataAccessObject.getInstance().getReferencesForDomain(dom).size() > 0) {
            showError("Regelbereich '" + dom.getName() + "' wird referenziert. Zuerst Referenzen l??schen.");
            return false;
        }
        if (askYesNo("Eintrag '" + dom.getName() + "' wirklich l??schen?") == true) {
            DataAccessObject.getInstance().deleteDomain(dom);
            return true;
        }
        return false;
    }

    protected Stage generateSubstage(String title, boolean canResize) {
        Stage substage = new Stage();
        substage.setTitle(title);
        substage.setResizable(canResize);
        return substage;
    }

    private void showAboutMe() {
        Stage substage = new Stage();
        substage.setTitle("??ber Mich");
        substage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.class.getResource("/view/AboutView.fxml"));

            Scene scene = new Scene(loader.load());
            substage.setScene(scene);
            substage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showRefDetail(Stage targetStage, IReference ref) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.class.getResource("/view/ReferenceDetailView.fxml"));

            Scene scene = new Scene(loader.load());
            targetStage.setScene(scene);

            RefDetailController ctrl = loader.getController();
            ctrl.setEditReference(ref);

            targetStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showSourceDetail(Stage targetStage, ISource src) {
        // find out if we load a book or website to set the correct view
        try {
            FXMLLoader loader = new FXMLLoader();
            if (src instanceof SourceBook) {
                loader.setLocation(BaseController.class.getResource("/view/BookSourceDetailView.fxml"));
            }
            if (src instanceof SourceWeb) {
                loader.setLocation(BaseController.class.getResource("/view/WebSourceDetailView.fxml"));
            }

            Scene scene = new Scene(loader.load());
            targetStage.setScene(scene);

            if (src != null) {
                // yes I could refactor that better with another class but I will not, I am already way out of scope of this programming task
                if (src instanceof SourceBook) {
                    BookSourceDetailController ctrl = loader.getController();
                    ctrl.setEditSource(src);
                }
                if (src instanceof SourceWeb) {
                    WebSourceDetailController ctrl = loader.getController();
                    ctrl.setEditSource(src);
                }
            }

            targetStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showDomainDetail(Stage targetStage, IRuleDomain dom) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.class.getResource("/view/DomainDetailView.fxml"));

            Scene scene = new Scene(loader.load());
            targetStage.setScene(scene);

            if (dom != null) {
                DomainDetailController ctrl = loader.getController();
                ctrl.setEditDomain(dom);
            }

            targetStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showReferenceOverview(Stage targetStage) {
        showSceneOnStage(targetStage, "Referenzen ??bersicht", "/view/ReferenceOverviewView.fxml");
    }

    protected void showSourceOverview(Stage targetStage) {
        showSceneOnStage(targetStage, "Sourcen ??bersicht", "/view/SourceOverviewView.fxml");
    }

    protected void showDomainOverview(Stage targetStage) {
        showSceneOnStage(targetStage, "Bereich ??bersicht", "/view/DomainOverviewView.fxml");
    }

    private void showSceneOnStage(Stage targetStage, String title, String sceneURI) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.class.getResource(sceneURI));

            Scene scene = new Scene(loader.load());
            targetStage.setScene(scene);
            targetStage.setTitle(title);
            targetStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Stage selectEditStage(Stage overviewStage, Stage substage){
        // shim method to allow display of the detailView in the mainStage. currently unused
        if (detailInMain == null) { // fallback to substage if we do not have this button 
            return substage;
        }
        if (detailInMain.isSelected()) {
            return overviewStage;
        } else {
            return substage;
        }
    }
}
