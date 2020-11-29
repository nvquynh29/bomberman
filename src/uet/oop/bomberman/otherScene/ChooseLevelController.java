package uet.oop.bomberman.otherScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import uet.oop.bomberman.BombermanGame;

public class ChooseLevelController {

    @FXML
    private TextField txt;

    @FXML
    private Button button;

    @FXML
    void handleButton(ActionEvent event) {
        String level = txt.getText();
        BombermanGame.nextGame(level);
    }

}
