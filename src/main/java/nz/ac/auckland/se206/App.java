package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {

    SceneManager.addUi(AppUi.START, loadFxml("start"));

    scene = new Scene(SceneManager.getUiRoot(AppUi.START), 1280, 720);

    String appCss = App.class.getResource("/css/app.css").toExternalForm();
    String fontCss = App.class.getResource("/css/fontstyle.css").toExternalForm();
    String buttonCss = App.class.getResource("/css/button+labelstyle.css").toExternalForm();
    scene.getStylesheets().add(appCss);
    scene.getStylesheets().add(fontCss);
    scene.getStylesheets().add(buttonCss);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() {
    Platform.exit();
    System.exit(0);
  }
}
