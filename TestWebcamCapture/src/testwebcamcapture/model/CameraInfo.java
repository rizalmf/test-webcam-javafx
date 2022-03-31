/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testwebcamcapture.model;

/**
 *
 * @author rizal
 */
public class CameraInfo {
    private String name ;
    private int index;

    public String getName() {
            return name;
    }
    public void setName(String webCamName) {
            this.name = webCamName;
    }
    public int getIndex() {
            return index;
    }
    public void setIndex(int webCamIndex) {
            this.index = webCamIndex;
    }

    @Override
    public String toString() {
            return getName();
    }
}
