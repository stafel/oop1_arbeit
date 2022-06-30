package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class BaseController {
    // base controller implements reused buttons like aboutMe and exit

    @FXML
    void onAboutMeClicked(ActionEvent e) {
        showAboutMe();
    }

    @FXML
    void onExitClicked(ActionEvent e) {
        javafx.application.Platform.exit(); // this closes all open windows of this program not only this stage
    }

    private void showAboutMe() {
        Stage substage = new Stage();
        substage.setTitle("Über Mich");
        substage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BaseController.class.getResource("../view/AboutView.fxml"));

            Scene scene = new Scene(loader.load());
            substage.setScene(scene);
            substage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
