import java.awt.*;
import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;

public class SimulationControlPanel extends JFrame {
  private int window_x;
  private int window_y;
  private int numPeople; //will range between 1 and 100(?) people
  private int infectRadius; //we will control these parameters with sliders
  private int socDistRadius;
  private double infectProb;
  //will also want to code in some sort of initial scenario.

  public StaticsPanel sP;
  public DynamicsPanel dP;
  public LiveDataPanel lDP;


  public SimulationControlPanel(String name, int window_x, int window_y) {
    super(name);
    this.window_x = window_x;
    this.window_y = window_y;
    setSize(window_x, window_y);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initialise();
  }

  private void initialise() {

    sP = new StaticsPanel(window_x, window_y/3);
    dP = new DynamicsPanel(window_x, window_y/3);
    lDP = new LiveDataPanel(window_x, window_y/3);

    add(sP);
    add(dP);
    add(lDP);
    setLayout(new GridLayout(3,1));
    //add(resetButton);
    setVisible(true);
  }

}
