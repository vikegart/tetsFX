import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private double eps;

    private SimpleDoubleProperty x0, y0;
    private SimpleIntegerProperty dotsAmount;

    public double getX0() {
        return x0.get();
    }
    public SimpleDoubleProperty x0Property() {
        return x0;
    }

    public double getY0() {
        return y0.get();
    }
    public SimpleDoubleProperty y0Property() {
        return y0;
    }

    public int getDotsAmount() {
        return dotsAmount.get();
    }
    public SimpleIntegerProperty dotsAmountProperty() {
        return dotsAmount;
    }

    public XYChart.Series series;
    private List<Point2D> dotsList;
    private List<Point2D> dotsDerivativeList;
    private double x_, y_;

    public Graph(double x0, double y0, double eps){
        this.x0 = new SimpleDoubleProperty(x0);
        this.y0 = new SimpleDoubleProperty(y0);
        x_ = 0;
        y_ = 2;
        this.eps = eps;
        dotsAmount = new SimpleIntegerProperty(0);
        //
        Point2D firstPoint = new Point2D(x0, y0);
        Point2D derivativePoint = new Point2D(0, 0);
        dotsList = new ArrayList<>();
        dotsList.add(firstPoint);

        dotsDerivativeList = new ArrayList<>();
        dotsDerivativeList.add(derivativePoint);
        series = new XYChart.Series();
        series.getData().add(new XYChart.Data(x0, y0));
        System.out.println(x0 + "\t" + y0);
        //
    }

    public void addDots(){
        Point2D lastPoint;
        Point2D lastDerivative;
        if(dotsList.size() > 0){
            lastPoint = dotsList.get(dotsList.size() - 1);
            lastDerivative = dotsDerivativeList.get(dotsList.size() - 1);
        }else{
            lastPoint = new Point2D(x0.getValue(), y0.getValue());
            lastDerivative = new Point2D(x_, y_);
        }

        double x_ = lastDerivative.getX();
        double y_ = lastDerivative.getY();
        double x = lastPoint.getX();
        double y = lastPoint.getY();
        double t = dotsAmount.get() * 200 * eps;
        for (int i = 0; i < 1000; i++) {
            t += eps;
            double newX = getX(x, y, x_, y_, t);
            double newY = getY(x, y, x_, y_, t);
            x = newX;
            y = newY;
            double newX_ = getX_(x, y, x_, y_, t);
            double newY_ = getY_(x, y, x_, y_, t);
            x_ = newX_;
            y_ = newY_;
            if(i % 50 == 0 && i != 0){
                series.getData().add(new XYChart.Data(x, y));
                dotsList.add(new Point2D(x, y));
                dotsDerivativeList.add(new Point2D(x_, y_));
                //System.out.print("Add dots: " + x + "\t" + y + "\t delta: " + newX + "\t" + newY);
                //System.out.println();
            }
        }
        dotsAmount.setValue(dotsAmount.getValue() + 1);
    }

    public void removeDots(){
        try{
            for (int i = 0; i < 1000; i++) {
                if(i % 50 == 0 && i != 0){
                    series.getData().remove(series.getData().size() - 1);
                    dotsList.remove(dotsList.size() - 1);
                    dotsDerivativeList.remove(dotsDerivativeList.size() - 1);
                }
            }
            dotsAmount.setValue(dotsAmount.getValue() - 1);
        }catch (Exception e){

        }
    }
    //
    private double getX(double x, double y, double x_, double y_, double t){
        return x + eps * getX_(x, y, x_, y_, t);
    }
    private double getY(double x, double y, double x_, double y_, double t){
        return y + eps * getY_(x, y, x_, y_, t);
    }
    private double getX_(double x, double y, double x_, double y_, double t){
        return x_ + eps * getX__(x, y, x_, y_, t);
    }
    private double getY_(double x, double y, double x_, double y_, double t){
        return y_ + eps * getY__(x, y, x_, y_, t);
    }

/*
    private double getX__(double x, double y, double x_, double y_, double t){
        return 4 * x + y * Math.sin(t);
    }
    private double getY__(double x, double y, double x_, double y_, double t){
        return -3 * x + 2 * y;
    }
*/

    /*
    private double getX__(double x, double y, double x_, double y_){
        return 6 * x + 2 * y;
    }
    private double getY__(double x, double y, double x_, double y_){
        return 3 * x + 7 * y;
    }
    */


    private double getX__(double x, double y, double x_, double y_, double t){
        return 3 * y_ + 2 * x;
    }
    private double getY__(double x, double y, double x_, double y_, double t){
        return -3 * x_ + 2 * y;
    }


}
