package src.mvc;

//import tools.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model extends Bean {

    public static int START_X = 125;
    public static int START_Y = 125;
    public static int WORLD_HEIGHT = 250;
    public static int WORLD_WIDTH = 250;

    private ArrayList<Line> path = new ArrayList<>();
    private Point currentLocation = new Point(START_X, START_Y);
    private Boolean pen = true;
    private Color color = Color.BLACK;

    public ArrayList<Line> getPath() {
        return path;
    }
    public Point getLocation() {
        return currentLocation;
    }
    public Boolean isPen() {
        return pen;
    }
    public void setPen(Boolean set) {
        pen = set;
        firePropertyChange("pen", !pen, pen);
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color newColor) {
        Color oldColor = color;
        color = newColor;
        firePropertyChange("color", oldColor, color);
    }

    public void addLine(Point endPoint, Color color) {
        Line newLine = new Line(currentLocation, endPoint, color);
        path.add(newLine);
        //firePropertyChange()
        updateLocation(endPoint);
    }

    public void updateLocation(Point newLocation) {
        Point lastLocation = currentLocation;
        currentLocation = newLocation;
        firePropertyChange("currentLocation", lastLocation, currentLocation);
    }

    public void reset() {
        path = new ArrayList<>();
        currentLocation = new Point(START_X, START_Y);
        pen = true;
        color = Color.BLACK;
        firePropertyChange("Clear", false, true);
    }

    class Line implements Serializable {
        private Point startPoint;
        private Point endPoint;
        private Color color;

        public Line(Point point1, Point point2, Color color) {
            startPoint = point1;
            endPoint = point2;
            this.color = color;
        }

        public Point getStartPoint() {
            return startPoint;
        }

        public Point getEndPoint() {
            return endPoint;
        }

        public Color getColor() {
            return color;
        }
    }

}
