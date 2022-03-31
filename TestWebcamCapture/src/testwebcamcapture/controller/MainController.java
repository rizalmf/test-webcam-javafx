/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testwebcamcapture.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import testwebcamcapture.libraries.WebcamLibrary;
import testwebcamcapture.model.CameraInfo;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class MainController implements Initializable {

    @FXML
    private ComboBox<CameraInfo> cbWebcam;
    @FXML
    private Button bCapture;
    @FXML
    private Button bStop;
    @FXML
    private Pane pWebcam;
    
    private WebcamLibrary webcamLibrary;
    private SimpleObjectProperty<Image> imageProperty;
    private ImageView ivCaptureImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            initNode();
        });
    }    
    
    public void initNode() {
        webcamLibrary = new WebcamLibrary();
        ivCaptureImage = new ImageView();
        // register image view to pane
        pWebcam.getChildren().add(ivCaptureImage);
        // bind width, height 
        ivCaptureImage.fitHeightProperty().bind(pWebcam.heightProperty());
        ivCaptureImage.fitWidthProperty().bind(pWebcam.widthProperty());
        
        imageProperty = new SimpleObjectProperty<>();
        // bind image
        ivCaptureImage.imageProperty().bind(imageProperty);
        
        cbWebcam.setItems(webcamLibrary.listWebcam());
    
        bCapture.setOnAction((e) -> {
            CameraInfo info = cbWebcam.getValue();
            if (info != null) {
                // start if exist
                webcamLibrary.start(imageProperty, info.getWebCamIndex());
            }
        });
        
        bStop.setOnAction((e) -> {
            // stop
            webcamLibrary.stop();
        });
    }
    
    
}
