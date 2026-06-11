package QRDriver.Core;
/**
 * Copyright © 2019-2021 The CETC PHM Authors.
 *
 */
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Class for creating a vector image for QR coda.
 */
public class _SVG {
    
    /*
     * Header data
     */
    private static String pre = "<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg xmlns=\"http://www.w3.org/2000/svg\"\n     version=\"1.1\" width=\"$widthpx\" height=\"$heightpx\">\n";
    /*
     * End of data
     */
    private static String end = "</svg>";
    /*
     * Path to file
     */
    private String filePath;
    
    /*
     * Image data
     */
    BufferedImage img;
    
    /**
     * Class constructor.
     * @param image image data
     * @param filePath path to file
     */
    public _SVG(BufferedImage image, String filePath) {
        this.img = image;
        this.filePath = filePath;
    }
    
    /**
     * Creating SVG image.
     */
    public void toSVG(){
        int width = img.getWidth();
        int height = img.getHeight();
        pre = pre.replaceAll("\\$width", "" + width);
        pre = pre.replaceAll("\\$height", "" + height);

        StringBuilder bSVG = new StringBuilder();
        
        for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) { 
                        Color color = new Color(img.getRGB(x, y), true);

                        if ((color.getAlpha() == 0) == false) {
                            bSVG.append(("    <rect x=\"" + x + "px\" y=\"" + y + "px\" width=\"1px\" height=\"1px\" fill=\"" + "#" + Integer.toHexString(color.getRGB()).substring(2) + "\"/>\n"));
                        }
                }
        }
        
        try {
                Files.write(Paths.get(filePath), (pre + bSVG + end).getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
}
