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

public class TestKalman1 {
    public static void main(String[] args) {
        double dt = 0.02;
        SimpleMatrix A = new SimpleMatrix(new double[][]{{1}});
        SimpleMatrix B = new SimpleMatrix(new double[][]{{dt}});
        SimpleMatrix H = new SimpleMatrix(new double[][]{{1}});
        SimpleMatrix Q = new SimpleMatrix(new double[][]{{5}});
        SimpleMatrix R = new SimpleMatrix(new double[][]{{10}});

        KalmanFilter filter = new KalmanFilter(A, B, H, Q, R);

        Graph graph = new Graph();

        double totalTime = 1000;

        double lastX = 0;
        for(double t = 0; t < totalTime; t+= dt){

            double x = t*t*5;
            if(t > 5){
                x = lastX;
            }
            double vel = (x-lastX)/dt;

            double noiseX = ((Math.random()*2)-1)*10;

            graph.addToSeries("Actual Pos", new XYDataItem(t, x));

            SimpleMatrix u = new SimpleMatrix(new double[][]{
                    {vel*5}
            });

            filter.predict(u);

            graph.addToSeries("Predict Pos", new XYDataItem( t,  filter.getxHat().get(0)));
            SimpleMatrix z = new SimpleMatrix(new double[][]{
                    {x+noiseX},
            });
            filter.correct(z);

            graph.addToSeries("Measurement Pos", new XYDataItem(t, z.get(0)));

            graph.addToSeries("Estimate", new XYDataItem(t, filter.getxHat().get(0)));

            lastX = x;

            try {
                Thread.sleep((long) (dt*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
