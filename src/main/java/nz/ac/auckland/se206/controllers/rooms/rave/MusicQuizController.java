package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class MusicQuizController {
  @FXML private TextArea speechBox;
  @FXML private Button btnReturn;
  @FXML private Button hintBtn;
  @FXML private Button songBtn;
  @FXML private Button optionOneBtn;
  @FXML private Button optionTwoBtn;
  @FXML private Button optionThreeBtn;
  @FXML private Button optionFourBtn;
  @FXML private Button optionFiveBtn;
  @FXML private Button optionSixBtn;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;
  @FXML private Label cooldownLabel;
  @FXML private Label answerLabel;

  private GameState gamestate;
  private String[] genres = {
    "techno", "pop", "hip hop", "rnb", "kpop", "funk", "house", "drum and bass"
  };
  private String genreSolution;
  private int correctGenreIndex;
  private MediaPlayer musicPlayer;
  private List<String> selectedGenres = new ArrayList<>();
  private int timeToAnswer;
  private Timer timer;
  private Thread timerThread;
  private boolean timerStarted;
  private ArrayList<Integer> availableWrongButtons;

  @FXML
  private void initialize() throws IOException, URISyntaxException, ApiProxyException {
    this.gamestate = GameState.getInstance();
    this.gamestate.timeManager.addToTimers(timerLabel);
    this.gamestate.hintManager.addHintLabel(hintLabel);
    this.timeToAnswer = 0;
    this.timerStarted = false;
    this.availableWrongButtons = new ArrayList<Integer>();
    this.speechBox.setText("Hey man, I need your help identifying this music...");
    Random random = new Random();
    int randomNumber = random.nextInt(8);
    this.genreSolution = genres[randomNumber];
    System.out.println("Solution for genre: " + genreSolution);
    this.musicPlayer =
        new MediaPlayer(
            new Media(
                getClass()
                    .getResource("/sounds/songs/" + genreSolution + ".mp3")
                    .toURI()
                    .toString()));

    musicPlayer.setOnEndOfMedia(
        () -> {
          musicPlayer.seek(Duration.ZERO);
        });

    selectOptions();
    if (GameState.difficulty == Difficulty.HARD) {
      hintBtn.setVisible(false);
    }
  }

  private void selectOptions() throws ApiProxyException {
    List<String> availableGenres = new ArrayList<>();
    Collections.addAll(availableGenres, genres);

    availableGenres.remove(genreSolution);

    Random random = new Random();

    Collections.shuffle(availableGenres, random);
    int randomIndex = random.nextInt(availableGenres.size() - 1);
    this.correctGenreIndex = randomIndex + 1;
    availableGenres.add(randomIndex, genreSolution);
    selectedGenres = availableGenres.subList(0, 6);

    List<Button> optionButtons =
        Arrays.asList(
            optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn, optionFiveBtn, optionSixBtn);

    for (int i = 0; i < selectedGenres.size(); i++) {
      String genre = selectedGenres.get(i);
      Button button = optionButtons.get(i);
      button.setText((i + 1) + ". " + genre);
    }

    for (int i = 1; i <= 6; i++) {
      if (i != correctGenreIndex) {
        availableWrongButtons.add(i);
      }
    }

    System.out.println("Correct genre index: " + correctGenreIndex);
  }

  @FXML
  private void onClickSongBtn() {
    System.out.println("sb");
    if (musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
      musicPlayer.pause();
      songBtn.setText("PLAY SONG");
    } else {
      musicPlayer.play();
      songBtn.setText("PAUSE SONG");
    }
  }

  private void setButtonCooldowns() {
    disableAllButtons();
    startCooldownTimer();
  }

  private void disableAllButtons() {
    optionOneBtn.setDisable(true);
    optionTwoBtn.setDisable(true);
    optionThreeBtn.setDisable(true);
    optionFourBtn.setDisable(true);
    optionFiveBtn.setDisable(true);
    optionSixBtn.setDisable(true);
  }

  private void startCooldownTimer() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    timer = new Timer();
    this.timerStarted = true;
    this.timeToAnswer = 20;

    // task for the update of the timer, decrements timer every second and updates GUI, aswell as
    // validating for any events that may have to be run.
    Task<Void> timerTask =
        new Task<Void>() {

          @Override
          // A task for the continuous decrementing of the timer, and updates all timer
          // value indicators for the GUI.
          protected Void call() throws Exception {
            timer.scheduleAtFixedRate(
                new TimerTask() {
                  @Override
                  public void run() {
                    if (timeToAnswer > 0) {
                      timeToAnswer--;
                      Platform.runLater(
                          () -> {
                            cooldownLabel.setText(
                                "You can answer again in " + timeToAnswer + " seconds!");
                          });
                    } else {
                      stopCountdown();
                      Platform.runLater(
                          () -> {
                            cooldownLabel.setText("You can attempt to answer again!");
                          });
                    }
                  }
                },
                1000,
                1000);
            return null;
          }
        };

    Thread timerThread = new Thread(timerTask, "TimerThread");
    timerThread.start();
  }

  public void stopCountdown() {
    if (timer != null) {
      timer.cancel();
    }

    if (timerThread != null) {
      timerThread.interrupt();
    }

    this.timerStarted = false;
    optionOneBtn.setDisable(false);
    optionTwoBtn.setDisable(false);
    optionThreeBtn.setDisable(false);
    optionFourBtn.setDisable(false);
    optionFiveBtn.setDisable(false);
    optionSixBtn.setDisable(false);
  }

  private void attemptSolve(int index) {

    if (correctGenreIndex == index) {
      hintBtn.setVisible(false);
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. For you, I got this key for you man.");
      gamestate.objectiveListManager.completeObjective1();
      answerLabel.setText("CORRECT");
      answerLabel.setTextFill(Color.GREEN);
      cooldownLabel.setVisible(false);
      disableAllButtons();
      return;
    }

    if (correctGenreIndex != index) {
      answerLabel.setText("INCORRECT");
      answerLabel.setTextFill(Color.RED);
      setButtonCooldowns();
    }
  }

  @FXML
  private void onClickOne() {
    System.out.println("1");
    attemptSolve(1);
  }

  @FXML
  private void onClickTwo() {
    System.out.println("2");
    attemptSolve(2);
  }

  @FXML
  private void onClickThree() {
    System.out.println("3");
    attemptSolve(3);
  }

  @FXML
  private void onClickFour() {
    System.out.println("4");
    attemptSolve(4);
  }

  @FXML
  private void onClickFive() {
    System.out.println("5");
    attemptSolve(5);
  }

  @FXML
  private void onClickSix() {
    System.out.println("6");
    attemptSolve(6);
  }

  private void deleteWrongOption() {
    Collections.shuffle(availableWrongButtons);
    int toRemove = availableWrongButtons.remove(0);
    switch (toRemove) {
      case 1:
        optionOneBtn.setVisible(false);
        break;
      case 2:
        optionTwoBtn.setVisible(false);
        break;
      case 3:
        optionThreeBtn.setVisible(false);
        break;
      case 4:
        optionFourBtn.setVisible(false);
        break;
      case 5:
        optionFiveBtn.setVisible(false);
        break;
      case 6:
        optionSixBtn.setVisible(false);
        break;
      default:
        break;
    }
    if (availableWrongButtons.size() <= 1) {
      hintBtn.setVisible(false);
    }
  }

  @FXML
  private void onClickHint() {
    System.out.println("hint");
    if (this.gamestate.hintManager.getHintsRemaining() > 0) {
      this.gamestate.hintManager.useHint();
      deleteWrongOption();
    } else {
      speechBox.setText("Sorry bro, I don't have any hints for you man.");
    }
  }

  // switches back to the rave room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
    musicPlayer.pause();
    songBtn.setText("PLAY SONG");
  }
}
