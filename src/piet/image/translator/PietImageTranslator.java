package piet.image.translator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    
    ArrayList<ArrayList<Region>> pietCode;
    
    @FXML private MenuItem openMenu;
    @FXML private MenuItem aboutMenu;
    
    @FXML private Button stepBtn;
    @FXML private Button runBtn;
    @FXML private Button resetBtn;
    @FXML private Button clearBtn;
    
    @FXML private ImageView imageView;
    @FXML private TextArea outputTextArea;
    @FXML private GridPane gamepane;
    @FXML private ScrollPane scrollPane;
    
//    private static final Image[] icons = {
//        new Image("PIET Icon/PIET-Icon (32x32).png"),
//        new Image("PIET Icon/PIET-Icon (64x64).png"),
//        new Image("PIET Icon/PIET-Icon (128x128).png"),
//        new Image("PIET Icon/PIET-Icon (256x256).png"),
//        new Image("PIET Icon/PIET-Icon (Orig).png"),
//    };
    
    @FXML
    protected void handleButtonAction(ActionEvent event) {
//        System.out.println("handleButtonAction() called.");
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
                imageView.setFitWidth(imgViewImage.getWidth());
                imageView.setFitHeight(imgViewImage.getHeight());
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
//            System.out.println("Step button has been pressed.");
            if (program != null && !program.isComplete()) {
                program.step();
            }
        } else if (event.getSource() == runBtn) {
//            System.out.println("Step button has been pressed.");
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
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("MainPietLayout.fxml"));
        Region region = new Region();
//        region.setI
        
//        gamepane.getStylesheets().add("PietCodelColors.css");
        
        for (int i = 0; i < 5; i++) {
            this.stage.getIcons().add(getIcon(i));
        }
        
        Rectangle rect = new Rectangle();
        rect.setX(50);
        rect.setY(50);
        rect.setWidth(100);
        rect.setHeight(100);
        rect.setFill(Color.BLUE);
        rect.setOnMouseClicked(new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked on square: (" + event.getX() + ", " + event.getY() + ")");
            }
        });
        
        Scene scene = new Scene(root);
        
        
        this.stage.setTitle("P.I.E.T");
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
        return 15;
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
    
    private Image getIcon(int icon) {
        String ico = "src/piet/image/translator/PIET Icons/";
        
        switch(icon) {
            case 0: ico += "PIET-Icon-(32x32).png"; break;
            case 1: ico += "PIET-Icon-(64x64).png"; break;
            case 2: ico += "PIET-Icon-(128x128).png"; break;
            case 3: ico += "PIET-Icon-(256x256).png"; break;
            case 4: ico += "PIET-Icon-(Orig).png"; break;
        }
        
        File image = new File(ico);
        String uri = image.toURI().toString();
        return new Image(uri);
    }
}