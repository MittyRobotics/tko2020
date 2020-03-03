package com.github.mittyrobotics.util.interfaces;

/**
 * Interface for all Piston based Subsystem classes
 */
public interface IPistonSubsystem extends ISubsystem{
    boolean isPistonExtended();
    void extendPiston();
    void retractPiston();
}
