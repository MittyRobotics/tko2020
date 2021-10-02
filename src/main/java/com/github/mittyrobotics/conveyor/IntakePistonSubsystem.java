/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.util.interfaces.IPistonSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake Piston Subsystem to extend or retract the {@link IntakeSubsystem}
 */
public class IntakePistonSubsystem extends SubsystemBase implements IPistonSubsystem {

    /**
     * {@link IntakeSubsystem} instance
     */
    private static IntakePistonSubsystem instance;

    /**
     * Intake piston's {@link DoubleSolenoid}
     */
    private DoubleSolenoid intakePiston;

    private boolean extended;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Intake Piston"
     */
    private IntakePistonSubsystem() {
        super();
        setName("Intake Piston");
    }

    /**
     * Returns the {@link IntakePistonSubsystem} instance.
     *
     * @return the {@link IntakePistonSubsystem} instance.
     */
    public static IntakePistonSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakePistonSubsystem();
        }
        return instance;
    }

    /**
     * Update the intake piston's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intake Extended", isPistonExtended());
    }

    /**
     * Initialize the intake piston's hardware
     */
    @Override
    public void initHardware() {
        intakePiston =
                new DoubleSolenoid(IntakeConstants.SOLENOID_FORWQRD_CHANNEL, IntakeConstants.SOLENOID_REVERSE_CHANNEL);
    }

    /**
     * Returns if the intake piston is extended
     *
     * @return if the {@link IntakeSubsystem} is extended
     */
    @Override
    public boolean isPistonExtended() {
        return intakePiston.get() != DoubleSolenoid.Value.kReverse;
    }


    public boolean getPistonExtended() {
        return extended;
    }

    public void setPistonExtended(boolean extended) {
        this.extended = extended;
    }

    /**
     * Extends the {@link IntakeSubsystem}
     */
    @Override
    public void extendPiston() {
        intakePiston.set(DoubleSolenoid.Value.kForward);
//        ConveyorSubsystem.getInstance().resetBallCount();
    }

    /**
     * Retracts the {@link IntakeSubsystem}
     */
    @Override
    public void retractPiston() {
        intakePiston.set(DoubleSolenoid.Value.kReverse);
//        ConveyorSubsystem.getInstance().resetBallCount();
    }
}