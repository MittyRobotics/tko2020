package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;

public class FieldRotation {
    Rotation angle;
    Position target;
    public FieldRotation(Rotation angle){
        this.angle = angle;
        this.target = null;
    }

    public FieldRotation(Position target){
        this.angle = null;
        this.target = target;
    }


    public Rotation getRotation(){
        return getRotation(null);
    }

    public Rotation getRotation(Position robotPosition){
        if(this.target != null || robotPosition == null){
            return robotPosition.angleTo(target).inverse();
        }
        else if(this.angle != null){
            return angle;
        }
        else{
            return new Rotation();
        }
    }
}
