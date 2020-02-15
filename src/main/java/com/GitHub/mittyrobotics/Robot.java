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
        DriveTrainTalon.getInstance().initHardware();
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

//        CommandScheduler.getInstance().cancelAll();
//        DriveTrainTalon.getInstance().resetEncoder();
        //DriveTrainFalcon.getInstance().tankDrive(1, 1);
        //DriveTrainFalcon.getInstance().resetEncoder();

    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();


//    DriveTrainFalcon.getInstance().tankVelocity(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight), -OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft));
//    System.out.println(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft));
//    System.out.println(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight));
//    DriveTrainFalcon.getInstance().tankVelocity(60, 60);

        //DriveTrainTalon.getInstance().velocityPIDFeedForward( 40, 40);
//        DriveTrainTalon.getInstance().tankVelocity(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft), -OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight));
//    DriveTrainTalon.getInstance().velocityPIDFeedForward( 50, 50); // 50 fine but 40 overshoots and undershoots
//        System.out.println("Left Encoder: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
//        System.out.println("Right Encoder: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());

    }

    @Override
    public void testInit() {


    }

    @Override
    public void testPeriodic() {



    }
}
