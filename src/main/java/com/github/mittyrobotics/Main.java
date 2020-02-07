package com.GitHub.mittyrobotics;

import edu.wpi.first.wpilibj.RobotBase;
//DO NOT TOUCH
public final class Main {
  private Main() {

  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}