package com.fchen.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QRCode {

    /**
     * 生成矩阵
     * @param content
     * @param size
     * @return
     */
    public static BitMatrix generateQRCodeStream(String content,int width,int height){
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hints.put(EncodeHintType.MARGIN,0); //内边框距
        BitMatrix bitMatrix;
        try {
            //参数顺序分别为:编码内容,编码类型,生成图片的宽度,生成图片的高度,设置参数
            //高度和宽度都是以像素来计算的
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

    public static void main(String [] args){
        generateQRCodeImage("http://www.baidu.com",300);
    }
}
