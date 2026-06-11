package QRDriver;
/**
 * Copyright © 2019-2021 The CETC PHM Authors.
 *
 */
import QRDriver.Core.DataType;
import QRDriver.Core.ImageType;
import QRDriver.Core._Creator;
import QRDriver.Core._DataBuilder;

import QRDriver.Core._SVG;

import java.awt.image.BufferedImage;

import java.util.Arrays;


/**
 * * Working with QR codes.
 */
public class QR {
    
    /*
     * Code data type
     */
    private DataType typeCode;
    /*
     * QR Code Generation Class 
     */
    private _Creator creator;
    
    /**
     * Class constructor with data type selection.
     * @param pathFile the path to the file
     * @param type data type
     */
    public QR(String pathFile, DataType type) {
        this.creator = new _Creator();
        this.creator.setPath(pathFile);
        this.typeCode = type;
    }
    
    /**
     * Default class constructor.
     * @param pathFile the path to the file
     */
    public QR(String pathFile){
        this.creator = new _Creator();
        this.creator.setPath(pathFile);
        this.typeCode = DataType.URL;
    }
    
    /**
     * Setting the image size.
     * @param size size in pixels
     */
    public void setSize(int size){
        this.creator.setHeight(size);
        this.creator.setWidth(size);
    }
    
    /**
     * Setting parameters by data type.
     * @param params data to fill 
     */
    public void setData(String ... params){
        _DataBuilder build = new _DataBuilder(this.typeCode);
        String data = null;
        switch(this.typeCode){
        case URL:
            build.byURL(params[0]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect URL format");
            }
            break;
        case EMAIL:
            build.byEMAIL(params[0], params[1]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect email format");
            }
            break;
        case PHONE:
            build.byPHONE(params[0]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect phone number format");
            }
            break;
        case SMS:
            if (params.length > 2){
                build.bySMS(Arrays.asList(params[0]));
                data = build.getData();
            } else {
                build.bySMS(params[0], params[1]);
                if(build.checkData()){
                    data = build.getData();
                } else {
                    System.out.println("Incorrect SMS message format");
                }
            }
            break;
        case MMS:
            build.byMMS(params[0], params[1]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect MMS message format");
            }
            break;
        case GEO:
            build.byGEO(params[0], params[1]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect geographic coordinates format");
            }
            break;
        case WIFI:
            build.byWIFI(params[0], params[1], params[2]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect Wi-Fi connection parameters");
            }
            break;
        case VCARD:
            build.byVCARD(params[0], params[1], params[2], params[3], params[4], params[5]);
            if(build.checkData()){
                data = build.getData();
            } else {
                System.out.println("Incorrect format of the electronic business card");
            }
            break;
        case TEXT:
            data = params[0];
            break;
        }
        this.creator.setText(data);
    }
    
    /**
     * Saving the QR code to a file.
     */
    public void save(){
        String path = this.creator.getPath();
        if(path.substring(path.lastIndexOf('.') + 1).toUpperCase().equals(ImageType.SVG.toString())){
            BufferedImage image = this.creator.createToBufferedImage();
            _SVG svg = new _SVG(image, path);
            svg.toSVG();
        } else {
            this.creator.create();
        }
    }
    
    /**
     * Reading QR code data from a file.
     * @return string text content of QR code 
     */
    public String read(){
        return this.creator.readText();
    }
    
}
