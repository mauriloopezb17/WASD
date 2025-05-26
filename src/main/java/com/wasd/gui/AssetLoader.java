package com.wasd.gui;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.net.ssl.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class AssetLoader {

    // Classpath version
    public static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(AssetLoader.class.getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // URL version
    public static ImageIcon loadIconFromUrl(String urlString, int width, int height) {
        try {
            disableCertificateValidation(); // Unsafe, dev-only
            ImageIcon icon = new ImageIcon(new URL(urlString));
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Classpath version
    public static ImageIcon loadCircularIcon(String path, int diameter) {
        try {
            BufferedImage master = ImageIO.read(AssetLoader.class.getResource(path));
            if (master == null) throw new IOException("Image not found: " + path);
            return createCircularIcon(master, diameter);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // URL version
    public static ImageIcon loadCircularIconFromUrl(String urlString, int diameter) {
        try {
            disableCertificateValidation(); // Unsafe, dev-only
            BufferedImage master = ImageIO.read(new URL(urlString));
            if (master == null) throw new IOException("Image not found at: " + urlString);
            return createCircularIcon(master, diameter);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Classpath version
    public static ImageIcon loadRoundedIcon(String path, int width, int height, int arcSize) {
        try {
            BufferedImage master = ImageIO.read(AssetLoader.class.getResource(path));
            if (master == null) throw new IOException("Image not found: " + path);
            return createRoundedIcon(master, width, height, arcSize);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // URL version
    public static ImageIcon loadRoundedIconFromUrl(String urlString, int width, int height, int arcSize) {
        try {
            disableCertificateValidation(); // Unsafe, dev-only
            BufferedImage master = ImageIO.read(new URL(urlString));
            if (master == null) throw new IOException("Image not found at: " + urlString);
            return createRoundedIcon(master, width, height, arcSize);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper for circular icons
    private static ImageIcon createCircularIcon(BufferedImage master, int diameter) {
        Image scaled = master.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
        BufferedImage scaledBuffered = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledBuffered.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();

        BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mask.createGraphics();
        applyQualityRenderingHints(g2);
        g2.fill(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2 = masked.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(scaledBuffered, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2.drawImage(mask, 0, 0, null);
        g2.dispose();

        return new ImageIcon(masked);
    }

    // Helper for rounded icons
    private static ImageIcon createRoundedIcon(BufferedImage master, int width, int height, int arcSize) {
        Image scaled = master.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledBuffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledBuffered.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();

        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mask.createGraphics();
        applyQualityRenderingHints(g2);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, arcSize, arcSize));
        g2.dispose();

        BufferedImage masked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = masked.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(scaledBuffered, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2.drawImage(mask, 0, 0, null);
        g2.dispose();

        return new ImageIcon(masked);
    }

    private static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    // Unsafe dev-only SSL override for self-signed certificates
    private static void disableCertificateValidation() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
