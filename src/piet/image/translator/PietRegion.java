/*
 * The MIT License
 *
 * Copyright 2016 Tyler.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package piet.image.translator;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;

public class PietRegion extends Region {
    private static final double PREFERED_SIZE = 0;
    private static final double MINIMUM_SIZE = 0;
    private static final double MAXIMUM_SIZE = 0;
    
    // Piet Data
    PietProgram program;
    
    // Properties of the control
    private boolean toggle;
    private boolean _frameVisible;
    private BooleanProperty on;
    private BooleanProperty frameVisiblel;
    private AnimationTimer timer;
    
    // Graphical nodes
    private Canvas canvas;
    private GraphicsContext ctx;
    
    public PietRegion() {
        init();
        initGraphics();
    }
    
    private void init() {
        // setPrefSize
        if (Double.compare(getWidth(), 0) <= 0 ||
            Double.compare(getHeight(), 0) <= 0 ||
            Double.compare(getPrefWidth(), 0) <= 0 ||
            Double.compare(getPrefHeight(), 0) <= 0) {
            setPrefSize(PREFERED_SIZE, PREFERED_SIZE);
        }
        
        // setMinSize
        if (Double.compare(getMinWidth(), 0) <= 0 ||
            Double.compare(getMinHeight(), 0) <= 0) {
            setMinSize(MINIMUM_SIZE, MINIMUM_SIZE);
        }
        
        // setMaxSize
        if (Double.compare(getMaxWidth(), 0) <= 0 ||
            Double.compare(getMaxHeight(), 0) <= 0) {
            setMaxSize(MAXIMUM_SIZE, MAXIMUM_SIZE);
        }
    }
    
    private void initGraphics() {
        // Create the canvas node
        canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();
        
        // Add the canvas to the Scene Graph
        getChildren().add(canvas);
    }
    
    private void registerListeners() {
        // Register Listeners to control properties (eg for resizing)
        widthProperty().addListener(observable -> draw());
        heightProperty().addListener(observable -> draw());
//        frameVisibleProperty().addListener(observable -> draw());
        
        
    }
    
    private void draw() {
        double size = getWidth() < getHeight() ? getWidth() : getHeight();
        canvas.setWidth(size);
        canvas.setHeight(size);
        
        ctx.clearRect(0, 0, size, size);
        
        
    }
}
