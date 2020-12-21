package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Moves the {@link ClimberSubsystem} to a certain position
 */
public class SetClimberPosition extends CommandBase {

    /**
     * Left and Right climber setpoints
     */
    private final double leftSetpoint, rightSetpoint;

    /**
     * PIDControllers to control the movement of the climbers
     */
    private PIDController leftController, rightController, auxController;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link ClimberSubsystem}
     *
     * @param leftSetpoint left position for climber to reach
     *
     * @param rightSetpoint right position for climber to reach
     */
    public SetClimberPosition(double leftSetpoint, double rightSetpoint){
        this.leftSetpoint = leftSetpoint;
        this.rightSetpoint = rightSetpoint;
        addRequirements(ClimberSubsystem.getInstance());
    }

    /**
     * Initializes and sets the setpoints for the PIDControllers
     */
    @Override
    public void initialize(){
        leftController = new PIDController(ClimberConstants.POSITION_P, ClimberConstants.POSITION_I, ClimberConstants.POSITION_D);
        rightController = new PIDController(ClimberConstants.POSITION_P, ClimberConstants.POSITION_I, ClimberConstants.POSITION_D);
        auxController = new PIDController(ClimberConstants.AUX_P, ClimberConstants.AUX_I, ClimberConstants.AUX_D);
        leftController.setSetpoint(leftSetpoint);
        rightController.setSetpoint(rightSetpoint);
        auxController.setSetpoint(leftSetpoint - rightSetpoint);
    }

    /**
     * Calculates and sets the motor values based on current position
     */
    @Override
    public void execute(){
        double left = leftController.calculate(ClimberSubsystem.getInstance().getLeftEncoderPosition());
        double right = rightController.calculate(ClimberSubsystem.getInstance().getRightEncoderPosition());
        double aux = auxController.calculate(ClimberSubsystem.getInstance().getLeftEncoderPosition() - ClimberSubsystem.getInstance().getRightEncoderPosition());
        ClimberSubsystem.getInstance().setSparks(left + aux, right - aux);
    }

    /**
     * Stops the climber motor
     */
    @Override
    public void end(boolean interrupted){
        ClimberSubsystem.getInstance().stopSparks();
    }

    /**
     * Returns if the command should end
     *
     * @return if both climber positions are within the threshold
     */
    @Override
    public boolean isFinished(){
        return (Math.abs(leftController.getPositionError()) < ClimberConstants.CLIMBER_THRESHOLD) && (Math.abs(rightController.getPositionError()) < ClimberConstants.CLIMBER_THRESHOLD);
    }
}