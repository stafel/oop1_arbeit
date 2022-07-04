package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AboutController extends BaseController {
    // about me screen just show a big text area with version and usage info

    @FXML
    private TextArea infotext;

    public AboutController() {
        super();
    }

    public void initialize(){

        String textdump = """
                Referenzverwaltung
                Version: 0.3
                Datum: 03.07.2022
                Author: Rafael Stauffer

                Zur Verwaltung von Buchreferenzen.
                Alpha-Version! 
                - Kann noch keine permanente Datenspeicherung

                Schnell-Editiertasten:
                Enter/Doppelklick: Bearbeiten des gewählten Eintrags/Neuer Eintrag erstellen
                Del: Löschen des gewählten Eintrags

                View/Fenster:
                Referenzen: Primäre Referenzverwaltung
                Regelkategorien: Gruppieren der Referenzen
                Sourcen: Verwalten der Informationsbasis (Buch und Website)
                """;

        infotext.setText(textdump);
    }
}
