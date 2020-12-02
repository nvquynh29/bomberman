package uet.oop.bomberman.otherScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

public class ChooseLevelController {

    @FXML
    private TextField txt;

    @FXML
    private Button button;

    @FXML
    void handleButton(ActionEvent event) {
        String level = "Level" + txt.getText();
        BombermanGame.nextGame(level);
        Stage border = (Stage) txt.getScene().getWindow();
        border.close();
    }

}
