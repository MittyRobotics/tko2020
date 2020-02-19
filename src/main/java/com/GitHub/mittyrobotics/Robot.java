package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainSparks;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();
        DriveTrainFalcon.getInstance().initHardware();
    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {


    }

    @Override
    public void autonomousPeriodic() {



    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();

    }

    @Override
    public void testInit() {


    }

    @Override
    public void testPeriodic() {



    }
}
