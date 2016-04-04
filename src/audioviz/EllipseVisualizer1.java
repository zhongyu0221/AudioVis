/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author dale
 */
public class EllipseVisualizer1 implements Visualizer {

    private final String name = "Ellipse Visualizer 1";

    private Integer numBands;

    private AnchorPane vizPane;

    private final Double bandHeightPercentage = 1.3;
    private final Double minEllipseRadius = 5.0;  // keep the shape

    private Double width = 0.0;
    private Double height = 0.0;

    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;

    private final Double startHue = 260.0;

    private Ellipse[] ellipses1;
    private Ellipse[] ellipses2;

    public EllipseVisualizer1() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void start(Integer numBands, AnchorPane vizPane) {//prepare the bojects and interface 
        end();

        this.numBands = numBands;//number of band ,provide the magnitude and phase in the update method
        this.vizPane = vizPane;

        height = vizPane.getHeight();
        width = vizPane.getWidth();

        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        ellipses1 = new Ellipse[numBands];
        ellipses2 = new Ellipse[numBands];

        for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * i);
            ellipse.setCenterY(15);
            ellipse.setRadiusX(bandWidth / 2);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses1[i] = ellipse;
        }

        for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * i);
            ellipse.setCenterY(height - 15);
            ellipse.setRadiusX(bandWidth / 2);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses2[i] = ellipse;
        }

    }

    @Override
    public void end() {
        if (ellipses1 != null) {
            for (Ellipse ellipse : ellipses1) {
                vizPane.getChildren().remove(ellipse);
            }
            ellipses1 = null;
        }

        if (ellipses2 != null) {
            for (Ellipse ellipse : ellipses2) {
                vizPane.getChildren().remove(ellipse);
            }
            ellipses2 = null;
        }
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {//magnitude array and phase array
        if (ellipses1 == null) {
            return;
        }

        Integer num = min(ellipses1.length, magnitudes.length);//return the smaller value of this two

        for (int i = 0; i < num; i++) {
            ellipses1[i].setRadiusY(((60.0 + magnitudes[i]) / 60.0)+ minEllipseRadius );
            ellipses1[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));//hue is the angle
            //double hue, double saturation, double brightness, double opacity
            //starthue is the color when no sound
        }

        if (ellipses2 == null) {
            return;
        }

        Integer num2 = min(ellipses2.length, magnitudes.length);//return the smaller value of this two

        for (int i = 0; i < num2; i++) {
            ellipses2[i].setRadiusY(((60.0 + magnitudes[i]) / 60.0)+ minEllipseRadius);
            ellipses2[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));//hue is the angle
            //double hue, double saturation, double brightness, double opacity
            //starthue is the color when no sound
        }
    }
}
