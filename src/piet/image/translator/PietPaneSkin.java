/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piet.image.translator;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

/**
 *
 * @author Tyler
 */
public class PietPaneSkin implements Skin<PietPane>, PietPaneAPI {
    
    PietPane control;
    PietProgram pietProgram;
    
    Group rootNode = new Group();
    Label label = null;
    
    public PietPaneSkin(PietPane control) {
        this.control = control;
    }

    @Override
    public PietPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return rootNode;
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
