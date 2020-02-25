package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ConveyorConstants;
import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FourBallConveyorIndexCommand extends CommandBase {

    private double distance, initialPosition, currentPosition;
    private boolean isDone;

    public FourBallConveyorIndexCommand(double distance) {
        super();
        this.distance = distance;
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        //ConveyorSubsystem.getInstance().moveConveyor(distance);
        initialPosition = ConveyorSubsystem.getInstance().getPosition();
        currentPosition = initialPosition;
        isDone = false;
        ConveyorSubsystem.getInstance().setConveyorSpeed(1);
    }

    @Override
    public void execute() {
//        if (Conveyor.getInstance().hasBallCountChanged()) {
//            if (Conveyor.getInstance().getTotalBallCount() < 5) {
//                Conveyor.getInstance().moveConveyor(distance);
//            } else {
//                Conveyor.getInstance().setConveyorSpeed(0);
//            }
//        }

        if ((currentPosition - initialPosition) >
                (distance * ConveyorConstants.TICKS_PER_BALL_INCH)) {
            isDone = true;
        }
        currentPosition = ConveyorSubsystem.getInstance().getPosition();


//        if (ConveyorSwitches.getInstance().getSwitch1()) {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(Constants.CONVEYOR_SPEED);
//        } else {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
//            isDone = true;
//        }
        System.out.println(currentPosition);
        System.out.println(initialPosition);
        System.out.println((currentPosition - initialPosition) / ConveyorConstants.TICKS_PER_BALL_INCH);
    }


    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().setConveyorSpeed(0);
//        Buffer.getInstance().manualBufferSpeed(0);
        System.out.println("end");
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

}
