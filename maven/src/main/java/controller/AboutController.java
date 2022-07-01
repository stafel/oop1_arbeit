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

                Zur Verwaltung von Buchreferenzen.
                Alpha-Version! 
                - Kann noch keine permanente Datenspeicherung
                - Bücher- und Bereich-Verwaltung noch nicht möglich

                Schnell-Editiertasten:
                Enter/Doppelklick: Bearbeiten des gewählten Eintrags/Neuer Eintrag erstellen
                Del: Löschen des gewählten Eintrags
                """;

        infotext.setText(textdump);
    }
}
