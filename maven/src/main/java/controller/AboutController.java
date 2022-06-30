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
        infotext.setText("Referenzverwaltung\nVersion: 0.1\nDatum: 30.06.2022\n\nAuthor: Rafael Stauffer");
    }
}
