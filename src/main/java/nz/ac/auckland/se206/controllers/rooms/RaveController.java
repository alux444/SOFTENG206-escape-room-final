package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class RaveController {

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane posterPane;
  @FXML private Pane djPane;
  @FXML private Pane bodybuilderPane;
  @FXML private Pane bouncerPane;
  @FXML private Pane discoPane;
  @FXML private Pane speakerPane;
  @FXML private ImageView doorImage;
  @FXML private ImageView greenLock;
  @FXML private ImageView redLock;
  @FXML private ImageView blueLock;
  @FXML private ImageView yellowLock;
  @FXML private Pane chatBoxPane;
  @FXML private Label timerLabel;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private boolean chatOpened;

  private GameState gameState;

  @FXML
  private void initialize() {
    gameState = GameState.getInstance();
    gameState.timeManager.addToTimers(timerLabel);
    gameState.chatManager.addTextArea(textArea);
    gameState.chatManager.addTextField(textField);
    chatOpened = false;
  }

  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
  }

  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
  }

  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
  }

  @FXML
  private void onClickBouncer(MouseEvent event) {
    System.out.println("bouncer clicked");
  }

  @FXML
  private void onClickDisco(MouseEvent event) {
    System.out.println("disco clicked");
  }

  @FXML
  private void onClickSpeaker(MouseEvent event) {
    System.out.println("speaker clicked");
  }

  @FXML
  private void onClickRed(MouseEvent event) {
    System.out.println("red clicked");
  }

  @FXML
  private void onClickGreen(MouseEvent event) {
    System.out.println("green clicked");
  }

  @FXML
  private void onClickBlue(MouseEvent event) {
    System.out.println("blue clicked");
  }

  @FXML
  private void onClickYellow(MouseEvent event) {
    System.out.println("yellow clicked");
  }

  @FXML
  private void onClickDoor(MouseEvent event) {
    System.out.println("door clicked");
  }

  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

    @FXML
  private void toggleChat() {
    if (chatOpened) {
      chatBoxPane.setDisable(true);
      chatBoxPane.setOpacity(0);
    } else {
      chatBoxPane.setDisable(false);
      chatBoxPane.setOpacity(0.95);
    }
    chatOpened = !chatOpened;
  }

  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }
}
