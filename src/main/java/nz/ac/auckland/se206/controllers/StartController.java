package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.GameState.PlayTime;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for handling the start screen of the game. */
public class StartController {

  @FXML private Label gameNameLabel;
  @FXML private Label difficultyDescriptionLabel;
  @FXML private ToggleButton easyBtn;
  @FXML private ToggleButton mediumBtn;
  @FXML private ToggleButton hardBtn;
  @FXML private ToggleButton twoMinsBtn;
  @FXML private ToggleButton fourMinsBtn;
  @FXML private ToggleButton sixMinsBtn;
  @FXML private Button startGameBtn;

  private GameState gameState;

  /** Initializes the start screen and sets up the game state. */
  @FXML
  private void initialize() {
    this.gameState = GameState.getInstance();
  }

  /** Handles the event when the "Easy" button is clicked, setting the game difficulty to EASY. */
  @FXML
  private void onClickEasy() {
    // Set the game difficulty to EASY when the "Easy" button is clicked.
    GameState.difficulty = Difficulty.EASY;
  }

  /** Displays a hint for the EASY difficulty when the mouse enters the "Easy" button area. */
  @FXML
  private void onEasyEnter() {
    // Display a hint for the EASY difficulty when the mouse enters the "Easy" button area.
    difficultyDescriptionLabel.setText("You can ask for as many hints as you want!");
  }

  /** Clears the hint when the mouse exits the "Easy" button area. */
  @FXML
  private void onEasyExit() {
    difficultyDescriptionLabel.setText("");
  }

  /** Sets the game difficulty to MEDIUM when the "Medium" button is clicked. */
  @FXML
  private void onClickMedium() {
    GameState.difficulty = Difficulty.MEDIUM;
  }

  /** Displays a hint for the MEDIUM difficulty when the mouse enters the "Medium" button area. */
  @FXML
  private void onMediumEnter() {
    difficultyDescriptionLabel.setText("You can ask for a maximum of 5 hints!");
  }

  /** Clears the hint when the mouse exits the "Medium" button area. */
  @FXML
  private void onMediumExit() {
    difficultyDescriptionLabel.setText("");
  }

  /** Sets the game difficulty to HARD when the "Hard" button is clicked. */
  @FXML
  private void onClickHard() {
    GameState.difficulty = Difficulty.HARD;
  }

  /** Displays a hint for the HARD difficulty when the mouse enters the "Hard" button area. */
  @FXML
  private void onHardEnter() {
    difficultyDescriptionLabel.setText("No hints will be given!");
  }

  /** Clears the hint when the mouse exits the "Hard" button area. */
  @FXML
  private void onHardExit() {
    difficultyDescriptionLabel.setText("");
  }

  /** Sets the game playtime to TWO minutes when the "2 Minutes" button is clicked. */
  @FXML
  private void onClickTwo() {
    GameState.time = PlayTime.TWO;
  }

  /** Sets the game playtime to FOUR minutes when the "4 Minutes" button is clicked. */
  @FXML
  private void onClickFour() {
    GameState.time = PlayTime.FOUR;
  }

  /** Sets the game playtime to SIX minutes when the "6 Minutes" button is clicked. */
  @FXML
  private void onClickSix() {
    GameState.time = PlayTime.SIX;
  }

  /**
   * Handles the game start action when the "Start Game" button is clicked, starting the game by
   * making all settings and initialising scenes.
   *
   * @param event The action event representing the button click.
   * @throws IOException If there is an error loading the game scenes or components.
   */
  @FXML
  private void onGameStart(ActionEvent event) throws IOException {
    // guard clauses for checking if there are difficulties / times selected
    if (easyBtn.getToggleGroup().getSelectedToggle() == null) { // no difficulty selected
      difficultyDescriptionLabel.setText("Please select a difficulty!");
      return;
    } else if (twoMinsBtn.getToggleGroup().getSelectedToggle() == null) { // no time selected
      difficultyDescriptionLabel.setText("Please select a time limit!");
      return;
    }

    gameState = GameState.getInstance();

    FXMLLoader pianoLoader = new FXMLLoader(App.class.getResource("/fxml/piano.fxml"));
    FXMLLoader harpLoader = new FXMLLoader(App.class.getResource("/fxml/harp.fxml"));
    SceneManager.addUi(AppUi.PIANO, pianoLoader.load());
    SceneManager.addUi(AppUi.HARP, harpLoader.load());

    // add reference to piano controller to use methods that are not static
    SceneManager.addController(AppUi.PIANO, pianoLoader.getController());
    SceneManager.addController(AppUi.HARP, harpLoader.getController());

    // reloads all FXML files required for playing the game
    SceneManager.addUi(AppUi.CLASSICAL, App.loadFxml("classical"));
    SceneManager.addUi(AppUi.RAVE, App.loadFxml("rave"));
    SceneManager.addUi(AppUi.ROCK, App.loadFxml("rock"));
    SceneManager.addUi(AppUi.BODYBUILDER, App.loadFxml("bodybuilder"));
    SceneManager.addUi(AppUi.GUITARIST, App.loadFxml("guitarist"));
    SceneManager.addUi(AppUi.TRUMPET, App.loadFxml("trumpet"));
    SceneManager.addUi(AppUi.ROCKNOTE, App.loadFxml("rocknote"));
    SceneManager.addUi(AppUi.CLASSICALNOTE, App.loadFxml("classicalnote"));
    SceneManager.addUi(AppUi.MUSICQUIZ, App.loadFxml("musicquiz"));

    // switches the current scene to the rock room
    Button current = (Button) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
    System.out.println(
        "Started with difficulty: "
            + GameState.difficulty
            + " and time: "
            + GameState.time
            + " minutes.");
    // run the gamestate's start function
    gameState.startGame(); // including generating tasks
  }
}
