import java.util.Random;

public class Person {
  public int xPos, yPos;
  public int window_x, window_y;
  public int xVel, yVel;
  public int size;
  public int maxVel;
  //public int socDistRad; //Social distancing radius
  private int internalClock = 0;

  public int incubationTime = 5;
  public int infectiousTime = 10;

  public boolean susceptible = true;
  public boolean infected = false;
  public boolean contagious = false;
  public boolean socialDistancing = false;
  public boolean rogue = false; //if true, a person is said to be rogue ignores social distancing.

  public Person(int xPos, int yPos, int maxVel, int size, int window_x, int window_y) {
    Random rand = new Random();
    this.xPos = xPos; this.yPos = yPos;
    this.maxVel = maxVel;
    this.size = size;
    this.xVel = rand.nextInt(2*maxVel+1)-maxVel; this.yVel = rand.nextInt(2*maxVel+1) - maxVel;
    this.window_x = window_x; this.window_y = window_y;
  }

  public void clockTick(int frameRate) {
    if (infected && !contagious) {
      if (internalClock < frameRate*incubationTime) {
        internalClock += 1;
      } else {
        internalClock = 0;
        contagious = true;
      }
    } else if (infected && contagious) {
      if (internalClock < frameRate*infectiousTime) {
        internalClock += 1;
      } else {
        internalClock =0;
        infected = false;
        contagious = false;
        susceptible = false;
      }
    } else {
      internalClock = 0;
    }
  }

  public double distance(Person otherPerson) {
    return Math.sqrt(Math.pow((xPos - otherPerson.xPos),2) + Math.pow((yPos - otherPerson.yPos), 2));
  }

  public void changePos(int delx, int dely) {
    int rad = size/2;

    if (xPos + delx - rad < 0) {
      xPos = rad;
      xVel = -xVel;
    } else if (xPos + delx + rad > window_x) {
      xPos = window_x - rad;
      xVel = xVel;
    } else {
      xPos += delx;
    }
    if (yPos - rad + dely < 0) {
      yPos = rad;
      yVel = -yVel;
    } else if (yPos + rad + dely > window_y ) {
      yPos = window_y - rad;
      yVel = -yVel;
    } else {
      yPos += dely;
    }
  }

  public void naturalMotion(int randDelVx, int randDelVy) {
    //we compute changes in velocity in the main simulation window to save resources
    changePos(xVel, yVel);
    changeVel(randDelVx, randDelVy);
    clockTick(60); //link this to frame rate in simulation class.
  }

  public void changeVel(int delvx, int delvy) {
    xVel += delvx;
    yVel += delvy;
    if (xVel > maxVel) {
      xVel = maxVel;
    } else if (xVel<-maxVel) {
      xVel = -maxVel;
    }
    if (yVel > maxVel) {
      yVel = maxVel;
    } else if (yVel < -maxVel) {
      yVel = -maxVel;
    }
  }

  public void changeInfectivity() {
    if (infected) {
      infected = false;
    } else {
      infected = true;
    }
  }

  public void changeSusceptibility() {
    if (susceptible) {
      susceptible = false;
    } else {
      susceptible = true;
    }
  }

}
