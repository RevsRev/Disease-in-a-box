import javax.swing.*;
import java.awt.*;

public class LiveDataRow extends JPanel {
  private JLabel nameLabel;
  public JLabel dataLabel;

  public LiveDataRow(String dataName, int dataValue, int window_x, int window_y) {
    super();

    nameLabel = new JLabel(dataName);
    dataLabel = new JLabel(String.valueOf(dataValue));
    setSize(window_x, window_y);
    add(nameLabel); add(dataLabel);

    setLayout(new GridLayout(1,2));
    setVisible(true);
  }
}
