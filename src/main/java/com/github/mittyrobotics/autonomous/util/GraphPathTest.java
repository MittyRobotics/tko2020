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

package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.geometry.Circle;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;

import java.awt.*;
import java.util.Random;

public class GraphPathTest {
    public static void main(String[] args) {

        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(
                                AutonCoordinates.TRENCH_STARTING_POINT.add(AutonCoordinates.ROBOT_FRONT_TO_CENTER),
                                180),
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180)
                })
        );

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 180), new Transform(AutonCoordinates.BALL_BACK_TRENCH_INNER
                        .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(-10))), 180 - 10),
                })
        );

        Path path3 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_INNER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(-10))), -10),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 0),
                })
        );


        Path path4 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 180),
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_OUTER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(10))), 180 + 10)
                })
        );


        Path path5 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_OUTER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(10))), 10),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 0),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 45)
                })
        );
        Path[] paths =  new Path[]{
                path1,
                path2,
                path3,
                path4,
                path5
        };

    }
}
