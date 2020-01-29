package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureSteering2 extends CommandBase {

    CurvatureSteering2(){
        addRequirements(DriveTrainTalon.getInstance());
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

        double turn = (OI.getInstance().getXboxWheel().getX()*450); //steering wheel
        double joystickSpeed = -OI.getInstance().getJoystick1().getY(); //joystick
        boolean brake = OI.getInstance().getJoystick1().getTrigger(); //brake
        final double radiusE = 1;
        double radius = 450/turn * radiusE; //radius of imaginary circle
        double halfWidthRobot = 12.5; //from center of robot to imagnary circle

        double leftSpeed = 0;
        double rightSpeed = 0;
        boolean inThreshold = Math.abs(turn) < 5;

        //testing for 10 degrees to the left
        if (turn >= 5){
            leftSpeed = (radius + halfWidthRobot)*(2*Math.PI);
            rightSpeed = (radius - halfWidthRobot)*(2*Math.PI);
            rightSpeed = rightSpeed/leftSpeed;
            leftSpeed = 1;
        } else if (turn <= -5){
            leftSpeed = (radius - halfWidthRobot)*(2*Math.PI);
            rightSpeed = (radius + halfWidthRobot)*(2*Math.PI);
            leftSpeed = leftSpeed/rightSpeed;
            rightSpeed = 1;
        } else if (inThreshold) {
            turn = OI.getInstance().getXboxWheel().getX()*450/120;
        }

        if (brake) {
            turn = 0;
            leftSpeed = 0;
            rightSpeed = 0;
            joystickSpeed = 0;
        }

        if (inThreshold){
            //DriveTrainTalon.getInstance().tankDrive(joystickSpeed/3, joystickSpeed/3);
            DriveTrainFalcon.getInstance().tankDrive(joystickSpeed/3, joystickSpeed/3);
        } else if (Math.abs(joystickSpeed) < 0.05) {
            System.out.println("Turn: " + turn);
            //DriveTrainTalon.getInstance().tankDrive(turn/20, -turn/20);
            DriveTrainFalcon.getInstance().tankDrive(turn/20, -turn/20);
        } else if (turn < -0.05){
            //DriveTrainTalon.getInstance().tankDrive(rightSpeed * joystickSpeed/3, leftSpeed * joystickSpeed/3);
            DriveTrainFalcon.getInstance().tankDrive(rightSpeed*joystickSpeed/3, leftSpeed*joystickSpeed/3);
        } else if (turn > 0.05){
            //DriveTrainTalon.getInstance().tankDrive(leftSpeed * joystickSpeed/3, rightSpeed * joystickSpeed/3);
            DriveTrainFalcon.getInstance().tankDrive(leftSpeed * joystickSpeed/3, rightSpeed * joystickSpeed/3);
        }

//        if(Math.abs(joystickSpeed) < 0.05){
//            DriveTrainTalon.getInstance().tankDrive(turn, - turn);
//            //DriveTrainSparks.getInstance().tankDrive(newTurn, - newTurn);
//
//        }
//        else if(joystickSpeed >= 0){
//            DriveTrainTalon.getInstance().tankDrive(joystickSpeed + leftSpeed, joystickSpeed - rightSpeed);
//            //DriveTrainSparks.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
//        } else {
//            DriveTrainTalon.getInstance().tankDrive(joystickSpeed - leftSpeed, joystickSpeed + rightSpeed);
//            //DriveTrainSparks.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
//        }

    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished() {
        return false;
    }
}


