package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureSteering extends CommandBase {

    CurvatureSteering(){
        //addRequirements(DriveTrainTalon.getInstance());
        addRequirements(DriveTrainFalcon.getInstance());
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

        double turn = (OI.getInstance().getXboxWheel().getX()*450); //steering wheel
        double joystickSpeed = -OI.getInstance().getJoystick1().getY(); //joystick
        boolean brake = OI.getInstance().getJoystick1().getTrigger(); //brake
        final double radiusE = 5;
        double radius = Math.abs(450/turn * radiusE); //radius of imaginary circle
        double halfWidthRobot = 12.5; //from center of robot to imaginary circle

        double leftSpeed = 0;
        double rightSpeed = 0;
        double threshold = 3;
        boolean inThreshold = Math.abs(turn) < threshold;
        double turnScale = 1;
        //testing for 10 degrees to the left
        if (turn >= threshold){
            leftSpeed = (radius + halfWidthRobot)*(2*Math.PI);
            rightSpeed = (radius - halfWidthRobot)*(2*Math.PI);
            rightSpeed = (rightSpeed/leftSpeed) * turnScale ;
            leftSpeed = 1 * turnScale;
        } else if (turn <= -threshold){
            leftSpeed = (radius - halfWidthRobot)*(2*Math.PI);
            rightSpeed = (radius + halfWidthRobot)*(2*Math.PI);
            leftSpeed = (leftSpeed/rightSpeed) * turnScale;
            rightSpeed = 1 * turnScale;
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
            //DriveTrainTalon.getInstance().tankDrive(joystickSpeed*0.67, joystickSpeed*0.67);
            DriveTrainFalcon.getInstance().tankDrive(joystickSpeed/3, joystickSpeed/3);
        } else if (Math.abs(joystickSpeed) < 0.05) {
            //System.out.println("Turn: " + turn);
            //DriveTrainTalon.getInstance().tankDrive(turn/20, -turn/20);
            //System.out.println("in-place");
            DriveTrainFalcon.getInstance().tankDrive(-turn/350, turn/350);
        } else {
            System.out.println("Turn: " + turn);
            System.out.println("Right Speed: " + rightSpeed);
            System.out.println("Left Speed: " + leftSpeed);

            DriveTrainFalcon.getInstance().tankDrive(rightSpeed * joystickSpeed/3, leftSpeed * joystickSpeed/3);
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


