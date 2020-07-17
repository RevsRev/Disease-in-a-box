import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Simulation implements ActionListener {

  private static int frameRate = 60;
  private static int frameTime = 1000/frameRate;

  private int window_x; private int window_y;
  private Random rand = new Random(); //we will use this for generating random data.
  private int people_size = 10;

  private int initInfectProb = 10;
  private int rogueProb = 10; //rogue people do not following social distancing when it is imposed.
  private int infectProb; //prob of being infected for each timestep spent within infectRad of infected person.
  private int infectRad;

  private int maxVel = 3;
  private int socDistRad;
  private boolean globalSocialDistancing;

  private Person[] people_array;
  private int num_people;
  private JFrame sF = new JFrame("Simulation Window");
  private SimulationControlPanel sCP = new SimulationControlPanel("Simulation Control", 600, 600);
  private SimulationWindow sW;
  //private SimulationWindow sW = new SimulationWindow(400,400, people_array);

  public Simulation(int window_x, int window_y) {
    this.num_people = num_people;
    this.window_x = window_x;
    this.window_y = window_y;
    initialise();
  }

  private void initialise() { //we can also use this function to reset our simulations using the sCP.

    num_people = sCP.sP.numPeople.dataValue;
    rogueProb = sCP.sP.rogueProb.dataValue;
    initInfectProb = sCP.sP.initInfectProb.dataValue;

    globalSocialDistancing = (sCP.sP.socDistancing.dataOptions.getItemAt(
      sCP.sP.socDistancing.dataOptions.getSelectedIndex())) ? true : false;

    people_array = new Person[num_people];
    for (int i = 0; i < num_people; i++) {
      people_array[i] = new Person(rand.nextInt(window_x), rand.nextInt(window_y),
        maxVel, people_size, window_x, window_y);
      people_array[i].infected = randomSampler(initInfectProb);
      if (people_array[i].infected) {
        people_array[i].susceptible = false;
      }
      people_array[i].rogue = randomSampler(rogueProb);
      if (people_array[i].rogue && globalSocialDistancing) {
        people_array[i].socialDistancing =false;
      } else if (globalSocialDistancing) {
        people_array[i].socialDistancing = true;
      }
    }
    sW = new SimulationWindow(window_x,window_y, people_array);

    updateDynamicalParameters();

    sF.add(sW);
    sF.setSize(window_x,window_y+20);
    sF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    sF.setLayout(null);
    sF.setVisible(true);

    sCP.sP.reset = false;

  }

  private void updateDynamicalParameters() {
    //use the sCP to update our parameters during the simulation.
    infectProb = sCP.dP.infectProb.dataValue;
    infectRad =  sCP.dP.infectRad.dataValue;
    socDistRad = sCP.dP.socDistanceRadius.dataValue;
    for (int i = 0; i< num_people; i++) {
      //people_array[i].socDistRad = sCP.sP.socDistanceRadius.dataValue;
      people_array[i].incubationTime = sCP.dP.incubationPeriod.dataValue;
      people_array[i].infectiousTime = sCP.dP.infectPeriod.dataValue;
    }
  }

  private void updateLiveData() {
    int infected = 0;
    int infectious = 0;
    int healthy = 0;
    int recovered = 0;

    for (int i =0; i<num_people; i++) {
      if (people_array[i].susceptible) {
        healthy += 1;
      } else if (people_array[i].infected) {
        infected+=1;
        if (people_array[i].contagious) {
          infectious+=1;
        }
      } else {
        recovered+=1;
      }
    }
    if (infected != 0) {
      sCP.lDP.rFactorRow.dataLabel.setText(String.valueOf(
        (Double.valueOf(infected-infectious))/Double.valueOf(infectious)));
    }
    sCP.lDP.infectedRow.dataLabel.setText(String.valueOf(infected));
    sCP.lDP.infectiousRow.dataLabel.setText(String.valueOf(infectious));
    sCP.lDP.healthyRow.dataLabel.setText(String.valueOf(healthy));
    sCP.lDP.recoveredRow.dataLabel.setText(String.valueOf(recovered));
  }

  public boolean collisionImminent(int x1, int x2, int v1, int v2) {
    if (x1>x2) { //x1 lies to the right of x2
      if (v1 < v2) {
        return true;
      } else {
        return false;
      }
    } else {
      if (v1<v2) {
        return false;
      } else {
        return true;
      }
    }
  }

  public void actionPerformed(ActionEvent e) {

    if (sCP.sP.reset) {
      sCP.dP.simRunning = false;

      initialise();
      updateDynamicalParameters();
      sW.repaint();
    }

    if (!sCP.dP.simRunning) {
      return;
    }
    for (int i = 0; i<num_people; i++) {
      for (int j=0; j<=i; j++) {
        if (j==i) {
          break;
        }
        if (sW.people_array[i].socialDistancing && sW.people_array[j].socialDistancing) {
          if (sW.people_array[i].distance(sW.people_array[j]) < socDistRad) {
            if (collisionImminent(sW.people_array[i].xPos, sW.people_array[j].xPos,
              sW.people_array[i].xVel, sW.people_array[j].xVel)) {
              if (rand.nextInt(2)==0) {
                sW.people_array[i].xVel = -sW.people_array[i].xVel;
              } else {
                sW.people_array[j].xVel = -sW.people_array[j].xVel;
              }
            }
            if (collisionImminent(sW.people_array[i].yPos, sW.people_array[j].yPos,
              sW.people_array[i].yVel, sW.people_array[j].yVel)) {
              if (rand.nextInt(2)==0) {
                sW.people_array[i].yVel = -sW.people_array[i].yVel;
              } else {
                sW.people_array[j].yVel = -sW.people_array[j].yVel;
              }
            }
          }
        }

        if (sW.people_array[i].distance(sW.people_array[j])< infectRad) {
          if (sW.people_array[i].infected && sW.people_array[i].contagious
            && sW.people_array[j].susceptible && !sW.people_array[j].infected) {
            if (randomSampler(infectProb)) {
              sW.people_array[j].infected = true;
              sW.people_array[j].susceptible = false;
            }
          } else if (sW.people_array[j].infected && sW.people_array[j].contagious &&
            sW.people_array[i].susceptible && !sW.people_array[i].infected) {
            if (randomSampler(infectProb)) {
              sW.people_array[i].infected = true;
              sW.people_array[i].susceptible = false;
            }
          }
        }
      }
      sW.people_array[i].naturalMotion(rand.nextInt(3)-1, rand.nextInt(3)-1);
    }
    sW.repaint();
    updateDynamicalParameters();
    updateLiveData();
  }

  private boolean randomSampler(int prob) {
    //prob should be an integer between 0 and 100;
    if (rand.nextInt(101) < prob) {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String []args) {
    Simulation sim = new Simulation(800, 800);
    Timer timer = new Timer(frameTime, sim);
    timer.start();
  }

}
