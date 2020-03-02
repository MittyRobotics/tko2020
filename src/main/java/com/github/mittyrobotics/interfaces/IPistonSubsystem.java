package com.github.mittyrobotics.interfaces;

/**
 * Interface for all Piston based Subsystem classes
 */
public interface IPistonSubsystem extends ISubsystem{
    boolean isPistonExtended();
    void up();
    void down();
}
