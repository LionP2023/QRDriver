package QRDriver.Core;
/**
 * Copyright © 2019-2021 The CETC PHM Authors.
 *
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;


import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Class for creating YK codes.
 */
public class _Creator {
    
    /*
     * charsets
     */
    private static String CHARSET_UTF_8 = "UTF-8";
    
    /*
     * file name default
     */
    private static String FILE_NAME = "QRCode.png";
    
    /*
     * error correction levels
     */
    private static int LEVEL_LOW = 1;
    private static int LEVEL_MEDIUM = 2;
    private static int LEVEL_QUARTILE = 3;
    private static int LEVEL_HIGH = 2;
    
    /*
     * data for QR code
     */
    private String text;
    /*
     * path image file
     */
    private String pathFile;
    /*
     * selected charset
     */
    private String charset;
    /*
     * height code
     */
    private int height;
    /*
     * width code
     */
    private int width;
    /*
     * QR code result
     */ 
    Result qrResult;
    /*
     * barcode format
     */
    BarcodeFormat format;
    /*
     * error correction level
     */
    int level;
    
    /**
     * Default class constructor.
     */
    public _Creator() {
        this.charset = CHARSET_UTF_8;
        this.height = 0;
        this.width = 0;
        this.format = BarcodeFormat.QR_CODE;
        this.level = LEVEL_LOW;
    }
    
    /**
     * Class constructor.
     * @param text data for code
     * @param pathFile path to file
     * @param charset code page
     * @param height height code in pixels
     * @param width width code in pixels
     */
    public _Creator(String text, String pathFile, String charset, int height, int width) {
        this.text = text;
        this.pathFile = pathFile;
        this.charset = charset;
        this.height = height;
        this.width = width;
        this.format = BarcodeFormat.QR_CODE;
        this.level = LEVEL_LOW;
    }
    
    /**
     * Set data.
     * @param text data for code
     */
    public void setText(String text){
        this.text = text;
    }
    
    /**
     * Get data.
     * @return data for code
     */
    public String getText(){
        return this.text;    
    }
    
    /**
     * Set file path with file extension check.
     * @param path path to file
     */
    public void setPath(String path){
        String check = path.substring(path.lastIndexOf('.') + 1).toUpperCase();
        // check file type format
        if(check.equals(ImageType.PNG.toString()) ||  
           check.equals(ImageType.BMP.toString()) ||
           check.equals(ImageType.GIF.toString()) ||
           check.equals(ImageType.JPG.toString()) ||
           check.equals(ImageType.SVG.toString())){
            this.pathFile = path;
        } else {
            System.out.print("Invalid file type" + '\n');
        }
    }
    
    /**
     * Get path.
     * @return current path to file
     */
    public String getPath(){
        return this.pathFile;
    }
        
    /**
     * Set code page.
     * @param charset code page
     */
    public void setCharset(String charset){
        this.charset = charset;
    }
    
    /**
     * Get code page.
     * @return current code page
     */
    public String getCharset(){
        return this.charset;
    }
    
    /**
     * Set height code.
     * @param height height code
     */
    public void setHeight(int height){
        this.height = height;
    }
    
    /**
     * Get height code.
     * @return current height code
     */
    public int getHeight(){
        return this.height;
    }
    
    /**
     * Set width code.
     * @param width width code
     */
    public void setWidth(int width){
        this.width = width;
    }
    
    /**
     * Get width code.
     * @return current width code
     */
    public int getWidth(){
        return this.width;
    }
    
    /**
     * Set barcode format.
     * @param format barcode format
     */
    public void setFormat(BarcodeFormat format){
        this.format = format;
    }
    
    /**
     * Get barcode format.
     * @return current barcode format
     */
    public BarcodeFormat getFormat(){
        return this.format;
    }
    
    /**
     * Set barcode level correction.
     * @param level barcode level correction
     */
    public void setLevel(int level){
        this.level = level;
    }
    
    /**
     * Get barcode level correction.
     * @return current barcode level correction
     */
    public int getLevel(){
        return this.level;
    }
    
