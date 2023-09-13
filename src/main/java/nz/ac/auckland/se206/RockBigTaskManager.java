package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

public class RockBigTaskManager {
  public enum Note { // all musical notes
    C,
    D,
    E,
    F,
    G,
    A,
    B;
  }

  public enum Colour { // colours of the electric guitars
    YELLOW("#EADE3B"),
    BLUE("#2E2EB6"),
    PURPLE("#7D2DB6"),
    CYAN("#18E5CD");

    private final String hex;

    Colour(String hex) {
      this.hex = hex;
    }

    public String getHex() {
      return hex;
    }
  }

  private Note[] noteSequence;
  private ArrayList<Label> colourLabels1;
  private ArrayList<Label> colourLabels2;
  private ArrayList<Label> colourLabels3;
  private ArrayList<Label> colourLabels4;
  private ArrayList<Pane> notePanes;
  private ArrayList<ToggleButton> noteButtons;
  private ArrayList<Label> noteSequenceLabels;
  private ArrayList<Colour> colours;
  private ArrayList<Note> notes;
  private ArrayList<Integer> order;
  private HashMap<Colour, Integer> orderColourMap;
  private String noteSequenceString;

  RockBigTaskManager() {
    this.noteSequence = new Note[4]; // 4 notes in the sequence
    this.colourLabels1 = new ArrayList<Label>();
    this.colourLabels2 = new ArrayList<Label>();
    this.colourLabels3 = new ArrayList<Label>();
    this.colourLabels4 = new ArrayList<Label>();
    this.notePanes = new ArrayList<Pane>();
    this.noteButtons = new ArrayList<ToggleButton>();
    this.noteSequenceLabels = new ArrayList<Label>();
    this.colours = new ArrayList<Colour>();
    colours.add(Colour.YELLOW);
    colours.add(Colour.BLUE);
    colours.add(Colour.PURPLE);
    colours.add(Colour.CYAN);
    Collections.shuffle(colours); // randomise the order of the colours
    this.notes = new ArrayList<Note>();
    notes.add(Note.C);
    notes.add(Note.D);
    notes.add(Note.E);
    notes.add(Note.F);
    notes.add(Note.G);
    notes.add(Note.A);
    notes.add(Note.B);
    this.order = new ArrayList<Integer>();
    order.add(1);
    order.add(2);
    order.add(3);
    order.add(4);
    Collections.shuffle(order); // randomise the order of the numbers
    this.noteSequenceString = "";
  }

  public void addToColourLabels1(Label label) {
    colourLabels1.add(label);
  }

  public void addToColourLabels2(Label label) {
    colourLabels2.add(label);
  }

  public void addToColourLabels3(Label label) {
    colourLabels3.add(label);
  }

  public void addToColourLabels4(Label label) {
    colourLabels4.add(label);
  }

  public void addToNotePanes(Pane pane) {
    notePanes.add(pane);
  }

  public void addToNoteButtons(ToggleButton button) {
    noteButtons.add(button);
  }

  public void addToNoteSequenceLabels(Label label) {
    noteSequenceLabels.add(label);
  }

  public void setVisibilityNotePanes(boolean visibility) {
    for (Pane pane : notePanes) {
      pane.setStyle(
          "-fx-background-color: #FFF5D8; -fx-border-color:"
              + " #000000;"
              + " -fx-border-width:"
              + " 2px"); // same colour as the note icon
      pane.setVisible(visibility);
    }
  }

  public void setVisibilityNoteButtons(boolean visibility) {
    for (ToggleButton button : noteButtons) {
      button.setVisible(visibility);
    }
  }

  public void setDisableNoteButtons(boolean disable) {
    for (ToggleButton button : noteButtons) {
      button.setDisable(disable);
    }
  }

  public void setLabelColours() {
    for (Label label : colourLabels1) {
      label.setStyle( // set random colour of the label
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(0).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(0))); // set random number of the colour
    }

    for (Label label : colourLabels2) {
      label.setStyle( // set random colour of the label
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(1).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(1))); // set random number of the colour
    }

    for (Label label : colourLabels3) {
      label.setStyle( // set random colour of the label
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(2).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(2))); // set random number of the colour
    }

    for (Label label : colourLabels4) {
      label.setStyle( // set random colour of the label
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(3).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(3))); // set random number of the colour
    }
  }

  public void setNoteSequence() { // set random note sequence
    for (int i = 0; i < 4; i++) {
      noteSequence[i] = notes.get((int) (Math.random() * notes.size()));
    }
    System.out.println(
        "Note sequence: " + noteSequence[0] + noteSequence[1] + noteSequence[2] + noteSequence[3]);
  }

  public Note[] getNoteSequence() {
    return noteSequence;
  }

  public void setOrderColourMap() { // organise the colours and numbers in a map
    orderColourMap = new HashMap<Colour, Integer>();
    orderColourMap.put(colours.get(0), order.get(0));
    orderColourMap.put(colours.get(1), order.get(1));
    orderColourMap.put(colours.get(2), order.get(2));
    orderColourMap.put(colours.get(3), order.get(3));
  }

  public HashMap<Colour, Integer> getOrderColourMap() {
    return orderColourMap;
  }

  public void setNoteSequenceLabels(String noteSequence) {
    // set the note sequence labels, which will update as guitars are clicked
    noteSequenceString = noteSequenceString.concat(noteSequence);
    for (Label label : noteSequenceLabels) {
      label.setText(noteSequenceString);
    }
  }

  public void clearNoteSequenceLabels() {
    noteSequenceString = "";
    for (Label label : noteSequenceLabels) {
      label.setText("");
    }
  }
}
