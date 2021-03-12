package src.mvc;

import javax.swing.JPanel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JPanel implements PropertyChangeListener {

    protected Model model;

    public View(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setModel(Model model){
        this.model.removePropertyChangeListener(this);
        this.model = model;
        this.model.initSupport();
        this.model.addPropertyChangeListener(this);
        repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        for (int i = 0; i < model.getPath().size(); i++) {
            gc.setColor(model.getPath().get(i).getColor());
            Point startPoint = model.getPath().get(i).getStartPoint();
            Point endPoint = model.getPath().get(i).getEndPoint();
            gc.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
        if (model.isPen()) {
            gc.setColor(model.getColor());
            gc.fillOval(model.getLocation().x - 2, model.getLocation().y - 2, 4, 4);
        } else {
            gc.setColor(Color.BLACK);
            gc.drawOval(model.getLocation().x - 2, model.getLocation().y - 2, 4, 4);
        }
        gc.setColor(oldColor);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        repaint();
    }
}

