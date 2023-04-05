package quiet.com.ShopQA.Ultils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.management.RuntimeErrorException;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QRGenerator {


    public static String generateQRCode(String qrContent){
        int width = 300; // Chiều rộng của hình ảnh
        int height = 300; // Chiều cao của hình ảnh
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height, hintMap);
        } catch (WriterException e) {
            System.out.println("Error!!!");
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(Color.BLACK.getRGB() , Color.WHITE.getRGB() ) ;

        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        } catch (IOException e) {
            System.out.println("Lỗi!!!");
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
    }
}
