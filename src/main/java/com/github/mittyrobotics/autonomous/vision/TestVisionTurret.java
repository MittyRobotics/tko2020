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

package com.github.mittyrobotics.autonomous.vision;

import com.github.mittyrobotics.autonomous.util.VisionTarget;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.GraphManager;

import java.awt.*;

public class TestVisionTurret {
    public static void main(String[] args) {
        Rotation pitch = new Rotation(5);
        Rotation yaw = new Rotation(-10);
        Rotation robotTurretYaw = new Rotation(0);
        Rotation gyro = new Rotation(45);
        VisionTarget target = Vision.getInstance().computeVisionTarget(pitch,yaw,robotTurretYaw,
                gyro);
        System.out.println(target);
        Graph graph = new Graph();
        graph.addDataset(GraphManager.getInstance().graphArrow(new Transform(0,0,target.getFieldRelativeYaw()),5,2,
                "field-relative vision yaw", Color.red));
        graph.addDataset(GraphManager.getInstance().graphArrow(new Transform(0,0,target.getTurretRelativeYaw()),5,2,
                "turret-relative vision yaw", Color.blue));
        graph.addDataset(GraphManager.getInstance().graphArrow(new Transform(0,0,gyro),5,2,
                "robot gyro", Color.yellow));
        graph.addDataset(GraphManager.getInstance().graphArrow(new Transform(0,0,yaw),5,2,
                "vision yaw", Color.green));
        graph.resizeGraph(-50,50,-50,50);
        graph.getChart().removeLegend();
    }
}
