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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class PietImageTranslator 
        extends Application 
        implements PietProgram.InIntCallback, 
                   PietProgram.InStrCallback, 
                   PietProgram.OutIntCallback, 
                   PietProgram.OutStrCallback {
    
    private Stage stage;
    private PietProgram program;
    private PietPanel pietPanel;
    
    @FXML private MenuItem openMenu;
    @FXML private MenuItem aboutMenu;
    
    @FXML private Button stepBtn;
    @FXML private Button runBtn;
    @FXML private Button resetBtn;
    @FXML private Button clearBtn;
    
    @FXML private ImageView imageView;
    @FXML private TextArea outputTextArea;
    
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
                
                program.setInIntCallback(this);
                program.setInStrCallback(this);
                program.setOutIntCallback(this);
                program.setOutStrCallback(this);
                
                Image imgViewImage = new Image(file.toURI().toString());
                imageView.setImage(imgViewImage);
                outputTextArea.clear();
                
            } catch (IOException ex) {
                Logger.getLogger(PietImageTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == aboutMenu) {
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
        } else if (event.getSource() == stepBtn) {
            System.out.println("Step button has been pressed.");
            if (program != null && !program.isComplete()) {
                program.step();
            }
        } else if (event.getSource() == runBtn) {
            System.out.println("Step button has been pressed.");
            if (program != null && !program.isComplete()) {
                program.run();
            }
        } else if (event.getSource() == resetBtn) {
            if (program != null) {
                program.reset();
                outputTextArea.clear();
            }
        } else if (event.getSource() == clearBtn) {
            outputTextArea.clear();
        }
    }  
    
    @Override
    public void start(final Stage stage) throws Exception {
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

    @Override
    public int onInInt() {
        return 0;
    }

    @Override
    public String onInStr() {
        return "";
    }

    @Override
    public void onOutInt(int value) {
        System.out.print(value);
        outputTextArea.appendText(String.valueOf(value));
    }

    @Override
    public void onOutStr(String value) {
        System.out.print(value);
        outputTextArea.appendText(value);
    }
    
}
