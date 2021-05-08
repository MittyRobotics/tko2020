package com.github.mittyrobotics;

import com.github.mittyrobotics.commands.AutoConveyorIndexCommand;
import com.github.mittyrobotics.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class TestRobot extends TimedRobot {
    private XboxController controller;
    @Override
    public void robotInit() {
        controller = new XboxController(0);
        ConveyorSubsystem.getInstance().initHardware();
        IntakeSubsystem.getInstance().initHardware();
        DriveTrainSubsystem.getInstance().initHardware();
//        Compressor.getInstance().initHardware();
//        Compressor.getInstance().start();
        ShooterSubsystem.getInstance().initHardware();
        DriveTrainSubsystem.getInstance().initHardware();
        TurretSubsystem.getInstance().initHardware();
    }

    @Override
    public void teleopPeriodic(){
        double left = 0;
        double right = 0;
        ConveyorSubsystem.getInstance().overrideSetMotor(0.7);
        ShooterSubsystem.getInstance().overrideSetMotor(.25);
        if(Math.abs(controller.getY(GenericHID.Hand.kLeft)) > 0.1){
            left = -controller.getY(GenericHID.Hand.kLeft)/2;
        }
        if(Math.abs(controller.getY(GenericHID.Hand.kRight)) > 0.1){
            right = -controller.getY(GenericHID.Hand.kRight)/2;
        }
        DriveTrainSubsystem.getInstance().overrideSetMotor(left, right);
        if(controller.getTriggerAxis(GenericHID.Hand.kRight) > 0.2){
            IntakeSubsystem.getInstance().overrideSetMotor(.75);
        } else if(controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.2){
            IntakeSubsystem.getInstance().overrideSetMotor(-0.75);
        } else {
            IntakeSubsystem.getInstance().overrideSetMotor(0);
        }
        if(controller.getPOV() == 90){
            TurretSubsystem.getInstance().overrideSetMotor(.25);
        } else if(controller.getPOV() == 270){
            TurretSubsystem.getInstance().overrideSetMotor(-.25);
        } else {
            TurretSubsystem.getInstance().overrideSetMotor(0);
        }
    }
    @Override
    public void testPeriodic() {
        if(controller.getAButton()){
            IntakeSubsystem.getInstance().overrideSetMotor(0.3);
        } else if(controller.getBButton()){
            IntakeSubsystem.getInstance().overrideSetMotor(-.3);
        } else {
            IntakeSubsystem.getInstance().overrideSetMotor(0);
        }
        ConveyorSubsystem.getInstance().overrideSetMotor(0.45);
        ShooterSubsystem.getInstance().overrideSetMotor(.25);
    }

    @Override
    public void autonomousInit(){
        CommandScheduler.getInstance().schedule(new AutoConveyorIndexCommand());
    }
    @Override
    public void autonomousPeriodic() {
        if(controller.getAButton()){
            IntakeSubsystem.getInstance().overrideSetMotor(.5);
        } else if(controller.getBButton()){
            IntakeSubsystem.getInstance().overrideSetMotor(-.5);
        } else {
            IntakeSubsystem.getInstance().overrideSetMotor(0);
        }
        CommandScheduler.getInstance().run();
//        ConveyorSubsystem.getInstance().overrideSetMotor(-0.25);
    }
}
