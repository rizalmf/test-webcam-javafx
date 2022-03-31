/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testwebcamcapture.libraries;

import testwebcamcapture.model.CameraInfo;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author rizal
 */
public class WebcamLibrary {
    private Webcam webcam = null;
    private boolean isStreaming = false;
    private BufferedImage buffImage;
    
    public ObservableList<CameraInfo> listWebcam() {
        ObservableList<CameraInfo> infoList = FXCollections.observableArrayList();
        
        int index = 0;
        for (Webcam w : Webcam.getWebcams()) {
            CameraInfo info = new CameraInfo();
            info.setWebCamIndex(index++);
            info.setWebCamName(w.getName());
            
            infoList.add(info);
        }
        
        return infoList;
    }
        
    public void stop() {
        isStreaming = false;
        
        // stop capture only
        webcam.close();
        // matikan webcam
        Webcam.shutdown();
    }
    

    public void start(SimpleObjectProperty<Image> image, int index) {
        if (isStreaming) {
            return;
        }
        
        webcam = Webcam.getWebcams().get(index);
        // open stream
        webcam.open();
        isStreaming= true;
        
        Task<Void> task = new Task<Void>() {
            
            @Override
            protected Void call() throws Exception {

                while (isStreaming) {
                    try {
                        if ((buffImage = webcam.getImage()) != null) {
                            Platform.runLater(() -> {
                                final Image mainImage = SwingFXUtils
                                                .toFXImage(buffImage, null);
                                image.set(mainImage);
                            });

                            buffImage.flush();

                        }
                    } catch (Exception e) {
                    } finally {
                    }
                }

                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
