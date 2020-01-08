/*
package com.github.mittyrobotics;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Subsystem {
}
*/




package com.github.mittyrobotics;

        import com.ctre.phoenix.motorcontrol.ControlMode;
        import com.ctre.phoenix.motorcontrol.FeedbackDevice;
        import com.ctre.phoenix.motorcontrol.NeutralMode;
        import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
        import com.github.mittyrobotics.datatypes.motion.MotionState;
        import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
        import com.github.mittyrobotics.motionprofile.TrapezoidalMotionProfile;
        import edu.wpi.first.wpilibj.DigitalInput;
        import edu.wpi.first.wpilibj.Encoder;
        import edu.wpi.first.wpilibj.Joystick;
        import edu.wpi.first.wpilibj2.command.Command;
        import edu.wpi.first.wpilibj2.command.PIDCommand;
        import edu.wpi.first.wpilibj2.command.PIDSubsystem;
        import edu.wpi.first.wpilibj2.command.SubsystemBase;

        import java.util.Set;

public class Subsystem extends SubsystemBase {
    private WPI_TalonSRX exampletalon;
    private double count = 0;


    private static Subsystem instance;
    public static Subsystem getInstance(){
        if(instance == null){
            instance = new Subsystem();
        }
        return instance;
    }
    private Subsystem(){
        super();
        setName("Subsystem");
        setDefaultCommand(new Command() {
            @Override
            public Set<edu.wpi.first.wpilibj2.command.Subsystem> getRequirements() {
                return null;
            }
        });
    }

    //Function that runs continuously regardless of any commands being run
    @Override
    public void periodic(){

    }
    private Joystick joystick1;
    private DigitalInput switch1, switch2;
    private WPI_TalonSRX talon1;
    private Encoder encoder1;
    private double speed = 0;
    private TrapezoidalMotionProfile pidthing;


    //Function to initialize the hardware
    public void initHardware(){
        talon1 = new WPI_TalonSRX(5);
        joystick1 = new Joystick(0);
        switch1 = new DigitalInput(0);
        switch2 = new DigitalInput(1);
        talon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    public TrapezoidalMotionProfile getPidthing(double t) {
        if(pidthing == null){
            pidthing = new TrapezoidalMotionProfile(new MotionState(0, 0), new MotionState(5, 5), new VelocityConstraints(0, 0, 0));
        }
        return pidthing;
    }
    public int getTicks(){
        return talon1.getSelectedSensorPosition();
    }
    public void Manual() {

        if (joystick1.getRawButtonPressed(2)) {
            speed = speed + 0.25;
            if(speed == 1){
                speed = 0;
            }
        }
        System.out.println(speed);
        System.out.println("Position" + talon1.getSelectedSensorPosition());



        double direction = joystick1.getY();
        if(direction > 0.05){
            direction = 1;
        }
        else if(direction < -0.05){
            direction = -1;
        }
        else{
            direction = 0;
        }
        System.out.println("Speed" + speed);

        System.out.println("Switch1" + switch1.get());
        System.out.println("Switch2" + switch2.get());
        if (switch1.get()) {
            talon1.setSelectedSensorPosition(0);
            talon1.set(ControlMode.Position, 5 * 117.91)

        }
        else if (switch2.get()) {
            System.out.println("Ticks" + talon1.getSelectedSensorPosition());
            talon1.set(ControlMode.Position, -5 * 117.91)


        }

        if (!switch1.get() && !switch2.get()) {
            talon1.set(ControlMode.PercentOutput, direction * speed);

        }

        else {
            talon1.set(ControlMode.Position, 0.5 * 4939);
        }
    }

}
