/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piet.image.translator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tyler
 */
public class PietImageTranslator extends Application {
    
    private Stage stage;
    private PietProgram program;
    private PietPanel pietPanel;
    
    @FXML private MenuItem openMenu = new MenuItem();
    @FXML private MenuItem aboutMenu = new MenuItem();
    @FXML private ImageView imageView = new ImageView();
    @FXML private SwingNode swingNode = new SwingNode();
    
    @FXML
    protected void handleButtonAction(ActionEvent event) {
        System.out.println("handleButtonAction() called.");
        if (event.getSource() == openMenu) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                final File file = fileChooser.showOpenDialog(stage);
                BufferedImage image = ImageIO.read(file);
                program = new PietProgram(image);

                if (pietPanel == null) {
                    pietPanel = new PietPanel();
                }

                pietPanel.loadPietProgram(program);
                pietPanel.setOpaque(true);

                if (file != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            swingNode.setContent(new JButton("Test"));
                            System.out.println("Swing Node content has been set.");
                        }
                    });
                }
            } catch (IOException ex) {
                Logger.getLogger(PietImageTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == aboutMenu) {
            System.out.println("About Menu clicked.");
            try {
                Stage aboutStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("AboutWindowFXML.fxml"));
                aboutStage.setScene(new Scene(root));
                aboutStage.initModality(Modality.APPLICATION_MODAL);
                aboutStage.initOwner(aboutMenu.getParentPopup().getScene().getWindow());
                aboutStage.setResizable(false);
                aboutStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }  
    
    @Override
    public void start(final Stage stage) throws Exception {
        System.out.println("JavaFX version: " + com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
        
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("MainPietLayout.fxml"));
        
        Scene scene = new Scene(root);
        
        
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
