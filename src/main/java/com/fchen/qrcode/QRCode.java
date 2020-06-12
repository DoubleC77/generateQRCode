package com.fchen.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCode {

    /**
     * 生成矩阵
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static BitMatrix generateQRCodeStream(String content,int width,int height){
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");    //字符编码
        hints.put(EncodeHintType.MARGIN,0);                 //二维码与图片边距
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q); //容错等级 L、M、Q、H 其中 L 为最低, H 为最高,等级越高存储信息越少
        BitMatrix bitMatrix;
        try {
            //参数顺序分别为:编码内容,编码类型,生成图片的宽度,生成图片的高度,设置参数
            //高度和宽度都是以像素为单位
            //一般content都是url,扫描后可以自动跳转到指定的地址
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        return bitMatrix;
    }

    public static void generateQRCodeImage(String content, int size){
        try {
            BitMatrix bitMatrix = generateQRCodeStream(content,size,size);
            File file = new File("/home/ttx/app/test.jpg");
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            MatrixToImageWriter.writeToFile(bitMatrix,"jpg",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void decodeImage(String filePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            String content = result.getText();
            System.out.println("图片中内容：  ");
            System.out.println("content： " + content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        //generateQRCodeImage("http://www.baidu.com",300);
        decodeImage("C:/home/ttx/app/test.jpg");
    }
}
