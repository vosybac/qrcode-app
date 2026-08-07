package com.example.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrCodeService {

    private static final int SIZE = 320;

    public String generateQrDataUri(String content) throws WriterException, IOException {
        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, SIZE, SIZE);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
