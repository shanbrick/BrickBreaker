package SwingShapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

public class RectangleShape {
    private RectangularShape shape;
    private Color fillColor, borderColor;
    private double rotation;
    private int borderThickness;

    public RectangleShape(RectangularShape shape) {
    	this.shape = shape;
    	this.fillColor = Color.red;
    	this.borderColor = Color.red;
    	this.borderThickness = 0;
        setLocation(250, 225);
        setSize(100, 100);
    }
    
    public RectangleShape(RectangularShape shape, int x, int y) {
    	this.shape = shape;
    	this.fillColor = Color.red;
    	this.borderColor = Color.red;
    	this.borderThickness = 0;
    	setLocation(x,  y);
        setSize(100, 100);
    }
    
    public RectangleShape(RectangularShape shape, Color color) {
    	this.shape = shape;
    	this.fillColor = color;
    	this.borderColor = color;
    	this.borderThickness = 0;
        setLocation(250, 225);
        setSize(100, 100);
    }
    
    public int getXLocation() {
    	return (int)shape.getX();
    }
    
    public int getYLocation() {
    	return (int)shape.getY();
    }

    public void setLocation(int x, int y) {
    	shape.setFrame(x, y, shape.getWidth(), shape.getHeight());
    }
    
    public int getWidth() {
    	return (int)shape.getWidth();
    }
    
    public int getHeight() {
    	return (int)shape.getHeight();
    }
    
    public void setWidth(int w) {
    	shape.setFrame(shape.getX(), shape.getY(), w, shape.getHeight());
    }
    
    public void setHeight(int h) {
    	shape.setFrame(shape.getX(), shape.getY(), shape.getWidth(), h);
    }

    public void setSize(int w, int h) {
    	shape.setFrame(shape.getX(), shape.getY(), w, h);
    }

    public void setColor(Color c) {
    	fillColor = c;
    	borderColor = c;
    }
    
    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color c) {
    	this.fillColor = c;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(Color c) {
    	this.borderColor = c;
    }
    
    public double getRotation() {
        return rotation * 180 / Math.PI;
    }

    public void setRotation(double degrees) {
    	this.rotation = degrees * Math.PI / 180;
    }
    
    public int getBorderThickness() {
    	return borderThickness;
    }
    
    public void setBorderThickness(int thickness) {
    	this.borderThickness = thickness;
    }

    public void paint(Graphics2D brush) {
        brush.rotate(rotation, shape.getCenterX(), shape.getCenterY());

        brush.setColor(fillColor);
        brush.fill(shape);
        
		java.awt.Stroke oldStroke = brush.getStroke();
        brush.setColor(borderColor);
        brush.setStroke(new BasicStroke(borderThickness));
        brush.draw(shape);
		brush.setStroke(oldStroke);
		
        brush.rotate(-rotation, shape.getCenterX(), shape.getCenterY());
    }
}