package com.wasd;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AssetLoader {
    public static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(AssetLoader.class.getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static ImageIcon loadCircularIcon(String path, int diameter) {
        try {
            BufferedImage master = ImageIO.read(AssetLoader.class.getResource(path));
            if (master == null) throw new IOException("Image not found: " + path);

            // Resize the image to fit the desired diameter
            Image scaled = master.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            BufferedImage scaledBuffered = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaledBuffered.createGraphics();
            g.drawImage(scaled, 0, 0, null);
            g.dispose();

            // Create circular mask
            BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = mask.createGraphics();
            applyQualityRenderingHints(g2);
            g2.fill(new Ellipse2D.Float(0, 0, diameter, diameter));
            g2.dispose();

            // Apply mask to image
            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            g2 = masked.createGraphics();
            applyQualityRenderingHints(g2);
            g2.drawImage(scaledBuffered, 0, 0, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2.drawImage(mask, 0, 0, null);
            g2.dispose();

            return new ImageIcon(masked);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
