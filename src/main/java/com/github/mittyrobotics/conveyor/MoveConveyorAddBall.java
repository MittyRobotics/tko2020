package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveConveyorAddBall extends CommandBase {

    private double distance, initialPosition, currentPosition;
    private boolean isDone = false;

    public MoveConveyorAddBall(double distance) {
        super();
        this.distance = distance;
        addRequirements(Conveyor.getInstance());
    }

    @Override
    public void initialize() {
        //ConveyorSubsystem.getInstance().moveConveyor(distance);
        initialPosition = Conveyor.getInstance().getPosition();
        currentPosition = initialPosition;
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

        if ((currentPosition - initialPosition) <
                (distance * com.github.mittyrobotics.conveyor.Constants.TICKS_PER_BALL_INCH)) {
            Conveyor.getInstance().setConveyorSpeed(1);
        } else {
            isDone = true;
        }
        currentPosition = Conveyor.getInstance().getPosition();


//        if (ConveyorSwitches.getInstance().getSwitch1()) {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(Constants.CONVEYOR_SPEED);
//        } else {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
//            isDone = true;
//        }
    }


    @Override
    public void end(boolean interrupted) {
        Conveyor.getInstance().setConveyorSpeed(0);
//        Buffer.getInstance().manualBufferSpeed(0);
        System.out.println("end");
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

}
