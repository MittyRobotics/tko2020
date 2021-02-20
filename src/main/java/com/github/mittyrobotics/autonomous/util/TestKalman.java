package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.motion.observers.KalmanFilter;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.visualization.Graph;
import com.github.mittyrobotics.visualization.XYSeriesWithRenderer;
import org.ejml.simple.SimpleMatrix;
import org.jfree.data.xy.XYDataItem;

public class TestKalman {
    public static void main(String[] args) {
        double dt = 0.02;
        SimpleMatrix A = new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }); //transition matrix
        SimpleMatrix B = new SimpleMatrix(new double[][]{
                {dt, 0},
                {0, dt},
                {1, 0},
                {0, 1}
        }); //control matrix
        SimpleMatrix H = new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }); //measurement matrix
        SimpleMatrix Q = new SimpleMatrix(new double[][]{
                {.005, 0, 0, 0},
                {0, .005, 0, 0},
                {0, 0, .1, 0},
                {0, 0, 0, .1}
        }); //process noise
        SimpleMatrix R = new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }); //measurement noise
        KalmanFilter filter = new KalmanFilter(A, B, H, Q, R);

        Graph graph = new Graph();
        graph.addSeries(new XYSeriesWithRenderer("Actual Vel X", null, false, true, null));
        graph.addSeries(new XYSeriesWithRenderer("Actual Vel Y", null, false, true, null));
        graph.addSeries(new XYSeriesWithRenderer("Actual Pos", null, false, true, null));
//        graph.addSeries(new XYSeriesWithRenderer("Predict", null, false, true, null));
        graph.addSeries(new XYSeriesWithRenderer("Estimate", null, false, true, null));
        double totalTime = 4;
        double lastX = 0;
        double lastY = 0;

        double lastXPredict = 0;
        double lastYPredict = 0;
        double lastVel = 0;

        Path path = new Path(PathGenerator.generateQuinticHermiteSplinePath(new Transform[]{new Transform(0, 0, 0), new Transform(4, 4, 0)}));

        for(double t = 0; t < totalTime; t+= dt){
            Position pos = path.getPosition(t/totalTime);

            double x = pos.getX();
            double velX = (x-lastX)/dt;

            double y = pos.getY();
            double velY = (y-lastY)/dt;

            double noiseX = ((Math.random()*2)-1)*0.5;
            double noiseY = ((Math.random()*2)-1)*0.5;

            graph.addToSeries("Actual Pos", new XYDataItem(x, y));
//            graph.addToSeries("Actual Vel X", new XYDataItem(t, velX));
//            graph.addToSeries("Actual Vel Y", new XYDataItem(t, velY));

            SimpleMatrix u = new SimpleMatrix(new double[][]{
                    {velX*.8},
                    {velY*.8}
            });

//            System.out.println(A.mult(filter.getxHat()));

            filter.predict(u);

            System.out.println(u.get(0) + " " + velY);

            lastXPredict = lastXPredict + u.get(0)*dt;
            lastYPredict = lastYPredict + u.get(1)*dt;

            graph.addToSeries("Predict Pos", new XYDataItem( lastXPredict,  lastYPredict));
//            graph.addToSeries("Predict Vel X", new XYDataItem(t, filter.getxHat().get(2)));
//            graph.addToSeries("Predict Vel Y", new XYDataItem(t, filter.getxHat().get(3)));
            SimpleMatrix z = new SimpleMatrix(new double[][]{
                    {x+noiseX},
                    {y+noiseY},
                    {velX},
                    {velY}
            });
            filter.correct(z);

            graph.addToSeries("Measurement Pos", new XYDataItem(z.get(0), z.get(1)));

            graph.addToSeries("Estimate", new XYDataItem(filter.getxHat().get(0), filter.getxHat().get(1)));
//            graph.addToSeries("Estimate Vel X", new XYDataItem(t, filter.getxHat().get(2)));
//            graph.addToSeries("Estimate Vel Y", new XYDataItem(t, filter.getxHat().get(3)));

            lastX = x;
            lastY = y;

            try {
                Thread.sleep((long) (dt*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
