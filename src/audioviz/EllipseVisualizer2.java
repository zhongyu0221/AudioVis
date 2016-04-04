/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author dale
 */
public class EllipseVisualizer2 implements Visualizer {

    private final String name = "Ellipse Visualizer 2";

    private Integer numBands;

    private AnchorPane vizPane;

    private final Double bandPercentage = 2.0;
    private final Double minEllipseRadius = 5.0;  // keep the shape
    private final Double rotatePhaseMultiplier = 300.0;

    private Double width = 0.0;
    private Double height = 0.0;

    private Double bandWidthrow = 0.0;
    private Double bandHeightrow = 0.0;
    private Double halfBandHeightrow = 0.0;

    private Double bandWidthcol = 0.0;
    private Double bandHeightcol = 0.0;
    private Double halfBandHeightcol = 0.0;

    private final Double startHue = 200.0;
    private final Double startHuecol = 260.0;

    private Ellipse[] ellipses1;
    private Ellipse[] ellipses2;
    private Ellipse[] ellipses3;

    public EllipseVisualizer2() {
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

        bandWidthrow = width / numBands;
        bandHeightrow = bandWidthrow;

        bandWidthcol = width*bandPercentage;
        bandHeightcol = height / numBands;

        halfBandHeightcol = bandHeightcol / 2;

        ellipses1 = new Ellipse[numBands];
        ellipses2 = new Ellipse[numBands];
        ellipses3 = new Ellipse[numBands];

        for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidthrow / 2 + bandWidthrow * i);
            ellipse.setCenterY(0);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses1[i] = ellipse;
        }

        for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidthrow / 2 + bandWidthrow * i);
            ellipse.setCenterY(height);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses2[i] = ellipse;
        }

       for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(width / 2);
            ellipse.setCenterY(bandHeightcol / 2 + bandHeightcol * i);

            ellipse.setRadiusX(bandHeightcol/2);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses3[i] = ellipse;
        }

        //Image clockFaceImage = new Image(this.getClass().getResourceAsStream("clockface.png"));
        //doge.setImage(clockFaceImage);
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
            ellipses1[i].setRadiusY( minEllipseRadius);
            ellipses1[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));//hue is the angle
            //double hue, double saturation, double brightness, double opacity
            //starthue is the color when no sound
        }

        if (ellipses2 == null) {
            return;
        }

       Integer num2 = min(ellipses2.length, magnitudes.length);//return the smaller value of this two

        for (int i = 0; i < num2; i++) {
            ellipses2[i].setRadiusY( minEllipseRadius);
            ellipses2[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
        }
        
        if (ellipses3 == null) {
            return;
        }
        Integer num3 = min(ellipses2.length, magnitudes.length);
        for (int i = 0; i < num; i++) {
            ellipses3[i].setRadiusX(((60.0 + magnitudes[i]) / 60.0) *bandWidthcol/2 + minEllipseRadius);
            ellipses3[i].setFill(Color.hsb(startHuecol - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            ellipses3[i].setRotate(phases[i] * rotatePhaseMultiplier);
        }
    }
}