    /**
     * Select error correction level.
     * @return ErrorCorrectionLevel
     */
    private ErrorCorrectionLevel _getErrorCorrectionLevel(){
        switch(this.level){
        case 1: return ErrorCorrectionLevel.L;
        case 2: return ErrorCorrectionLevel.M;
        case 3: return ErrorCorrectionLevel.Q;
        case 4: return ErrorCorrectionLevel.H;
        default: return ErrorCorrectionLevel.L;
        }
    }
    
    /**
     * Create new QR code.
     */
    public void create(){
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, _getErrorCorrectionLevel());

        if(this.charset == null){this.charset = CHARSET_UTF_8;}
        if(this.pathFile == null){this.pathFile = FILE_NAME;}
        if(this.width == 0){this.width = 200;}
        if(this.height == 0){this.height = 200;}

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(text.getBytes(this.charset), this.charset), this.format, this.width, this.height, hashMap);
            //MatrixToImageWriter.writeToFile(matrix, this.pathFile.substring(this.pathFile.lastIndexOf('.') + 1), new File(this.pathFile));
            MatrixToImageWriter.writeToPath(matrix, this.pathFile.substring(this.pathFile.lastIndexOf('.') + 1), Paths.get(this.pathFile));
        } catch (WriterException e) {
            } catch (IOException e) {
        }

    }
    
    /**
     * Create new QR code to bytes array.
     * @return bytes array
     */
    public byte[] createToByteArray(){
        byte[] image = null;
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, _getErrorCorrectionLevel());

        if(this.charset == null){this.charset = CHARSET_UTF_8;}
        if(this.pathFile == null){this.pathFile = FILE_NAME;}
        if(this.width == 0){this.width = 200;}
        if(this.height == 0){this.height = 200;}

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(text.getBytes(this.charset), this.charset), this.format, this.width, this.height, hashMap);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, this.pathFile.substring(this.pathFile.lastIndexOf('.') + 1), out);
            image = out.toByteArray();
        } catch (WriterException e) {
            } catch (IOException e) {
        }
        return image;
    }
    
    /**
     * Create new QR code to Buffered Image.
     * @return BufferedImage
     */
    public BufferedImage createToBufferedImage(){
        BufferedImage image = null;
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, _getErrorCorrectionLevel());

        if(this.charset == null){this.charset = CHARSET_UTF_8;}
        if(this.pathFile == null){this.pathFile = FILE_NAME;}
        if(this.width == 0){this.width = 200;}
        if(this.height == 0){this.height = 200;}

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(text.getBytes(this.charset), this.charset), this.format, this.width, this.height, hashMap);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            image = MatrixToImageWriter.toBufferedImage(matrix);
        } catch (WriterException e) {
            } catch (IOException e) {
        }
        return image;
    }
    
    /**
     * Read QR code.
     * @throws FileNotFoundException file not found
     * @throws IOException input output exceptions
     * @throws NotFoundException not found exception
     */
    private void _read() throws FileNotFoundException, IOException, NotFoundException{
        //Map<DecodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<DecodeHintType, ErrorCorrectionLevel>();
        //hashMap.put(DecodeHintType.OTHER, _getErrorCorrectionLevel());
        Hashtable<DecodeHintType, String> hashMap = new Hashtable<DecodeHintType, String>();
        hashMap.put(DecodeHintType.CHARACTER_SET, this.charset); 
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                                    new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(this.pathFile)))));
        this.qrResult = new MultiFormatReader().decode(binaryBitmap, hashMap);
    }
    
    /**
     * Read data from QR code.
     * @return data code
     */
    public String readText(){
        try {
            _read();
        } catch (FileNotFoundException | NotFoundException e) {
        } catch (IOException e) {
        }
        return this.qrResult.getText();
    }
    
    /**
     * Read timestamp from QR code.
     * @return timestamp in long format
     */
    public long readTimestamp(){
        try {
            _read();
        } catch (FileNotFoundException | NotFoundException e) {
        } catch (IOException e) {
        }
        return this.qrResult.getTimestamp();
    }
    
    /**
     * Read format QR code.
     * @return BarcodeFormat
     */
    public BarcodeFormat readFormat(){
        try {
            _read();
        } catch (FileNotFoundException | NotFoundException e) {
        } catch (IOException e) {
        }
        return this.qrResult.getBarcodeFormat();
    }
    
}
