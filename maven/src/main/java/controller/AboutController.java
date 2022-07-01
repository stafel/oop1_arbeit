package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AboutController extends BaseController {
    @FXML
    private TextArea infotext;

    public AboutController() {
        super();
    }

    public void initialize(){

        String textdump = """
                Referenzverwaltung
                Version: 0.2
                Datum: 01.07.2022
                Author: Rafael Stauffer


                Schnell-Editiertasten:
                Enter: Bearbeiten des gewählten elements
                Del: Löschen des gewählten elements
                """;

        infotext.setText(textdump);
    }
}
