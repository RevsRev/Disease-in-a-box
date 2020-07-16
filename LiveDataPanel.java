import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LiveDataPanel extends JPanel {
  private int window_x;

  public LiveDataRow rFactorRow;
  public LiveDataRow infectedRow;
  public LiveDataRow infectiousRow;
  public LiveDataRow healthyRow; //have never been infected.
  public LiveDataRow recoveredRow; //have been infected but no longer are.

  public LiveDataPanel(int window_x, int window_y) {
    super();
    this.window_x = window_x;

    TitledBorder title = BorderFactory.createTitledBorder("Live Data");
    title.setTitleJustification(TitledBorder.CENTER);
    setBorder(title);

    setSize(window_x, window_y);
    addDataOptions();
    setLayout(new GridLayout(2,3));
    setVisible(true);
  }

  public void addDataOptions() {
    rFactorRow = new LiveDataRow("R Factor", 0, window_x, 50);
    infectedRow = new LiveDataRow("Infected", 0, window_x, 50);
    infectiousRow = new LiveDataRow("Infectious", 0, window_x, 50);
    healthyRow = new LiveDataRow("Healthy", 0, window_x, 50);
    recoveredRow = new LiveDataRow("Recovered", 0, window_x, 50);
    add(rFactorRow); add(infectedRow); add(infectiousRow);
    add(healthyRow); add(recoveredRow);
  }

}
