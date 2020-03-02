package com.github.mittyrobotics.interfaces;

public interface IPistonSubsystem extends ISubsystem{
    boolean isPistonExtended();
    void up();
    void down();
}
