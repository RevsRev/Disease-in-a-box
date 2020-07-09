import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class SetupRow extends JPanel {
  private JLabel dataLabel;
  private JSlider dataSlider;
  //private JLabel dataField;
  public int dataValue;

  public SpinnerModel spinnerModel;
  public JSpinner dataSpinner;

  public SetupRow(String labelName, int dataMin, int dataMax, int window_x, int window_y) {
    super();
    //setBorder(new EmptyBorder(10,10,10,10));

    dataValue = (dataMax+dataMin)/2;

    spinnerModel = new SpinnerNumberModel(dataValue, dataMin, dataMax, 1);
    dataSpinner = new JSpinner(spinnerModel);

    //dataField = new JLabel(String.valueOf(dataValue));

    dataLabel = new JLabel(labelName);
    dataSlider = new JSlider(dataMin, dataMax);

    dataSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        dataValue = dataSlider.getValue();
        dataSpinner.setValue(dataValue);
        //dataField.setText(String.valueOf(dataValue));
      }
    });

    dataSpinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        dataValue = (Integer)dataSpinner.getValue();
        dataSlider.setValue(dataValue);
      }
    });

    add(dataLabel); add(dataSlider); add(dataSpinner);
    setSize(window_x, window_y);
    setLayout(new GridLayout(1,3,10,10));
    setVisible(true);
  }


}
