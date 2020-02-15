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
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.GraphManager;

import java.awt.*;

public class GraphPathTest {
    public static void main(String[] args) {
        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.TRENCH_STARTING_POINT, 180),
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180)
                })
        );

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180),
                        new Transform(AutonCoordinates.PICKUP_LAST_TRENCH, 180)
                })
        );

        Path path3 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.PICKUP_LAST_TRENCH, 0),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 45)
                })
        );

        Path path4 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 180 + 45),
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 110),
                        new Transform(AutonCoordinates.BALL_5,110)
                })
        );

        Path path5 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_5,180+110),
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 180 + 110),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 180 + 180 + 45)
                })
        );
        Graph graph = new Graph();
        graph.getChart().removeLegend();
        for(double t = 0; t < 1; t+=0.01){
            graph.addDataset(GraphManager.getInstance().graphArrow(path1.getTransform(t),1,1,"Arrow" + t, Color.red));
            graph.addDataset(GraphManager.getInstance().graphArrow(path2.getTransform(t),1,1,"Arrow1" + t,
                    Color.green));
            graph.addDataset(GraphManager.getInstance().graphArrow(path3.getTransform(t),1,1,"Arrow2" + t, Color.blue));
            graph.addDataset(GraphManager.getInstance().graphArrow(path4.getTransform(t),1,1,"Arrow3" + t,
                    Color.yellow));
            graph.addDataset(GraphManager.getInstance().graphArrow(path5.getTransform(t),1,1,"Arrow4" + t, Color.cyan));
        }
    }
}
