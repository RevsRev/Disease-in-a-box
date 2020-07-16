import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DynamicsPanel extends JPanel {
  private int window_x;

  public SetupRow infectRad;
  public SetupRow infectProb;
  public SetupRow incubationPeriod;
  public SetupRow infectPeriod;
  public SetupRow socDistanceRadius;

  private JButton startSimButton;
  public boolean simRunning = false; //toggle whether the simulation is running or not.

  public DynamicsPanel(int window_x, int window_y) {
    super();
    this.window_x = window_x;

    TitledBorder title = BorderFactory.createTitledBorder("Dynamical Variables");
    title.setTitleJustification(TitledBorder.CENTER);
    setBorder(title);

    setSize(window_x, window_y);
    addDataOptions();
    addSimStartButton();
    setLayout(new GridLayout(6,1,10,10));
    setVisible(true);
  }

  public void addSimStartButton() {
    startSimButton = new JButton("Start/Stop");
    startSimButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (simRunning) {
          simRunning = false;
        } else {
          simRunning = true;
        }
      }
    });
    add(startSimButton);
  }

  public void addDataOptions() {
    infectRad = new SetupRow("Infection Radius", 10, 30, window_x, 50);
    infectProb = new SetupRow("Infection Probability", 0, 100, window_x, 50);
    incubationPeriod = new SetupRow("Incubation Period", 0, 20, window_x, 50);
    infectPeriod = new SetupRow("Infectious Period", 0, 20, window_x, 50);;
    socDistanceRadius = new SetupRow("Social Distancing Radius", 0, 50, window_x, 50);
    add(infectRad); add(infectProb); add(incubationPeriod); add(infectPeriod);
    add(socDistanceRadius);
  }

}
