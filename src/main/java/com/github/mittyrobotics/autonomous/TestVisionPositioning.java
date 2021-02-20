package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.visualization.Graph;
import com.github.mittyrobotics.visualization.GraphUtil;
import com.github.mittyrobotics.visualization.XYSeriesWithRenderer;
import org.jfree.data.xy.XYDataItem;

public class TestVisionPositioning {
    public static void main(String[] args) {
        Rotation robotRotation = Rotation.fromDegrees(20);
        Rotation turretRotation = Rotation.fromDegrees(10);
        Rotation yaw = Rotation.fromDegrees(12);
        Rotation pitch = Rotation.fromDegrees(10);
        VisionTarget target = new VisionTarget(yaw, pitch, Vision.getInstance().calculateDistanceToTarget(pitch));
        Transform cameraTransform = Vision.getInstance().calculateCameraRelativeTransform(target);
        System.out.println(cameraTransform);
        Transform turretTransform = Vision.getInstance().calculateTurretRelativeTransform(cameraTransform);
        System.out.println(turretTransform);
        Transform turretFieldTransform = Vision.getInstance().calculateTurretFieldTransform(turretTransform, turretRotation, robotRotation);
        System.out.println(turretFieldTransform);
        Transform robotTransform = Vision.getInstance().calculateRobotFieldTransform(turretFieldTransform, robotRotation);
        Graph graph = new Graph();
        graph.scaleGraphToScale(.3, 0, -65);
        graph.addToSeries("Target", GraphUtil.populateSeries(new XYSeriesWithRenderer("Target"), GraphUtil.rectangle(new Transform(AutonCoordinates.SCORING_TARGET), 20, 2)));
        graph.addToSeries("Estimate", GraphUtil.populateSeries(new XYSeriesWithRenderer("Estimate"), GraphUtil.arrow(turretFieldTransform, 20, 5)));
        graph.addToSeries("Turret", GraphUtil.populateSeries(new XYSeriesWithRenderer("Turret"), GraphUtil.arrow(new Transform(turretFieldTransform.getPosition(), turretRotation.add(robotRotation)), 20, 5)));
        graph.addToSeries("Robot", GraphUtil.populateSeries(new XYSeriesWithRenderer("Robot"), GraphUtil.rectangle(robotTransform, 20, 30)));
        graph.addToSeries("Robot Arrow", GraphUtil.populateSeries(new XYSeriesWithRenderer("Robot Arrow"), GraphUtil.arrow(robotTransform, 15, 4)));
        graph.addToSeries("Yaw", GraphUtil.populateSeries(new XYSeriesWithRenderer("Yaw"), GraphUtil.arrow(new Transform(turretFieldTransform.getPosition(), turretRotation.add(robotRotation).add(yaw)), 20, 5)));
    }
}
