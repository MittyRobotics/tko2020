package com.amhsrobotics;

import edu.wpi.first.wpilibj.RobotBase;

//DO NOT TOUCH THIS CLASS
public final class Main {
  private Main() {
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
