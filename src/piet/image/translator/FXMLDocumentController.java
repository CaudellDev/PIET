/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piet.image.translator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 *
 * @author Tyler
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    MenuItem openMenu;
    
    @FXML
    ImageView imageView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
            }
        });
    }    
    
}
