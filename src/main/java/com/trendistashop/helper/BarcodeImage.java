package com.trendistashop.helper;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Locnd
 */
public class BarcodeImage {
    public byte[] generateBarcodeImage(String barcodeText) {
        try {
            // Tạo barcode Code 128
            Barcode barcode = BarcodeFactory.createCode128B(barcodeText);
            barcode.setBarHeight(40);
            barcode.setBarWidth(2);
            barcode.setDrawingText(true);

            // Vẽ thành ảnh
            BufferedImage image = new BufferedImage(300, 80, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(java.awt.Color.WHITE);
            g2d.fillRect(0, 0, 300, 80);
            barcode.draw(g2d, 20, 10); // Vị trí vẽ
            g2d.dispose();

            // Chuyển thành byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();

        } catch (BarcodeException | OutputException | IOException e) {
            return null;
        }
    }
}
