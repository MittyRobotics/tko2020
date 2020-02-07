package com.GitHub.mittyrobotics.climber.commands;

import com.GitHub.mittyrobotics.climber.PistonValue;
import com.GitHub.mittyrobotics.climber.Hooks;
import com.GitHub.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class MoveHookPWM extends CommandBase {

    private RobotSide side;
    private PistonValue value;

    private double activePercent, inactivePercent, cycleTime, cycleModifier, totalMult, totalCount, counter;
    private boolean pistonActive = true;

    public MoveHookPWM(RobotSide side, PistonValue value, double activePercent, double cycleModifier) {
        super();
        this.side = side;
        this.value = value;
        this.activePercent = activePercent;
        this.cycleModifier = cycleModifier; //Affects the rate of cycles. Total cycle time is 1/cycleModifier.
        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() {
        inactivePercent = 1 - activePercent;
        cycleTime = Timer.getFPGATimestamp();

        totalMult = 1/activePercent;
        totalCount = 100 * totalMult;
        counter = 0;
        //activePercent *= 20;

        //One cycle is the period of time it takes for the piston to activate and deactivate once.
    }


    @Override
    public void execute() {
        //begins in active state
        //divides inactive/activePercent by cycleModifier to increase/decrease the rate of cycles
        /*
        if(Timer.getFPGATimestamp() - cycleTime > activePercent / cycleModifier) {
            pistonActive = false; //deactivate pistons once active time is over
            //TODO logic seems overall ok (it's a bit confusing, but makes sense), but I think a solution using % would be more efficient and simpler
            if(Timer.getFPGATimestamp() - cycleTime > activePercent / cycleModifier + inactivePercent / cycleModifier) {
                pistonActive = true; //reverts back to active state once cycle is over
                cycleTime = Timer.getFPGATimestamp();
            }
        }
        */

        //TEST METHOD 2

        if (counter / totalCount == 0.01) {
            pistonActive = true;
            counter = 0;
        } else {
            pistonActive = false;
        }
        counter++;

        //TEST METHOD 3
        /*
        if(Math.round(counter % 20) < activePercent){
            pistonActive = true;
        }else{
            pistonActive = false;
        }

        counter++;
        */



        if(pistonActive) {
            System.out.println("Active");
            Hooks.getInstance().push(side, value); //push in designated direction
        } else  {
            System.out.println("Inactive");
            Hooks.getInstance().push(side, PistonValue.OFF); //turn piston off
        }

        System.out.println(Hooks.getInstance().getSolenoidValue());
        System.out.println(counter);
        System.out.println(totalCount);
        System.out.println();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}


/*revision pseudo code: WIP
notes:
execute runs every 20ms
use a counter

logic:
(1/activePercent) = tempA
tempA * 100 = countTotal


if (countTotal / counter = 0.01) {
    push
    reset counter
} else {
off
}

counter ++


activePercent *= 100
if(counter %100 < activePercent){
push
}else{
off
}

counter++



*/