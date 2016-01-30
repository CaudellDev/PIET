package piet.image.translator;

import java.awt.Button;
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

public class PietImageTranslator 
        extends Application 
        implements PietProgram.InIntCallback, 
                   PietProgram.InStrCallback, 
                   PietProgram.OutIntCallback, 
                   PietProgram.OutStrCallback {
    
    private Stage stage;
    private PietProgram program;
    private PietPanel pietPanel;
    
    @FXML private MenuItem openMenu = new MenuItem();
    @FXML private MenuItem aboutMenu = new MenuItem();
    
    @FXML private Button stepBtn = new Button();
    
    @FXML private ImageView imageView = new ImageView();
    
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
        } else if (event.getSource() == stepBtn) {
            System.out.println("Step button has been pressed.");
            if (program != null && !program.isComplete()) {
                program.step();
            }
        } 
//        else if (event.getSource() == runBtn) {
//            if (program != null && !program.isComplete()) {
//                program.run();
//            }
//        } else if (event.getSource() == resetBtn) {
//            if (program != null && !program.isComplete()) {
//                program.reset();
//            }
//        }
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
    }

    @Override
    public void onOutStr(String value) {
        System.out.print(value);
    }
    
}
