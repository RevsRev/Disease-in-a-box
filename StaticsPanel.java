import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class StaticsPanel extends JPanel {
  private int window_x;

  private JButton resetButton = new JButton("Reset");
  public boolean reset = false;

  public SetupRow initInfectProb;
  public SetupRow rogueProb;
  public SetupRow numPeople;
  public SetupRowAlt socDistancing;

  public StaticsPanel(int window_x, int window_y) {
    super();
    this.window_x = window_x;

    TitledBorder title = BorderFactory.createTitledBorder("Static Variables");
    title.setTitleJustification(TitledBorder.CENTER);
    setBorder(title);

    setSize(window_x, window_y);
    addDataOptions();
    addResetButton();
    setLayout(new GridLayout(5,1,10,10));
    setVisible(true);
  }

  public void addResetButton() {
    resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reset = true;
      }
    });
    add(resetButton);
  }

  public void addDataOptions() {
    initInfectProb = new SetupRow("Initial Infection Probability", 0, 100, window_x, 50);
    rogueProb = new SetupRow("Initial Rogue Probability", 0, 100, window_x, 50);
    numPeople = new SetupRow("Initial Number of People", 0, 1000, window_x, 50);
    socDistancing = new SetupRowAlt("Social Distancing", window_x, 50);
    add(initInfectProb); add(rogueProb); add(numPeople); add(socDistancing);
  }

}
