import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class SimulationWindow extends JPanel {
  public int window_x;
  public int window_y;
  private int people_size = 10; //the size of the blobs representing people
  public Person[] people_array;

  public SimulationWindow(int window_x, int window_y, Person[] people_array) {
    super();
    this.window_x = window_x;
    this.window_y = window_y;
    this.people_array = people_array;
    this.setSize(window_x, window_y);
    this.setLayout(null);
    this.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    BufferedImage bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics g_buff = (Graphics) bufferImage.getGraphics();
    paintPeople(g_buff);

    g.drawImage(bufferImage,0,0,null);
  }

  private void personColor(Person p, Graphics g) {
    if (p.susceptible) {
      g.setColor(Color.BLUE);
    } else if (p.infected && !p.contagious) {
      g.setColor(Color.YELLOW);
    } else if (p.infected && p.contagious) {
      g.setColor(Color.RED);
    } else {
      g.setColor(Color.GRAY);
    }
  }

  public void paintPerson(Person p, Graphics g) {
    personColor(p,g);
    g.fillOval(getDrawCoord(window_x, p.xPos, people_size),
      getDrawCoord(window_y, p.yPos, people_size), people_size, people_size);
    if (p.rogue) {
      g.setColor(Color.WHITE);
      g.fillOval(getDrawCoord(window_x, p.xPos+people_size/2-1, people_size),
        getDrawCoord(window_y, p.yPos+people_size/2-1, people_size), 2, 2);
    }
  }

  private int getDrawCoord(int window, int pos, int size) {
    if (pos<size) {
      return size;
    } else if (pos + size > window) {
      return window-size;
    } else {
      return pos;
    }
  }

  public void paintPeople(Graphics g) {
    for (int i = 0; i<people_array.length; i++) {
      paintPerson(people_array[i],g);
    }
  }

}
