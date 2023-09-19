package nz.ac.auckland.se206;

import java.io.IOException;
import nz.ac.auckland.se206.controllers.rooms.notes.ClassicalNoteController;
import nz.ac.auckland.se206.controllers.rooms.notes.RockNoteController;
import nz.ac.auckland.se206.controllers.rooms.rave.BodybuilderController;

/** Represents the state of the game. */
public class GameState {
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  public enum PlayTime {
    TWO(2),
    FOUR(4),
    SIX(6);

    private final Integer time;

    PlayTime(Integer time) {
      this.time = time;
    }

    public Integer getTime() {
      return this.time;
    }
  }

  // variables to keep track of the game state
  private static GameState instance;
  public static Difficulty difficulty;
  public static PlayTime time;
  public static boolean isRiddleResolved = false;
  public static boolean isRiddleObjectFound = false;
  public static boolean isNoteSequenceFound = false;
  public static boolean isKeyFound = false;
  public static boolean isMusicQuizCompleted = false;
  public static boolean isSafeOpened = false;
  public static boolean isPianoPlayed = false;
  public static boolean isHarpPlayed = false;
  public static boolean isEscaped = false;

  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
      instance.timeManager = new TimeManager();
      instance.taskManager = new TaskManager();
      instance.chatManager = new ChatManager();
      instance.rockBigTaskManager = new RockBigTaskManager();
      instance.ravePuzzle = new RavePuzzle();
      instance.objectiveListManager = new ObjectiveListManager();
      instance.hintManager = new HintManager();
    }
    return instance;
  }

  public static void setInstance(GameState instance) {
    GameState.instance = instance;
  }

  public static void resetVariables() {
    isRiddleResolved = false;
    isRiddleObjectFound = false;
    isNoteSequenceFound = false;
    isKeyFound = false;
    isMusicQuizCompleted = false;
    isSafeOpened = false;
    isPianoPlayed = false;
    isHarpPlayed = false;
    isEscaped = false;
  }

  public TimeManager timeManager;
  public HintManager hintManager;
  public TaskManager taskManager;
  public ChatManager chatManager;
  public RockBigTaskManager rockBigTaskManager;
  public RavePuzzle ravePuzzle;
  public ObjectiveListManager objectiveListManager;

  public BodybuilderController bodybuilderController;
  public ClassicalNoteController classicalNote;
  public RockNoteController rockNote;

  public void startGame() throws IOException {
    this.taskManager.generateTasks();
    this.timeManager.setTime(time.getTime() * 60);
    this.timeManager.startCountdown();
    this.hintManager.initialiseManager(difficulty);
    this.ravePuzzle.setHints(classicalNote, rockNote);
    this.bodybuilderController.initialiseCode();
  }
}
