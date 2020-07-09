import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class SetupRowAlt extends JPanel {
  private JLabel dataLabel;
  private JLabel filler;
  public JComboBox<Boolean> dataOptions;
  private DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
  public Boolean[] options = {true, false};

  public SetupRowAlt(String labelName, int window_x, int window_y) {
    super();
    //setBorder(new EmptyBorder(10,10,10,10));

    dataOptions = new JComboBox<>(options);
    dataOptions.setSelectedIndex(1);
    listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    dataOptions.setRenderer(listRenderer);

    dataLabel = new JLabel(labelName);
    filler = new JLabel();

    /*dataSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        dataValue = dataSlider.getValue();
        dataField.setText(String.valueOf(dataValue));
      }
    });*/

    add(dataLabel); add(dataOptions); add(filler);
    setSize(window_x, window_y);
    setLayout(new GridLayout(1,3,10,10));
    setVisible(true);
  }


}
